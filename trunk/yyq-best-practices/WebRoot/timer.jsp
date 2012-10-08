<label id="clock" class="labelClass"></label>
					<script type="text/javascript">
						showtime();
						var date,nowTime;
						function showtime(){
							date=new Date();
							nowTime=date.getFullYear()+"-"+reviseLength(date.getMonth())+"-"+reviseLength(date.getDate())+" "+reviseLength(date.getHours())+":"+reviseLength(date.getMinutes())+":"+reviseLength(date.getSeconds());
							document.getElementById("clock").innerHTML=nowTime;
							window.setTimeout("showtime()",1000);
						}
						function reviseLength(data){
							if(data<10){
								return "0"+data;
							}
							return data;
						}
					</script>