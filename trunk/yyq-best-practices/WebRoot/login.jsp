<%@ page language="java" import="com.yyq.util.RSAUtil,com.yyq.util.DateUtil" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
	<head>
		<title>请先登录！</title>
		<meta http-equiv="Refresh" content="60" >
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript" src="js/md5-min.js"></script>
		<script type="text/javascript" src="js/sha1-min.js"></script>
		<script type="text/javascript">
			//window.setInterval(function reflash(){window.location.href=window.location.href;},60000);
			function check(){
				//if("<sec:authentication property='name'/>"!="roleAnonymous") window.location.href="welcome.jsp";
				//if(window.parent.location.href!="http://localhost:8080/YYQ/") window.parent.location.href="http://localhost:8080/YYQ/";
			}
			var p="";
			var isDivShow=false;
			function charInput(obj){
				var c=obj.innerHTML;
				p=p+c;
				$("#password").val($("#password").val()+"*");
			}
			function ok(){
				var nowTime=$("#nowTime").val();
				$("#j_password").val(hex_sha1(hex_md5(p)+nowTime));
				$("#charDiv").hide();
				isDivShow=false;
				$("#captchafield").focus();
			}
			function backspace(){
				$("#password").val($("#password").val().substring(0,$("#password").val().length-1));
				p=p.substr(0,p.length-1);
			}
			function showCharDiv(){
				if(isDivShow==false){
					$("#password").val("");
					p="";
					isDivShow=true;
				}
				$("#charDiv").css("left",$("#password").offset().left);
				$("#charDiv").css("top",$("#password").offset().top+$("#password").height()+5);
				$("#charDiv").show();
			}
		</script>
<style type="text/css">
.charTd{
	background: silver;
	color: black;
	width: 24;
	height: 16;
	text-align: center;
}
</style>
	</head>

	<body onload="document.f.j_username.focus();">
		<h1>请先登录！</h1>
		<c:if test="${param.login_error==1}">
			<font color="red">
			用户名或密码错误，请重新输入！<br/><br/>
			</font>
		</c:if>
		<c:if test="${param.login_error==2}">
			<font color="red">
			验证码错误，请重新输入！<br/><br/>
			</font>
		</c:if>

		<form name="f" action="j_spring_security_check" method="POST">
		<table>
			<tr><td>用户名:</td><td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${param.j_username}"/></c:if>'/></td></tr>
			<tr><td>密码:</td><td><input id="password" type='password' name='password' onclick="showCharDiv()" onfocus="showCharDiv()" readonly="readonly"></td></tr>
			<tr><td>验证码</td><td><img src="SimpleCaptcha.jpg" onclick="this.src='SimpleCaptcha.jpg?date='+new Date()"><br><input type="text" name="captchafield" id="captchafield"></td></tr>
			<tr><td><input type="checkbox" name="_spring_security_remember_me"></td><td>两周里不用再输入</td></tr>
			<tr><td colspan='2'><input type="submit" value="确定"><input name="reset" type="reset" value="重置"></td></tr>
		</table>
		<input id="j_password" type="hidden" name="j_password">
		<input id="nowTime" type="hidden" name="nowTime" value="<%=DateUtil.nowTime()%>">
		</form>
		<div id="charDiv" style="display:none;position:absolute">
	<table border="3" bordercolor="gray" style="border-collapse:collapse;">
		<tr>
			<td class="charTd" onclick="charInput(this)">1</td>
			<td class="charTd" onclick="charInput(this)">2</td>
			<td class="charTd" onclick="charInput(this)">3</td>
			<td class="charTd" onclick="charInput(this)">4</td>
			<td class="charTd" onclick="charInput(this)">5</td>
			<td class="charTd" onclick="charInput(this)">6</td>
			<td class="charTd" onclick="charInput(this)">7</td>
			<td class="charTd" onclick="charInput(this)">8</td>
			<td class="charTd" onclick="charInput(this)">9</td>
			<td class="charTd" onclick="charInput(this)">0</td>
		</tr>
		<tr>
			<td class="charTd" onclick="charInput(this)">a</td>
			<td class="charTd" onclick="charInput(this)">b</td>
			<td class="charTd" onclick="charInput(this)">c</td>
			<td class="charTd" onclick="charInput(this)">d</td>
			<td class="charTd" onclick="charInput(this)">e</td>
			<td class="charTd" onclick="charInput(this)">f</td>
			<td class="charTd" onclick="charInput(this)">g</td>
			<td class="charTd" onclick="charInput(this)">h</td>
			<td class="charTd" onclick="charInput(this)">i</td>
			<td class="charTd" onclick="charInput(this)">j</td>
		</tr>
		<tr>
			<td class="charTd" onclick="charInput(this)">k</td>
			<td class="charTd" onclick="charInput(this)">l</td>
			<td class="charTd" onclick="charInput(this)">m</td>
			<td class="charTd" onclick="charInput(this)">n</td>
			<td class="charTd" onclick="charInput(this)">o</td>
			<td class="charTd" onclick="charInput(this)">p</td>
			<td class="charTd" onclick="charInput(this)">q</td>
			<td class="charTd" onclick="charInput(this)">r</td>
			<td class="charTd" onclick="charInput(this)">s</td>
			<td class="charTd" onclick="charInput(this)">t</td>
		</tr>
		<tr>
			<td class="charTd" onclick="charInput(this)">u</td>
			<td class="charTd" onclick="charInput(this)">v</td>
			<td class="charTd" onclick="charInput(this)">w</td>
			<td class="charTd" onclick="charInput(this)">x</td>
			<td class="charTd" onclick="charInput(this)">y</td>
			<td class="charTd" onclick="charInput(this)">z</td>
			<td class="charTd" onclick="ok()" colspan="2">确定</td>
			<td class="charTd" onclick="backspace()" colspan="2">←</td>
		</tr>
	</table>
	</div>
		<jsp:include page="/clock.jsp"/>
		<a href="#" onclick="window.location.href='feedback/main.jsp'">客户服务系统</a>
		<br>
		<a href="#" onclick="window.location.href='feedback/Superfish.jsp'">下拉菜单演示</a>
	</body>
</html>
