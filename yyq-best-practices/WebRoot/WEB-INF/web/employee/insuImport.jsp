<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title>MyJSP'insuImport.jsp'startingpage</title>
</head>
<body>
	<form action="sale!insuImport.action" method="post" enctype="multipart/form-data">
		<a href="download/Insu.xls">下载保单销售Excel模板</a><br/><br/>
		请选择保单销售Excel：<input name="file" type="file"/><button type="submit">导入</button>
	</form>
</body>
</html>
