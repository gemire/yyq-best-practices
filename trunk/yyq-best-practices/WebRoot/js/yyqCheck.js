function checkEmpty(id){
	var obj=document.getElementById(id);
	if(obj.value.length==0){
		alert("请输入数据！");
		obj.focus();
		return false;
	}
	return true;
}
function checkInt(id){
	var obj=document.getElementById(id);
	var patrn=/^[0-9]+$/;
	if (!patrn.exec(obj.value)){
		alert("请输入整数！");
		obj.focus();
		return false;
	}
	return true;
}
function checkNumber(id){
	var obj=document.getElementById(id);
	var patrn=/^[0-9]+[.]?[0-9]{0,2}$/;
	if (!patrn.exec(obj.value)){
		alert("请输入数字，小数请保留到小数点后两位！");
		obj.focus();
		return false;
	}
	return true;
}