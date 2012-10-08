<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize ifAllGranted="ROLE_ADMIN">
<ul id="nav"><li>
	<a href="#" onclick="doMenu('childMenu1')"><strong>管理员模块</strong></a>
	<ul id="childMenu1">
		<li><a href="#" onclick="window.location.href='userManage!returnToUserList.action'">用户管理</a></li>
		<li><a href="#" onclick="window.location.href='userManage!saleStatistics.action'">所有员工月份销售情况统计</a></li>
		<li><a href="#" onclick="window.location.href='iframe.jsp?action=userManage!goToDeptManage'">部门管理</a></li>
		<li><a href="#" onclick="window.location.href='userManage!listPic.action'">图片管理</a></li>
		<li><a href="#" onclick="window.location.href='userManage!listNews.action'">查看新闻</a></li>
		<li><a href="#" onclick="window.location.href='iframe.jsp?action=userManage!goToCalculator'">计算器</a></li>
		<li><a href="#" onclick="window.location.href='iframe.jsp?action=userManage!goTo3DLink'">3D连连看</a></li>
		<li><a href="#" onclick="window.location.href='userManage!listMp3.action'">Mp3播放器</a></li>
		<li><a href="#" onclick="window.location.href='userManage!addList.action'">添加Mp3至播放列表</a></li>
		<li><a href="#" onclick="window.location.href='iframe.jsp?action=userManage!goToDAETest'">3D模型Web展示</a></li>
	</ul>
</li></ul>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_DEPT">
<ul id="nav"><li>
	<a href="#" onclick="doMenu('childMenu2')"><strong>部门经理模块</strong></a>
	<ul id="childMenu2">
		<li><a href="#" onclick="window.location.href='dept!findDeptReport.action'">部门报表</a></li>
	</ul>
</li></ul>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_SALE">
<ul id="nav"><li>
	<a href="#" onclick="doMenu('childMenu3')"><strong>销售人员模块</strong></a>
	<ul id="childMenu3">
		<li><a href="#" onclick="window.location.href='sale!gotoSale.action'">保单销售</a></li>
		<li><a href="#" onclick="window.location.href='sale!gotoInsuImprot.action'">销售保单导入</a></li>
		<li><a href="#" onclick="window.location.href='sale!returnToSaleList.action'">已售保单列表</a></li>
		<li><a href="#" onclick="window.location.href='sale!noAnswerList.action'">未处理的客户留言</a></li>
		<li><a href="#" onclick="window.location.href='iframe.jsp?action=sale!goToSumInsu'">统计自己的保单销售量</a></li>
	</ul>
</li></ul>
</sec:authorize>

<ul id="nav"><li>
	<a href="#" onclick="doMenu('childMenu4')"><strong>通用模块</strong></a>
	<ul id="childMenu4">
		<li><a href="#" onclick="window.location.href='common!goChatRoom.action'">系统聊天室</a></li>
		<li><a href="#" onclick="window.location.href='common!getWeather.action'">天气预报</a></li>
	</ul>
</li></ul>
