<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
	<head>
		<title>欢迎来到保险销售管理系统 - <decorator:title default="Welcome!" /></title>
			<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
			<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
			<META HTTP-EQUIV="Expires" CONTENT="0">
			<script src='dwr/interface/LoginDWR.js'></script>
			<script src='dwr/engine.js'></script>
			<script src='dwr/util.js'></script>
			<script type="text/javascript">
				$$=dwr.util.byId;//将dwr里的$注册成$$
			</script>
			<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
			<script type="text/javascript" src="js/myJs.js"></script>
			<!-- 
			<script type="text/javascript" src='js/jquery.message.min-1.0.0.js'></script>
			 -->
			<script type="text/javascript" src="js/zebra.js"></script>
			<link rel="stylesheet" type="text/css" href="css/zebra.css"/>
			
			<link rel="stylesheet" type="text/css" href="css/mycss.css"/>
			
			<!-- 
			<link rel="stylesheet" type="text/css" media="screen" href="css/jquery.message.css"/>
			 -->
			<decorator:head />
			
			<script type="text/javascript">

			
				//<c:if test="${not empty message}">
				//	$(function() {
				//		$().message("${message}");
				//	});
				//	<c:remove var="message"/>
				//</c:if>
				
				function gotoLogin(){
					window.location.href="/YYQ/";
				}
				function checkUser(){
					if("<sec:authentication property='name'/>"=="roleAnonymous") window.location.href="/YYQ/";
				}
			</script>
	</head>
	<body onload="checkUser()" style="overflow:auto">
		<table height="100%" width="100%">
			<tr height="12%" valign="middle">
				<td><table width="100%"><tr><td width="80%" valign="middle">
					欢迎您，<label id="name"><sec:authentication property="name"/></label>!
					<button onclick="LoginDWR.logout(gotoLogin);">登出</button></td>
					<td>
					<jsp:include page="/timer.jsp"/>
				</td></tr></table></td>
			</tr>
			<tr height="80%"><td width="100%">
				<table height="100%" width="100%" border="1">
					<tr>
						<td width="180px" style="background: url(css/images/bodybg.gif);background-repeat: repeat-x;background-color: #00ff00;"><div style="position:absolute;top: 240px"><jsp:include page="/left.jsp"/></div></td>
						<td>
							<decorator:body />
						</td>
					</tr>
				</table>
			</td></tr>
			<tr>
				<td> © 2009 YYQ 保留所有权限 <button onclick="window.location.href='iframe.jsp?action=common!gotoAbout'">关于作者</button> <button onclick="window.location.href='/YYQ/technology.jsp'">相关技术</button></td>
			</tr>
		</table>
	</body>
</html>