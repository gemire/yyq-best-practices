<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>新建用户</title>
		<script src='dwr/util.js'></script>
		<script type="text/javascript" src="js/jquery.validate.min-1.7.js"></script>
		<script type="text/javascript" src="js/jquery.metadata.js"></script>
		<script type="text/javascript" src="js/jquery.watermark.min-3.0.4.js"></script>
		<SCRIPT type="text/javascript">
			$().ready(function(){
				$("#userForm").validate();
				//jQuery插件Watermark实现自定义文本框水印提示效果
				$("#username").watermark("请输入长度2-10的字母");
			});
			function checkUsername(){
				LoginDWR.checkUsername($("#username").val(),checkUsernameHander);
			}
			function checkUsernameHander(data){
				if(data==true){
					$("#usernameMessage").html("用户名已经存在！");
				}else{
					$("#usernameMessage").html("");
				}
			}
		</SCRIPT>
<style type="text/css">
.watermark {
	color: #999 !important;
}
</style>
	</head>
	<body>
	<form id="userForm" action="userManage!saveOrUpdateUser.action" method="post">
		<input type="hidden" name="user.id" value="${user.id}">
		<font id="usernameMessage" color="red"></font>
		<table>
		<tr>
			<td bgcolor="lightblue">用户名:</td>
			<td><input type="text" name="user.username" id="username" value="${user.username}" <c:if test="${empty user.id}">onblur="checkUsername()"</c:if> <c:if test="${not empty user.id}">readonly="readonly"</c:if> class="{required:true,rangelength:[2,10]}"></td>
		</tr>
		<tr>
			<td bgcolor="lightblue">密码:</td>
			<td><input type="password" name="user.password" value="${user.password}" class="{required:true}"></td>
		</tr>
		<tr>
			<td bgcolor="lightblue">状态:</td>
			<td>
			<select name="user.enabled">
				<option value="true" <c:if test="${user.enabled=='true'}">selected="selected"</c:if> >正常</option>
				<option value="false" <c:if test="${user.enabled=='false'}">selected="selected"</c:if> >禁用</option>
			</select>
			</td>
		</tr>
		<tr>
			<td bgcolor="lightblue">用户类型:</td>
			<td>
			<select name="user.userType">
				<option value="0" <c:if test="${user.userType==0}">selected="selected"</c:if> >管理员</option>
				<option value="1" <c:if test="${user.userType==1}">selected="selected"</c:if> >部门经理</option>
				<option value="2" <c:if test="${user.userType==2}">selected="selected"</c:if> >员工</option>
			</select>
			</td>
		</tr>
		<tr>
			<td bgcolor="lightblue">姓名:</td>
			<td><input type="text" name="user.name" value="${user.name}" class="{required:true}"></td>
		</tr>
		<tr>
			<td bgcolor="lightblue">所属部门:</td>
			<td>
			<select name="user.dept__id">
				<c:forEach var="dept" items="${deptList}">
					<option value="${dept.id}" <c:if test="${dept.id==user.dept__id}">selected="selected"</c:if> >${dept.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td bgcolor="lightblue">工资:</td>
			<td><input type="text" name="user.money" value="${user.money}"></td>
		</tr>
		<tr>
			<td><input type="submit" value="保存"></td>
			<td><input type="button" value="返回" onclick="javascript:history.go(-1)"/></td>
		</tr>
		</table>
	</form>
	</body>
</html>