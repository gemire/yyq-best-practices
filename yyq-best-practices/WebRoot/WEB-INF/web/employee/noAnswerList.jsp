<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="y" uri="http://www.yyq.com/jstl"%>

<html>
	<head>
		<title>未处理客户留言列表</title>
		<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
		<script type="text/javascript">
			var ckEditor;
			function selectFeedback(fid,index){
				$("#fid").val(fid);
				ckEditor.setData($$("t"+index).value);
			}
		</script>
	</head>
	<body>
	<form action="sale!noAnswerList.action" method="post">
		<table class="stripeMe">
			<thead>
			<tr>
				<th>选择</th>
				<th>标题：</th>
				<th>姓名：</th>
				<th>电子邮箱：</th>
				<th>联系电话：</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="f" items="${page.list}" varStatus="i">
			<tr>
				<td><input type="radio" name="fid" onclick="selectFeedback('${f.id}','${i.index}')"><div style="display:none"><textarea id="t${i.index}">${f.content}</textarea></div></td>
			<script type="text/javascript">
				CKEDITOR.replace("t${i.index}",{toolbar:'Basic'});
			</script>
				<td>${f.title}</td>
				<td>${f.name}</td>
				<td>${f.email}</td>
				<td>${f.linkTel}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="center">${y:page("sale!returnToSaleList.action",page)}</td>
			</tr>
			</tbody>
		</table>
	</form>
	<form action="sale!answerFeedback.action" method="post">
		<table>
			<tr>
				<td>留言内容：</td>
				<td width="500px">
					<div id="div_b"><textarea id="fcontent" rows="10" cols="10"></textarea></div>
			<script type="text/javascript">
				ckEditor=CKEDITOR.replace("fcontent",{toolbar:'Basic',height:100});
			</script>
			${y:coverDiv("div_t","div_b")}
				</td>
			</tr>
			<tr>
				<td>答复内容：</td>
				<td>
					<textarea name="feedback.answer" rows="8" cols="60"></textarea>
					<button type="submit">确认回复</button>
				</td>
			</tr>
		</table>
		<input type="hidden" name="feedback.id" id="fid">
	</form>
	</body>
</html>