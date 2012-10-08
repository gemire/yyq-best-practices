<%@ page language="java" import="com.yyq.util.SSUtil" pageEncoding="utf-8"%>

<html>
	<head>
		<title>保单销售页面</title>
		<script src='dwr/interface/EmpDWR.js'></script>
		<script type="text/javascript" src="js/jquery.validate.min-1.7.js"></script>
		<script type="text/javascript" src="js/jquery.metadata.js"></script>
		<script type="text/javascript">
			var carhosts;
			function findCarhost(){
				var carhostSelect=$("#carhostSelect");
				//如果车主信息的下拉列表框里的车主信息为空，去服务器取车主信息
				if(carhostSelect.length==1&&carhostSelect[0].value=="-1"){
					EmpDWR.findCarhost("<%=SSUtil.getName()%>",listCarhost);
				}
			}
			function listCarhost(data){//处理函数
				carhosts=data;
				var carhostSelect=$("#carhostSelect")[0];
				DWRUtil.removeAllOptions("carhostSelect");
				//重新构建车主信息的下拉列表框
				for(var i=0;i<data.length;i++){
					carhostSelect.options[i]=new Option(carhosts[i].carHostName,i);
				}
				changeCarhost();
			}
			function changeCarhost(){
				var carhost=carhosts[$("#carhostSelect")[0].value];
				if(carhost==undefined) return false;
				//alert(carhost.carHostName);
				DWRUtil.setValue("id",carhost.id);
				DWRUtil.setValue("carHostName",carhost.carHostName);
				DWRUtil.setValue("carHostCode",carhost.carHostCode);
				DWRUtil.setValue("sex",carhost.sex);
				DWRUtil.setValue("age",carhost.age);
				DWRUtil.setValue("address",carhost.address);
				DWRUtil.setValue("linkTel",carhost.linkTel);
			}
			$().ready(function(){
				$("#newForm").validate();
			});
		</script>
	</head>
	<body>
	<form id="newForm" action="sale!sale.action" method="post">
		<table>
			<tr bgcolor="lightblue"><td colspan="2">保单信息</td></tr>
			<tr><td>保单开始时间</td><td><input name="insu.insurDateTimeBegin" class="{required:true}" onclick="date_showDate(this)"></td></tr>
			<tr><td>保单结束时间</td><td><input name="insu.insurDateTimeEnd" class="{required:true}" onclick="date_showDate(this)"></td></tr>
			<tr><td>保费</td><td><input name="insu.sumFee" class="{required:true}"></td></tr>
			<tr><td>车牌号</td><td><input name="insu.carCode" class="{required:true}"></td></tr>
			<tr/>
			<tr bgcolor="lightblue">
			<td>车主信息</td>
			<td><select id="carhostSelect" onclick="findCarhost()" onchange="changeCarhost()"><option value="-1">查找车主</option></select></td>
			</tr>
			<tr><td>姓名</td><td><input id="carHostName" name="host.carHostName" class="{required:true}"></td></tr>
			<tr><td>证件号</td><td><input id="carHostCode" name="host.carHostCode" class="{required:true}"></td></tr>
			<tr><td>性别</td>
			<td><select id="sex" name="host.sex"><option value="男">男</option><option value="女">女</option></select></td>
			</tr>
			<tr><td>年龄</td><td><input id="age" name="host.age" class="{required:true}"></td></tr>
			<tr><td>地址</td><td><input id="address" name="host.address"></td></tr>
			<tr><td>联系电话</td><td><input id="linkTel" name="host.linkTel"></td></tr>
			<tr align="center">
				<td colspan="1">
					<input type="submit" value="确定销售"/>
				</td>
				<td colspan="1">
					<input type="button" value="返回" onclick="javascript:history.go(-1)"/>
				</td>
			</tr>
		</table>
		<input type="hidden" id="id" name="host.id" value="">
	</form>
	<jsp:include page="/date.jsp"/>
	
	</body>
</html>