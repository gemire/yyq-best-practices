<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="y" uri="http://www.yyq.com/jstl"%>

<html>
	<head>
		<title>保单列表</title>
		<script type="text/javascript">
			function cleanCondition(){
				$("#carHostName").val("");
				$("#carCode").val("");
				$("#inputDateTime").val("");
			}
		</script>
	</head>
	<body>
	<form action="sale!returnToSaleList.action" method="post">
		<table>
			<tr>
				<td>车主姓名：<input id="carHostName" name="host.carHostName" value="${host.carHostName}"></td>
				<td>车牌号：<input id="carCode" name="insu.carCode" value="${insu.carCode}"></td>
			</tr>
			<tr>
				<td>录入时间：<input id="inputDateTime" name="insu.inputDateTime" value="${insu.inputDateTime}"></td>
				<td><button type="submit" style="width: 100px;">查询</button><button onclick="cleanCondition()">清空查询条件</button></td>
			</tr>
		</table>
		<table border="1">
			<tr bgcolor="lightblue">
				<td>车主姓名</td>
				<td>车主证件号</td>
				<td>车牌号</td>
				<td>录入员工</td>
				<td>录入时间</td>
			</tr>
			<c:forEach var="insu_carhost" items="${page.list}">
				<tr>
					<td>${insu_carhost.carhost.carHostName}</td>
					<td>${insu_carhost.carhost.carHostCode}</td>
					<td>${insu_carhost.insu.carCode}</td>
					<td>${insu_carhost.insu.user__username}</td>
					<td>${insu_carhost.insu.inputDateTime}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="1" align="left">共${page.totalRows}条记录</td>
				<td colspan="3" align="center"><input name="action:sale!gotoSale" type="submit" value="继续销售"></td>
				<td colspan="1" align="left">共${page.totalPages}页</td>
			</tr>
			<tr>
				<td colspan="5" align="center">${y:page("sale!returnToSaleList.action",page)}</td>
			</tr>
		</table>
	</form>
	</body>
</html>