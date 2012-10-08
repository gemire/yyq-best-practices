<%@ page language="java" import="com.yyq.util.SSUtil" pageEncoding="utf-8"%>
<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/Liquid.js"></script>
		<script type="text/javascript">
			function show(){
				var li = document.getElementById("li");
				var fx=Liquid({
					scale:2000,
					speed: 10,
					src:"welcomePIC.jpg",
					target:li,
					direction:"bottom",
					callback:function(){showWelcome();}
				});
				//fx.play();
    	}
    	function showWelcome(){
    		$(welcome).fadeIn("slow");
    	}
    	function reShow(){
    		var li = document.getElementById("li");
    		li.innerHTML="";
    		show();
    	}
		</script>
	</head>
	<body onload="show()" oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' onmouseup='document.selection.empty()'>
		<div id="li"></div>
		<p>
			<label id="welcome" style="display: none" onclick="reShow()">欢迎您！</label>
		</p>
		<script type="text/javascript">
			show();
		</script>
	</body>
</html>