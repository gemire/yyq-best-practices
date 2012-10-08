<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Mp3播放器</title>
		<script type="text/javascript" src="js/jquery.jmp3.js"></script>
		
	</head>
	<body>
	<a href="userManage!addList.action">添加歌曲</a><br/>
	<c:forEach items="${dirList}" var="dir" varStatus="num">
		${dir}===================================<br/>
		<c:forEach items="${mp3List[num.index]}" var="mp3" varStatus="num2">
			<SPAN class="mp3" id="${num.index}_${num2.index}">${mp3.fileName}</SPAN>
			${mp3.displayName}
			<a href="userManage!deleteMp3.action?id=${mp3.id}">删除歌曲</a>
			<br/>
			<script type="text/javascript">
				$("#${num.index}_${num2.index}").jmp3({
					filepath: "http://localhost:8080/mp3/",
					showfilename: "false",
					backcolor: "000000",
					forecolor: "00ff00",
					width: 150,
					showdownload: "false"
				});
			</script>
		</c:forEach>
	</c:forEach>	
	</body>
</html>
