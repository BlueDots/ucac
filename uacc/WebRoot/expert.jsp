<%@ page pageEncoding = "UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "com.ucac.po.*"  %>
<!DOCTYPE html>
<html lang="en">
<head>
<%
if(session.getAttribute("expert") == null){
	response.sendRedirect("login.jsp");
}
 %>
<title>活动</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
    <!-- messenger  -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger-theme-future.css">
    <script type="text/javascript" src="js/messenger.min.js"></script>
    <script type="text/javascript" src="js/messenger-theme-future.js"></script>
<script>
$(document).ready(function() {
var pages = (<%= request.getParameter("pages") == null?1:request.getParameter("pages")%>);
	show(pages);
});
function show(nowpage) {
      var imath=Math.random();
		$.getJSON(
			   "ShowExpertRecord.do?run="+imath,
			  { 
			    expertId:<c:out value="${expert.id }"></c:out>,
			    category:<c:out value="${expert.category }"></c:out>,
			    nowpage:nowpage,
			    pageSize:5,
			    isFront:true
			  },
			   function(json){
				  var htmlStr = "<table  style='margin-left:5.4cm;width:69.8%;' class='table table-hover' >"+
					"<tr>"+
					"<th>编号</th>"+
					"<th>类别</th>"+
					"<th>评审</th>"+
					"<th>视频分数</th>"+
					"<th>展板分数</th>"+
					"<th>材料分数</th>"+
					"<th>最后得分</th>"+
					"</tr>";
				if(json.length>0) {
					for(var i = 0;i<json.length-1;i++) {
						
						if(json[i].videoScore == 0.0){
							  json[i].videoScore = " ";
							  json[i].pictureScore = " ";
							  json[i].documentScore = " ";
							  json[i].score = " ";
						 }
						if(json[i].category == 1) {
							json[i].category = "理论学习类";
						}else if(json[i].category == 2) {
							json[i].category = "学术科技类";
						}else if(json[i].category == 3){
							json[i].category = "文艺体育类";
						}else if(json[i].category == 4) {
							json[i].category = "社会公益类";
						}else if(json[i].category == 5) {
							json[i].category = "实践创新类";
						}else if(json[i].category == 6) {
							json[i].category = "其他区类";
						}
						if(json[i].videoScore == " ")
							htmlStr +="<tr class='warning'>";
						else { 
							htmlStr +="<tr class='success'>";
						}
						htmlStr += "<td>"+((nowpage-1)*json[json.length-1].pageSize+i+1)+"</td>"+
						"<td>"+json[i].category+"</td>";
						if(json[i].videoScore == " ")
							htmlStr += "<td><a href = 'score.jsp?applicantId="+json[i].applicantId+"&expertId="+<c:out value="${expert.id }"></c:out>+"&pages="+json[json.length-1].nowpage+"'>评分</a></td>";
						else  htmlStr += "<td><a href = 'score.jsp?applicantId="+json[i].applicantId+"&expertId="+<c:out value="${expert.id }"></c:out>+"&id="+json[i].id+"&pages="+json[json.length-1].nowpage+"'>修改</a></td>";	
						htmlStr += "<td>"+json[i].videoScore+"</td>"+
						"<td>"+json[i].pictureScore+"</td>"+
						"<td>"+json[i].documentScore+"</td>"+
						"<td>"+json[i].score+"</td>"+
						"</tr>";
					}
				}
				htmlStr+="</table>";
				//alert(htmlStr);
				$("#scoreform").html(htmlStr);
				$("#total").html("<font color = 'red'>"+json[json.length-1].total+"</font>");
				$("#finished").html("<font color = 'red'>"+json[json.length-1].finished+"</font>");
				var pages = parseInt((json[json.length-1].total + json[json.length-1].pageSize - 1)/json[json.length-1].pageSize);
				var nowPage = json[json.length-1].nowpage;
				var pageStr = "<li><a href='#' onClick = '"+"show("+(nowPage <=1 ? 1:(nowPage-1))+")"+"'>上一页</a></li>";
				
				for(var i=0;i<pages;i++) {
					pageStr += "<li><a href='#' onClick = 'show("+(i+1)+")'>"+(i+1)+"</a></li>";
				}
				pageStr += "<li><a href='#' onClick = '"+"show("+(nowPage >=pages ? pages:(nowPage+1))+")"+"'>下一页</a></li>";
				$("#page").html(pageStr);
			  } 
			);
}


</script>

<style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }
      #cjtc {
		margin-left: 213px;
		margin-top:-10px;

      }
      #exit {
      	margin-left: 575px;
		margin-top:-10px;
      }
      div.hero-unit {
      	margin-left: -130px;
		margin-top:0px;
        height: 20px;
        width: 90px;
        background-color: rgba(33, 235, 227, 0.43);
      }  
      div#form{
      	margin-left: 10px;
		margin-top:-1px;
		height: 250px;
        width: 1330px
      }    
       #info p {
        font-size: 12px;
        text-align:center;
      }
      #info h4 {
        font-size: 14px;
        text-align:center;
      }     
</style>

<script type="text/javascript">
	function prompt() {
	var total = $("#total").text();
	var finished = $("#finished").text();
	var remader = total-finished;
	$.get("logout.do");
	if(remader>0){
		alert("还有"+remader+"个作品未评选！");
	}
	else {
		alert("已评完，老师辛苦了！");
	}
    }
</script>
</head>
<body>
<div class="container">
	<div id="logo"><img src="img/logo.gif"></div>
 
  
  <div class="navbar"  style="margin-top:15px;">
    <div class="navbar-inner"> 
      <ul class="nav">
        <li class="active"><a href="#">首页</a></li>
        <li><a href="expert.jsp?pages=1">作品列表</a></li>
      </ul>
    </div>
  </div>
</div>

<div id = "cjtc">

共有<span id = "total"></span>个作品需评，已评选<span id = "finished"></span>个
<font color="red">(打分制度为百分制,分数范围是:1-99,最多保留两位小数,谢谢合作!)</font>
</div>
<div class="row-fluid">
  <div class="span11">

  	<div id="form">
<form action="admin-templates/" method="post" >
	<div id = "scoreform">
	</div>
</form>
<div style="margin-left:5.4cm;" class="pagination">
  <ul id = "page">

  </ul>
</div>
</div>	
  </div>
  <div class="span1" >
  	      <div class="hero-unit">
        <div id = "info">
          <p><font color = "red">${expert.expertName}</font>，欢迎您</p>
          <h4><a href="login.jsp" onclick = "prompt()">退出</a></h4>
        </div>
      </div>
  </div>
</div>

<script type="text/javascript">







</script>

</body>
<script type="text/javascript" src="js/checkBrowers.js"></script>
</html>
