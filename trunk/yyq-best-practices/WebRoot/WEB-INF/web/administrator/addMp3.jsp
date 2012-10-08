<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>可添加的Mp3列表</title>
		
	</head>
	<body>
	<c:forEach items="${dirList}" var="dir" varStatus="num">
		<br/>${dir}===================================<br/>
		<c:forEach items="${mp3List[num.index]}" var="mp3" varStatus="num2">
			${mp3}
			<a href="userManage!addMp3.action?displayName=${mp3}&dir=${dir}">添加歌曲</a>
			<br/>
		</c:forEach>
	</c:forEach>	
	</body>
</html>
