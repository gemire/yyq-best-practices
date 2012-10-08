<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="y" uri="http://www.yyq.com/jstl"%>
<html>
  <head>
    <title>欢迎来到保险客服系统！</title>
    
    <script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min-1.7.js"></script>
		<script type="text/javascript" src="../js/jquery.metadata.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="../js/zebra.js"></script>
		<link rel="stylesheet" type="text/css" href="../css/zebra.css"/>
    <script type="text/javascript">
    	$().ready(function(){
				$("#newForm").validate();
			});
    </script>
    <link rel="stylesheet" type="text/css" href="../css/mycss.css"/>
    
  </head>
  <body>
  <a href="#" onclick="parent.location.href='/YYQ/'">返回保险销售管理系统</a>
  	<form method="post" action="feedback!listFeedback.action">
  		留言标题：<input name="keyWord" value="${keyWord}" type="text"><button type="submit">查找</button>
  		<c:if test="${page!=null}">
  			<table class="stripeMe">
  				${y:orderByTitle("标题,是否解决","title, ",page)}
  				<tbody>
  				<c:forEach var="feedback" items="${page.list}">
  				<tr>
  					<td><a href="feedback!findFeedback.action?id=${feedback.id}">${feedback.title}</a></td>
  					<td>${feedback.isOK}</td>
  				</tr>
  				</c:forEach>
  				</tbody>
  			</table>
  			<!-- 用自定义标签实现分页 -->
  			${y:page("feedback!listFeedback.action",page)}
  		</c:if>
  	</form>
    <form id="newForm" method="post" action="feedback!newFeedback.action">
    	<!-- 实践页面重用 -->
    	<jsp:include page="/common/feedback.jsp"/>
    	<table>
    		<tr>
    			<td>
    			<input type="submit" value="发布留言"/>
    			</td>
    		</tr>
    	</table>
    </form>
    <form id="jmsForm" method="post" action="feedback!sendJms.action">
    	<table>
    		<tr>
    			<td>
    			<textarea name="msg" cols="50" rows="10"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td>
    			<input type="submit" value="给管理员发信"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
