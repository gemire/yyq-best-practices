<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>用户列表</title>
		<SCRIPT type="text/javascript">
			function gotoUser(uid){
				$$("uid").value=uid;
				$$("userManageForm").action="userManage!gotoUser.action";
				$$("userManageForm").submit();
			}
			function deleteUser(uid){
				if(confirm("确定要删除用户吗？")){
					$$("uid").value=uid;
					$$("userManageForm").action="userManage!deleteUser.action";
					$$("userManageForm").submit();
				}
			}
			function gotoAuthority(uid){
				$$("uid").value=uid;
				$$("userManageForm").action="userManage!gotoAuthority.action";
				$$("userManageForm").submit();
			}
		</SCRIPT>
	</head>
	<body>
	<form id="userManageForm" method="post" action="userManage!newUser.action">
		<table border="1" class="stripeMe">
		<thead>
			<tr>
				<th>用户名</th>
				<th>密码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="person" items="${userList}">
				<tr>
					<td>${person.username}</td>
					<td>密码已经过MD5加密</td>
					<td>
						<button onclick="gotoUser('${person.id}')" style="background: url(css/images/btnbg.gif)">更新</button>
						<button onclick="deleteUser('${person.id}')">删除</button>
						<button onclick="gotoAuthority('${person.id}')">权限</button>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3">
					<button type="submit">建立新用户</button>
				</td>
			</tr>
		</tbody>
		</table>
		<input type="hidden" id="uid" name="uid" value="0">
	</form>
	</body>
</html>