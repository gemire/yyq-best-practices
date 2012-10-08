<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<table>
	<tr>
		<td>姓名</td>
		<td><input name="feedback.name" id="name" value="${feedback.name}" class="{required:true}"></td>
	</tr>
	<tr>
		<td>E-Mail</td>
		<td><input name="feedback.email" value="${feedback.email}"></td>
	</tr>
	<tr>
		<td>联系电话</td>
		<td><input name="feedback.linkTel" value="${feedback.linkTel}"></td>
	</tr>
	<tr>
		<td>留言标题</td>
		<td><input name="feedback.title" id="title" value="${feedback.title}" class="{required:true}"></td>
	</tr>
	<tr>
		<td>留言内容</td>
		<td width="400px"><textarea name="feedback.content" id="content">${feedback.content}</textarea></td>
	</tr>
	<script type="text/javascript">
		CKEDITOR.replace('content');
	</script>
</table>