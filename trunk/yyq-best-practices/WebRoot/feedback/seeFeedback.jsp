<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="y" uri="http://www.yyq.com/jstl"%>
<html>
	<head>
		<title>留言详情</title>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="../js/myJs.js"></script>
  </head>
  <body>
    <div id="div_b">
    <jsp:include page="/common/feedback.jsp"/>
    <table>
  <tr>
		<td>是否回复</td>
		<td><input name="feedback.isOK" id="isOK" value="${feedback.isOK}" class="{required:true}"></td>
	</tr>
	<tr>
		<td>回复人</td>
		<td><input name="feedback.user__id" id="user__id" value="${feedback.user__id}" class="{required:true}"></td>
	</tr>
	<tr>
		<td>回复内容</td>
		<td width="400px"><textarea name="feedback.answer" id="answer" class="{required:true}">${feedback.answer}</textarea></td>
	</tr>
    </table>
    </div>
    <!-- 用透明div层遮盖进来的页面，防止用户修改文本框 -->
    ${y:coverDiv("div_t","div_b")}
    <input type="button" value="返回" onclick="history.go(-1);">
  </body>
</html>
