<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>图片管理页面</title>
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.cycle.all-2.72.js"></script>
		<script type="text/javascript" src="js/jquery.jqzoom1.0.1.js"></script>
		<script type="text/javascript">
			var x;
			var y;
			function start(ev){
				ev = ev || window.event;
				var mousePos = mousePosition(ev);
				x=mousePos.x;
				y=mousePos.y;
				//window.forward() parent.forward()
				//xxx.value=mousePos.x;
				
			}
			function mouseMove(ev){
				ev = ev || window.event;
				var mousePos = mousePosition(ev);
				var xx=mousePos.x-x;
				var yy=mousePos.y-y;
				if(xx>10) xx=10;
				if(xx<-10) xx=-10;
				if(yy>10) yy=10;
				if(yy<-10) yy=-10;
				if((topDiv.offsetLeft+xx<=0)&&(topDiv.offsetLeft+xx>document.body.clientWidth-topDiv.offsetWidth))
					topDiv.style.left=topDiv.offsetLeft+xx;
				if((topDiv.offsetTop+yy<=0)&&(topDiv.offsetTop+yy>document.body.clientHeight-topDiv.offsetHeight))
					topDiv.style.top=topDiv.offsetTop+yy;
			}
			function mousePosition(ev){
				if(ev.pageX || ev.pageY){
					return {x:ev.pageX, y:ev.pageY};
				};
				return {
					x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
					y:ev.clientY + document.body.scrollTop  - document.body.clientTop
				};
			}
			function showDiv(src){
				topDiv.style.left=0;
				topDiv.style.top=0;
				bigPic.src=src;
				$(topDiv).show();
			}
			
			function hideDiv(){
				$(topDiv).hide();
			}
			
			//以前用这个方法来在页面上缩小图片，现在是在页面上显示缩略图，此方法暂时不用。
			function autoSize(obj,MaxWidth,MaxHeight){
				if(obj.width()/obj.height()>=MaxWidth/MaxHeight){
					obj.width(MaxWidth);
					obj.height((MaxWidth/obj.width())*obj.height());
				}else{
					obj.height(MaxHeight);
					obj.width((MaxHeight/obj.height())*obj.width());
				}
				//style="width:192px;height:140px;"
			}
			$().ready(function(){
				$(".slideshow").cycle({
					fx: 'shuffle',
					delay: -2000
				});
				<c:forEach items="${list}" varStatus="num">
					$("#jqzoom${num.index}").jqzoom(jqzoom_options);
					//autoSize($("#div${num.index}"),192,140);
				</c:forEach>
			});
			var jqzoom_options =
			{
				zoomWidth: 360,
				zoomHeight: 210,
				showEffect: 'fadein',
				hideEffect: 'fadeout',
				fadeinSpeed: 'slow',
				fadeoutSpeed: 'slow',
				//lens:false,
				//title: false,
				zoomType:'reverse'
			}
			function checkFileType(inputFile){
				var patrn=new RegExp("jpg|gif|jpeg$");//限制文件类型
				if (!patrn.test(inputFile.value)){
					inputFile.outerHTML=inputFile.outerHTML;//清空input file
					alert("请选择图片文件！");
				}
			}
		</script>

	</head>
	
	<body>
		<table>
		<tr>
		<c:forEach items="${list}" var="p" varStatus="num">
			<c:if test="${num.index%5==0}">
				</tr><tr>
			</c:if>
			<td width="192" height="120" align="center">
			<div id="content" id="div${num.index}">
			<a href="pic/${p}" id="jqzoom${num.index}" onclick='showDiv(this.href)'>
			<img id="pic${num.index}" alt="显示" src="pic/smallPic/${p}">
			</a>
			</div>
			<br>
			<a href="userManage!picDelete.action?fileName=${p}">删除</a>
			</td>
		</c:forEach>
		</tr>
		</table>
		
		<br>
		<div id="topDiv" align="center" style="z-index:1000;display:none;position:absolute;background:#ccc;" onmousedown="start(event)" onclick="hideDiv()">
			<img id="bigPic" alt="关闭" ondrag="mouseMove(event)">
		</div>
		<form action="userManage!picUpload.action" method="post" enctype="multipart/form-data">
			请选择要上传的图片：<input type="file" name="file" onchange="checkFileType(this)"/>
			<br>
			<input type="submit" value="确定">
		</form>
		图片展示：<br>
		<div class="slideshow">
		<c:forEach items="${list}" var="p">
			<img src="pic/smallPic/${p}" width="192" height="120" />
		</c:forEach>
		</div>
		
	</body>
</html>
