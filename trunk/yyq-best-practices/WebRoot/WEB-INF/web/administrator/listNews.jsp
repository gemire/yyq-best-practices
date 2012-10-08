<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>新闻列表</title>
	</head>
	<body>
		<table class="stripeMe">
			<thead>
				<tr>
					<th>序号</th>
					<th>新闻标题</th>
					<th>新闻简介</th>
					<th>新闻日期</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${newsList}" var="new" varStatus="num">
				<tr>
					<td>${num.index+1}</td>
					<td><a href="${new.href}">${new.title}</a></td>
					<td>${new.info}</td>
					<td>${new.date}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</body>
</html>