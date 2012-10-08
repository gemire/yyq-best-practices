//获取对象在屏幕绝对位置的Left值
function getOffsetLeft(obj){
	var i=obj.offsetLeft;
	while(obj.offsetParent!=null){
		obj=obj.offsetParent;
		i=i+obj.offsetLeft;
	}
	return i;
}
//获取对象在屏幕绝对位置的Top值
function getOffsetTop(obj){
	var i=obj.offsetTop;
	while(obj.offsetParent!=null){
		obj=obj.offsetParent;
		i=i+obj.offsetTop;
	}
	return i;
}
function setLeft(id_s,id_t){
	document.getElementById(id_s).style.left=getOffsetLeft(document.getElementById(id_t));
}
function setTop(id_s,id_t){
	document.getElementById(id_s).style.top=getOffsetTop(document.getElementById(id_t));
}
//设置id_s的位置与id_t一样
function setPosition(id_s,id_t){
	setLeft(id_s,id_t);
	setTop(id_s,id_t);
}
function setWidth(id_s,id_t){
	document.getElementById(id_s).style.width=document.getElementById(id_t).offsetWidth;
}
function setHeight(id_s,id_t){
	document.getElementById(id_s).style.height=document.getElementById(id_t).offsetHeight;
}
//设置id_s的大小与id_t一样
function setSize(id_s,id_t){
	setWidth(id_s,id_t);
	setHeight(id_s,id_t);
}
//将id_s覆盖id_t
function cover(id_s,id_t){
	setPosition(id_s,id_t);
	setSize(id_s,id_t);
}
//左边导航树二级菜单的显示和隐藏
function doMenu(ul_id){
	var ul=$$(ul_id);
	if(ul.style.display=="none"){
		ul.style.display="block";
	}else{
		ul.style.display="none";
	}
}