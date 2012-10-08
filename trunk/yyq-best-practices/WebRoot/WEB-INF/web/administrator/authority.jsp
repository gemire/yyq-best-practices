<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>权限管理</title>
	</head>
	<body>
	<form id="authorityForm" action="userManage!updateAuthority.action">
		<input type="hidden" name="uid" value="${uid}">
		<table class="stripeMe">
			<thead>
				<tr>
					<th>权限名称</th>
					<th>是否具有</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${authoritylist}" var="al">
				<tr>
					<td>${al.authority}</td>
					<td><input id="a${al.id}" name="authority" type="checkbox" value="${al.id}"/></td>
				</tr>
			</c:forEach>
				<tr>
					<td><input type="submit" value="保存"></td>
					<td><input name="action:userManage!returnToUserList" type="submit" value="返回"></td>
				</tr>
			</tbody>
		</table>
		<SCRIPT type="text/javascript">
			<c:forEach items="${authorities}" var="a">
				document.getElementById("a${a}").checked="checked";
			</c:forEach>
		</SCRIPT>
	</form>
	</body>
</html>