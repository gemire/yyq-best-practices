package main
{
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.display.StageAlign;
	import flash.display.StageQuality;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	
	import org.papervision3d.cameras.Camera3D;
	import org.papervision3d.core.math.Number3D;
	import org.papervision3d.events.FileLoadEvent;
	import org.papervision3d.materials.BitmapFileMaterial;
	import org.papervision3d.materials.ColorMaterial;
	import org.papervision3d.materials.utils.MaterialsList;
	import org.papervision3d.objects.parsers.DAE;
	import org.papervision3d.render.BasicRenderEngine;
	import org.papervision3d.scenes.Scene3D;
	import org.papervision3d.view.Viewport3D;
	
	public class DAETest extends Sprite
	{
		private var _viewPort:Viewport3D;
		private var _scene:Scene3D;
		private var _camera:Camera3D;
		private var _render:BasicRenderEngine;
		private var _dae:DAE;
		
		public function DAETest()
		{
			_viewPort = new Viewport3D(425,280);
			addChild(_viewPort);
			_scene = new Scene3D();
			_camera = new Camera3D();
			_render = new BasicRenderEngine();
			trace("1");
			var _materials:MaterialsList = new MaterialsList();
			_materials.addMaterial(new BitmapFileMaterial("assets/cow.png"),"cow");
			_dae = new DAE();
			_dae.addEventListener(FileLoadEvent.LOAD_COMPLETE, onloadCompleteHandler);
			_dae.addEventListener(FileLoadEvent.LOAD_ERROR, onloadErrorHandler);
			_dae.load("assets/cow.dae", _materials);
			_dae.z = 200;
			_dae.scale = 200;//放大200倍
			_dae.rotationX = 45;
		}
		private function onloadCompleteHandler(e:FileLoadEvent):void
		{
			_scene.addChild(_dae);
			addEventListener(Event.ENTER_FRAME, enterHand);
			
		}
		private function onloadErrorHandler(e:FileLoadEvent):void
		{
			trace("载入出现问题");
		}
		private function enterHand(e:Event):void
		{
			//trace(_dae.hitTestPoint(0, 0, -950));
			_dae.rotationX = _viewPort.mouseY * 0.5;
			_dae.rotationY = _viewPort.mouseX * 0.5;
			_render.renderScene(_scene, _camera, _viewPort);
		}
	}
}