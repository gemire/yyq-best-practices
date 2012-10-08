<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>部门销售报表</title>
	</head>
	<body>
		<form method="post">
		部门销售报表：
		<table width="99%"><tr height="99%" valign="top">
			<td><br><br>
				<table class="stripeMe">
					<thead>
					<tr>
						<th>用户名</th>
						<th>员工姓名</th>
						<th>销售量</th>
						<th>销售额</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="deptReport" items="${deptReportList}">
					<tr>
						<td>${deptReport.username}</td>
						<td>${deptReport.name}</td>
						<td>${deptReport.sumInsu}</td>
						<td>${deptReport.sumFee}</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="2">
							<input name="action:dept!getExcel" type="submit" value="获得部门销售报表(EXCEL)">
						</td>
						<td colspan="2">
							<input name="action:dept!getPDF" type="submit" value="获得部门销售报表(PDF)">
						</td>
					</tr>
					</tbody>
				</table>
			</td>
			<td>
				<img alt="部门销售情况统计图" src="jfeechart/displaychart?filename=${jfc}">
			</td>
		</tr></table>
				
		
		</form>
	</body>
</html>