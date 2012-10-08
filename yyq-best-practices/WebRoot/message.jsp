<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>提示信息</title>
<script type="text/javascript">
	function init(){
		//三秒后跳回指定页面
		window.setTimeout("messageForm.submit()",3000);
	}
	$(function() {
		init();
	});
</script>
</head>
<body>
<form action="${backtoPage}" method="post" name="messageForm">
<p>&nbsp;</p>
<table width="49%"  border="0" align="center" cellpadding="0" cellspacing="1">
	<tr>
		<td height="19" bgcolor="#3e83c9" align="center"><span class="h1">${message}</span></td>
	</tr>
	<tr>
		<td height="80" bgcolor="#95bce2" align="center">
			<input type="submit" value="确定" height="40" width="60">
		</td>
	</tr>
</table>
<c:if test="${param1name!=null}">
<input type="hidden" name="${param1name}" value="${param1value}">
</c:if>
</form>
</body>
</html>

