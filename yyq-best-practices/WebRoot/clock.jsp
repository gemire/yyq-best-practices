<div id="c0" style="position:absolute;right:6;top:6; z-index:2;">
</div>
<div id="c1" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>1</b></div>
<div id="c2" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>2</b></div>
<div id="c3" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>3</b></div>
<div id="c4" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>4</b></div>
<div id="c5" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>5</b></div>
<div id="c6" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>6</b></div>
<div id="c7" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>7</b></div>
<div id="c8" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>8</b></div>
<div id="c9" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>9</b></div>
<div id="c10" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>10</b></div>
<div id="c11" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>11</b></div>
<div id="c12" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>12</b></div>
<div id="ob0" style="position:absolute;left:-20;top:-20;z-index:1"> </div>
<div id="ob1" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<div id="ob2" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<div id="ob3" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<div id="ob4" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<div id="ob5" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<div id="ob6" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
<div id="ob7" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
<div id="ob8" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
<div id="ob9" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
<div id="ob10" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>
<div id="ob11" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>
<div id="ob12" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>

<SCRIPT language=javascript>
<!--
pX=400;pY=200
obs = new Array(13);

function ob(){
	for (i=0; i<13; i++) {
		if (document.all) obs[i]=new Array (eval('ob'+i).style,-100,-100);
		else obs[i] = new Array (eval('document.ob'+i),-100,-100);
	}
}
function cl(a,b,c){
	if(document.all){
		if (a!=0) b+=-1;
		eval('c'+a+'.style.pixelTop='+(pY+(c)));
		eval('c'+a+'.style.pixelLeft='+(pX+(b)));
	}else{
		if(a!=0) b+=10;
		eval('document.c'+a+'.top='+(pY+(c)));
		eval('document.c'+a+'.left='+(pX+(b)));
	}
	if(document.all) c0.style.pixelLeft=26;
}

function runClock(){
	for(i=0; i<13; i++){
		obs[i][0].left=obs[i][1]+pX;
		obs[i][0].top=obs[i][2]+pY;
	}
}

var lastsec;
function timer(){
	time = new Date();
	sec = time.getSeconds();
	if(sec!=lastsec){
		lastsec = sec;
		sec=Math.PI*sec/30;
		min=Math.PI*time.getMinutes()/30;
		hr =Math.PI*((time.getHours()*60)+time.getMinutes())/360;
		for(i=1;i<6;i++){
			obs[i][1] = Math.sin(sec) * (44 - (i-1)*11)-16;
			if (document.layers)obs[i][1]+=10;
			obs[i][2] = -Math.cos(sec) * (44 - (i-1)*11)-27;
		}
		for(i=6;i<10;i++){
			obs[i][1] = Math.sin(min) * (40 - (i-6)*10)-16;
			if (document.layers) obs[i][1]+=10;
			obs[i][2] = -Math.cos(min) * (40 - (i-6)*10)-27;
		}
		for(i=10;i<13;i++){
			obs[i][1] = Math.sin(hr) * (37 - (i-10)*11)-16;
			if (document.layers)obs[i][1]+=10;
			obs[i][2] = -Math.cos(hr) * (37 - (i-10)*11)-27;
		}
	}
}

function setNum(){
	cl(0,-67,-65);
	cl(1,10,-51);
	cl(2,28,-33);
	cl(3,35,-8);
	cl(4,28,17);
	cl(5,10,35);
	cl(6,-15,42);
	cl(7,-40,35);
	cl(8,-58,17);
	cl(9,-65,-8);
	cl(10,-58,-33);
	cl(11,-40,-51);
	cl(12,-16,-56);
}

ob();
setNum();
setInterval('timer()',100);
setInterval('runClock()',100);
//-->
</SCRIPT>