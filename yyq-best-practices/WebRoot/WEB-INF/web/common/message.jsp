<%@ page language="java" import="com.yyq.util.SSUtil" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>聊天室</title>
	<script type='text/javascript' src="dwr/interface/MessageUtil.js"></script>
	<script type="text/javascript">
		function textEnter(){
			if(event.keyCode==13) sendMessage(); //回车键发送消息
		}
		function sendMessage(){
			var text=DWRUtil.getValue("message");
			if(text!="") MessageUtil.sendMessage("<%=SSUtil.getName()%>",text);
			DWRUtil.setValue("message","");
		}
		function newMessage(data){
			DWRUtil.addOptions("messageBox",new Array(data));
		}
		
		function reflash(data){
			DWRUtil.removeAllOptions("userList");
			DWRUtil.addOptions("userList",data);
		}
		document.onkeydown = function()
		{
			if(event.keyCode==116) { //屏蔽F5刷新键
				event.keyCode=0;
				event.returnValue = false;
			}
		}
	</script>
</head>
<body> 
欢迎您！<input id="username" type="text" value="<%=SSUtil.getName()%>">
<br/>
<input type="text" id="message" size="100" onkeypress="textEnter()"><input type="button" onclick="sendMessage()" value="发送">
<table>
	<tr>
		<td>用户列表：</td>
		<td>消息盒子：</td>
	</tr>
	<tr>
		<td>
			<select multiple="multiple" id="userList" style="width:200px;height:400px;"></select>
		</td>
		<td>
			<select multiple="multiple" id="messageBox" style="width:800px;height:400px;"></select>
		</td>
	</tr>
</table>
<script type="text/javascript">
	document.body.onload=function(){
			dwr.engine.setActiveReverseAjax(true);
			MessageUtil.addRoom("<%=SSUtil.getName()%>")
		}
		document.body.onunload=function(){
			MessageUtil.leftRoom("<%=SSUtil.getName()%>");
			alert("即将退出聊天室！");
		}
</script>
</body>
</html>