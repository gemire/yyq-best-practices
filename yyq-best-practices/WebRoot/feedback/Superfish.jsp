<%@page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>jquery Superfish插件演示</title>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../js/superfish-1.4.8.js"></script>
		<script type="text/javascript">
			$().ready(function(){
				$("#superFish").superfish();
				//菜单变为横向(默认)
				//$('#superFish').removeClass('sf-vertical');
				//菜单变为竖向
				//$('#superFish').addClass('sf-vertical');
			});
		</script>
		<link rel="stylesheet" type="text/css" href="../css/superfish.css"/>
		<link rel="stylesheet" type="text/css" href="../css/superfish-navbar.css"/>
		<link rel="stylesheet" type="text/css" href="../css/superfish-vertical.css"/>
	</head>
	<body>
<ul id="superFish" class="sf-menu">
	<li><a href="#">Java学习</a>
		<ul>
			<li><a href="#">JavaEye</a>
				<ul>
					<li><a href="http://www.javaeye.com/news">JavaEye新闻频道</a></li>
					<li><a href="http://www.javaeye.com/blogs/list">最近博客文章</a></li>
				</ul>
			</li>
			<li><a href="http://www.open-open.com/">Java开源大全</a></li>
			<li><a href="http://www.blogjava.net/">BlogJava</a></li>
		</ul>
	</li>

	<li><a href="#">实用网站</a>
		<ul>
			<li><a href="http://www.ctrip.com/">携程旅行网</a></li>
			<li><a href="http://www.xmrc.com.cn/">厦门人才网</a></li>
		</ul>
	</li>
</ul>

	</body>
</html>
