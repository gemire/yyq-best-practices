<%@ page language="java" import="com.yyq.util.SSUtil" pageEncoding="utf-8"%>
	<div id="date_div" style="position:absolute;display:none;">
		<table width="320" border="1" bgcolor="#BFEFFF">
			<tr align="center">
				<td><input type="button" value="《" onclick="date_changeYear(-1)"></td>
				<td><input type="button" value="〈" onclick="date_changeMonth(-1)"></td>
				<td colspan="2"><label id="date_yearAndMonth" ></label></td>
				<td><input type="button" value="〉" onclick="date_changeMonth(1)"></td>
				<td><input type="button" value="》" onclick="date_changeYear(1)"></td>
				<td><input type="button" value="关闭" onclick="date_hide()"></td>
			</tr>
			<tr>
				<td  colspan="7">
					<label id="date_Date"></label>
				</td>
			</tr>
			
			<tr>
		</table>
	</div>
<script type="text/javascript">
	var date_year;
	var date_month;
	var date_nowDate=new Date();
	var date_day;
	var date_week;
	var date_dateText;
	function date_showDate(obj){
		date_dateText=obj;
		var d=new Date();
		date_year=d.getYear();
		date_month=d.getMonth();
		date_day=d.getDate();
		date_week=d.getDay();
		date_drawYearAndMonth();
		date_drawDate();
		
		var date_div=document.getElementById("date_div");
		var selects=document.getElementsByTagName("select"); 
		for(i=0;i <selects.length;i++){
			selects[i].style.display="none";
		}
		date_div.style.display="block";
		date_div.style.left=getLeft(obj);
		date_div.style.top=getTop(obj)+obj.offsetHeight;
	}
	function date_drawYearAndMonth(){
		var monthArray=new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二");
		var yearAndMonthString=date_year+"年"+monthArray[date_month]+"月";
		document.getElementById("date_yearAndMonth").innerHTML=yearAndMonthString;
	}
	function date_drawDate(){
		var dateString="<table border='1' width='100%'><tr align='center' bgcolor='#00FF00'><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td>";
		var days_Month=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
		if((date_year%400==0)||((date_year%4==0)&&(date_year%100!=0))){
			days_Month[1]=29;
		}
		var weekFirstDay=new Date(date_year,date_month,1).getDay();
		var nowDay=1;
		//dateString+="<tr>";
		for(var i=0;i<42;i++){
			if(i%7==0){
				dateString+="</tr><tr align='center' bgcolor='#EEC900'>";
				if(nowDay>days_Month[date_month]) break;
			}
			dateString+="<td>";
			if(i>=weekFirstDay&&nowDay<=days_Month[date_month]){
				if((nowDay==date_day)&&(date_year==date_nowDate.getYear())&&(date_month==date_nowDate.getMonth())){
					dateString+="<a href=javascript:date_setDate("+nowDay+")><font color='#FF0000'>"+nowDay+"</font></a>";
				}else{
					dateString+="<a href=javascript:date_setDate("+nowDay+")><font color='#0000EE'>"+nowDay+"</font></a>";
				}
				
				nowDay++;
			}
			dateString+="</td>";
		}
		//alert(2);
		dateString+="</tr>";
		dateString+="</table>";
		//alert(dateString);
		
		document.getElementById("date_Date").innerHTML=dateString;
	}
	
	function date_changeMonth(i){
		date_month+=i;
		if(date_month>11){
			date_month-=12;
			date_year++;
		}else if(date_month<0){
			date_month+=12;
			date_year--;
		}
		date_drawYearAndMonth();
		date_drawDate();
	}
	
	function date_changeYear(i){
		date_year+=i;
		date_drawYearAndMonth();
		date_drawDate();
	}
	
	function date_setDate(day){
		var n_month=date_month+1;
		var n_day=day;
		if(n_month<10) n_month="0"+n_month;
		if(n_day<10) n_day="0"+n_day;
		date_dateText.value=date_year+"-"+n_month+"-"+n_day;
		date_hide();
	}
	
	function date_hide(){
		var selects=document.getElementsByTagName("select"); 
		for(i=0;i <selects.length;i++){
			selects[i].style.display="";
		}
		document.getElementById("date_div").style.display="none";
	}
	
	function getLeft(obj){
		if(obj.offsetParent){
			return obj.offsetLeft+getLeft(obj.offsetParent);
		}else{
			return obj.offsetLeft;
		}
	}
	
	function getTop(obj){
		if(obj.offsetParent){
			return obj.offsetTop+getTop(obj.offsetParent);
		}else{
			return obj.offsetTop;
		}
	}
</script>