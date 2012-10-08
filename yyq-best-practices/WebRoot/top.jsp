<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags"  prefix="s" %>

<html>
  <head>
    <title>请登陆</title>
    <script src='dwr/interface/LoginDWR.js'></script>
		<script src='dwr/engine.js'></script>
		<script src='dwr/util.js'></script>
    <SCRIPT type="text/javascript">
    	function init(){
    		//checkSession();
    		setInterval("checkSession()",10000);
    	}
    	
    	function checkSession(){
    		LoginDWR.check(check);
    	}
    	
    	function check(data){
    		if(data=="-1"){
    			$("logoutDiv").style.display="none";
    			$("loginDiv").style.display="";
    			//alert(parent.mainFrame.location.href);
    			if(parent.mainFrame.location.href!="http://localhost:8080/YYQ/login.jsp"){
    				parent.mainFrame.location.href="login.jsp";
    			}
    		}else{
    			
    			//var user=data.split(",");
    			//if(user.length!="2"){
    			//	alert("未知错误！");
    			//}else{
    			//	var userType;
    			//	if(user[1]=="0"){
    			//		userType="管理员";
    			//	}else if(user[1]=="1"){
    			//		userType="部门经理";
    			//	}else if(user[1]=="2"){
    			//		userType="普通员工";
    			//	}else{
    			//		userType="未知";
    			//	}
    				$("loginDiv").style.display="none";
    				$("logoutDiv").style.display="";
    				$("name").innerHTML=data;//user[0];
    				//$("type").innerHTML=userType;
    				//您的等级是<label id="type" class="labelClass"></label>。
    			//}
    		}
    	}
    	
    	function logout(){
    		LoginDWR.logout();
    		$("logoutDiv").style.display="none";
    		$("loginDiv").style.display="";
    		window.setTimeout("gotoLogin()",1000);
    	}
    	
    	function gotoLogin(){
    		parent.mainFrame.location.href="login.jsp";
    	}
    </SCRIPT>
<style type="text/css">
.labelClass{
	font-size: 18pt;
	font-weight: bold;
	color: red;
}
</style>
  </head>
  <body onload="init()">
	<div id="loginDiv">
		您还没有登陆，请先登陆！
	</div>
	<div id="logoutDiv" style="display:none">
		欢迎您，<label id="name" class="labelClass"></label>！<input type="button" value="登出" onclick="logout()">
	</div>
  </body>
</html>
