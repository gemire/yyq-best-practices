package main
{
	import flash.display.Bitmap;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.net.URLRequest;
	
	import objects.Cube;
	
	import org.papervision3d.events.InteractiveScene3DEvent;
	import org.papervision3d.materials.BitmapMaterial;
	import org.papervision3d.materials.ColorMaterial;
	import org.papervision3d.materials.utils.MaterialsList;
	import org.papervision3d.objects.DisplayObject3D;
	import org.papervision3d.objects.primitives.Plane;
	import org.papervision3d.view.BasicView;
	
	public class Link3D extends BasicView
	{
		private var d3d:DisplayObject3D=new DisplayObject3D();
		private var cube:Cube;//被选中的方块
		private var cubet:Cube;//这次点击选中的方块
		private var colorMaterial:ColorMaterial;
		private var mL:MaterialsList;
		private var picNum:int=32;//方块种类
		private var c:Array;//原来的颜色
		private var c2:Array;//被选中的颜色
		private var cL:Array;//64个方块的顺序
		private var cubeL:Array;//
		private var i:int;//临时变量
		private var flag:Boolean;//临时变量
		
		private var p:Plane;
		private var imgLoader:Loader;
		
		public function Link3D()
		{
			stage.frameRate = 40;
			stage.addEventListener(KeyboardEvent.KEY_DOWN,keyDownHander);
			
			init();//游戏初始化
			
			imgLoader = new Loader();
			imgLoader.contentLoaderInfo.addEventListener (Event.COMPLETE,loadComplete);
			imgLoader.load(new URLRequest("assets/win.jpg"));
			
			viewport.interactive=true;
			
			addChild(viewport);
			
			startRendering();
		}
		
		//游戏初始化
		private function init():void{
			//变量初始化
			c=new Array(picNum);
			c2=new Array(picNum);
			cL=new Array();
			cubeL=new Array();
			
			//这段生成0-63随机排列的数组
			var j:int;
			for(i=0;i<64;i++){
				flag=false;
				do{
					j=Math.ceil(Math.random()*64-1);
					if(cL.indexOf(j)==-1){
						cL.push(j);
						flag=true;
					}
				}while(flag==false);
			}
			
			//载入游戏贴图
			for(i=0;i<picNum;i++){
				imgLoader = new Loader();
				var loaderInfo:LoaderInfo=imgLoader.contentLoaderInfo;
				loaderInfo.parameters["i"]=i;
				loaderInfo.addEventListener (Event.COMPLETE,picLoadComplete);
				imgLoader.load(new URLRequest("assets/"+i+".jpg?i="+i));//加入图片并传入参数
				imgLoader = new Loader();
				var loaderInfo:LoaderInfo=imgLoader.contentLoaderInfo;
				loaderInfo.parameters["i"]=i;
				loaderInfo.addEventListener (Event.COMPLETE,picLoadComplete2);
				imgLoader.load(new URLRequest("assets/h"+i+".jpg?i="+i));
//				trace("i=="+i);
				
			}
			
			//这段初始化64个方块
			mL=new MaterialsList();
			colorMaterial=new ColorMaterial(0xFFFFFF,1,true);
			mL.addMaterial(colorMaterial,"all");
			for(var x:int=0;x<4;x++){
				for(var y:int=0;y<4;y++){
					for(var z:int=0;z<4;z++){
						cube=new Cube(mL,128,128,128);
						cube.x=128*x;
						cube.y=128*y;
						cube.z=128*z;
						cubeL.push(cube);
						
						cube.addEventListener(InteractiveScene3DEvent.OBJECT_CLICK,onClick);
						d3d.addChild(cube);
					}
				}
			}
			cube=null;//清空cube
			
			d3d.x=0;
			d3d.y=0;
			d3d.z=0;
			
			scene.addChild(d3d);
			super.onRenderTick();
			
		}
		
		private function loadComplete(e:Event):void{
			var bitmap:Bitmap = e.target.content as Bitmap;
			var material:BitmapMaterial = new BitmapMaterial(bitmap.bitmapData);
			material.interactive=true;
			p=new Plane(material,1600,300);
			p.x=200;
			p.y=200;
			p.visible=false;
			scene.addChild(p);
			p.addEventListener(InteractiveScene3DEvent.OBJECT_CLICK,restart);
		}
		
		private function picLoadComplete(e:Event):void{
			var loaderInfo:LoaderInfo=e.target as LoaderInfo;
			var i:int=loaderInfo.parameters["i"];
//			trace("i="+i);
			
			var bitmap:Bitmap = e.target.content as Bitmap;
			var material:BitmapMaterial = new BitmapMaterial(bitmap.bitmapData);
			material.interactive=true;
			
			c[i]=material;
			
			var j:int;
			for(var k:int=0;k<64/picNum;k++){
				j=cL.pop() as int;
				cube=cubeL[j] as Cube;
				cube.material=material;
				cube.color=i;
			}
			cube=null;
			
			super.onRenderTick(e);
			
		}
		
		private function picLoadComplete2(e:Event):void{
			var loaderInfo:LoaderInfo=e.target as LoaderInfo;
			var i:int=loaderInfo.parameters["i"];
			
			var bitmap:Bitmap = e.target.content as Bitmap;
			var material:BitmapMaterial = new BitmapMaterial(bitmap.bitmapData);
			material.interactive=true;
			
			c2[i]=material;
			
		}
		
		//方块点击监听函数
		private function onClick(e:InteractiveScene3DEvent):void{
			var cubet:Cube=Cube(e.currentTarget);
//			trace("cube.color="+cubet.color);
//			trace("cubeL.indexOf="+cubeL.indexOf(cubet));
			if(cube!=null){
				if(cube!=cubet){
					if(check(cube,cubet)){//如果两个方块相同，消去两方块
						d3d.removeChild(cube);
						d3d.removeChild(cubet);
						cube=null;
						
						if(d3d.childrenList().length==0){
							p.visible=true;
						}
					}else{//如果两个方块不同，选中当前方块，还原选中的方块
						cube.material=c[cube.color];
						cubet.material=c2[cubet.color];
						cube=cubet;
					}
				}else{//如果点击的是之前选中的方块，则取消选中
					cube=null;
					cubet.material=c[cubet.color];
				}
			}else{//如果之前还没有方块被选中，选中当前方块
				cubet.material=c2[cubet.color];
				cube=cubet;
			}
			super.onRenderTick(e);
		}
		
		//重新开始
		private function restart(e:InteractiveScene3DEvent):void{
//			trace("ok");
			p.visible=false;
			init();
			super.onRenderTick(e);
		}
		
		//键盘监听函数
		private function keyDownHander(event:KeyboardEvent):void{
			var x:Number,y:Number,z:Number;
			switch (event.keyCode){//w:87  a:65  s:83  d:68;上：38左：37右：39，下：40
				case 38:
					d3d.rotationX=d3d.rotationX+6;
					break;
				case 40:
					d3d.rotationX=d3d.rotationX-6;
					break;
				case 37:
					d3d.rotationY=d3d.rotationY+6;
					break;
				case 39:
					d3d.rotationY=d3d.rotationY-6;
					break;
			}
			//			d3d.lookAt(dd);
		}
		
		//判断两个方块是否一样
		private function check(cube1:Cube,cube2:Cube):Boolean{
			if(cube1.color==cube2.color){
				return true;
			}else{
				return false;
			}
		}
		override protected function onRenderTick(e:Event=null):void
		{
			super.onRenderTick();
		}
	}
}
