<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台设置</title>
 

<link rel="stylesheet" href="<%=basePath%>manager/css/jquery-ui.css">

	<script src="<%=basePath%>manager/js/jquery-1.9.1.js"></script>
	<script src="<%=basePath%>manager/js/jquery.ui.core.js"></script>
	 <script src="<%=basePath%>manager/js/jquery-ui.js"></script>
	<link rel="stylesheet" href="<%=basePath%>manager/css/demos.css">
 
	
 
 
 
  <script type="text/javascript" src="<%=basePath%>manager/js/yiwei/admin_manage.js"></script>
 <script type="text/javascript" src="<%=basePath%>manager/js/yiwei/stringformat.js"></script>

<style type="text/css">
<!--
* { 
	margin:0px; 
	padding:0px;
}
html, body{	
	height:100%;
	overflow: hidden;

}
html>body{		/*-- for !IE6.0 --*/
	width: auto;
	height: auto;
	position: absolute;
	top: 0px;
	left: 0px;
	right: 0px;
	bottom: 0px;
	
}
body {
	border:0px solid #ffffff;
	background-color: #ffffff;min-width:230px;
}
#mainDiv {
	width: 100%;
	height: 100%;
    padding:60px 0px 25px 0px;;
	
	
}
#centerDiv{
	width: 100%;
	height: 100%;
	background-color:#00CCFF;
	padding-left:0px;
}
#mainDiv>#centerDiv{		/*-- for !IE6.0 --*/	
	width: auto;
	height: auto;
	position: absolute;
	top: 56px;
	left: -180px;
	right: 0px;
	bottom: 20px;
	
}
#left{
width:158px;
height:100%;
background:url(<%=basePath%>manager/images/slide.jpg) repeat-y;
position:absolute;
left:0px;
}
#head{
background:url(<%=basePath%>manager/images/left-head.jpg) left top no-repeat;
height:25px;
	font-size:14px;
	color:#FF9933;
    text-align:center;
	line-height:25px;
}
#right{
width:100%;
height:100%;
background:#ffffff;
position:absolute;
overflow-y:auto;
border:1px #003366 solid;
padding:20px 0 0 10px;
font-size:12px;
font-family:"宋体";
}
#centerDiv>#right{
width:auto;
height:auto;
position:absolute;
top:13px;
right:0px;
left:130px;
bottom:0px;
}
#topDiv{
	width:100%;
	height:56px;

	background:url(<%=basePath%>manager/images/head-bg.jpg) repeat-x;
	position:absolute;
	top:0px;
	overflow:hidden;
}
#topDiv ul{
list-style:none;
font-size:12px;

width:100%;
margin:0;
padding:0;
}
#topDiv ul li{
float:left;
width:12%
}
#topDiv ul li a {
display:block;
width:100%;
height:25px;
line-height:25px;
background:url(<%=basePath%>manager/images/menu-bg.jpg) repeat-x;
color:#FFFFFF;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
border:1px #06597D solid;
}
#tmenu{
width:1169px;
position:absolute;
left:7%;
bottom:0;
padding-left:15%;
margin-left:-15%;
}
#current{
background:#ccc;
height:50px;
line-height:25px;
margin:-20px 0 0 -10;
overflow:hidden;
}
#bottomDiv{
	width:100%;
	height:20px;
	background:url(<%=basePath%>manager/images/bottom.jpg) repeat-x;
	position:absolute;
	bottom:0px;
	bottom:-1px;		 /*-- for IE6.0 --*/
}
#left ul{
list-style:none;
font-size:12px;
margin:50px 0 0 8px;
}
#left ul li a{
display:block;
width:140px;
height:25px;
line-height:25px;
background:url(<%=basePath%>manager/images/menu-bg.jpg) repeat-x;
color:#FFFFFF;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
border:1px #06597D solid;
}
#left ul li a:hover{

background:url(<%=basePath%>manager/images/menu-bg.jpg) 0px 25px;
color:#99FFCC;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
}
#form{
width:80%;
height:99%;
margin:0 auto;
_margin-left:20%;

}
fieldset{
width:100%;
margin:20 auto;
line-height:35px;
padding-left:20PX;
}
 
</style>
<!--表格样式-->
<style type="text/css">
#customers
  {
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  width:100%;
  border-collapse:collapse;
  }

#customers td, #customers th 
  {
  font-size:1em;
  border:1px solid #0099FF;
  padding:3px 7px 2px 7px;
  }

#customers th 
  {
  font-size:1.1em;
  text-align:left;
  padding-top:5px;
  padding-bottom:4px;
  background-color:#0099FF ;
  color:#ffffff;
  }

#customers tr.alt td 
  {
  color:#000000;
  background-color:#EAF2D3;
  }
</style>

<style type="text/css">
input[type="button"] {
    -moz-user-select: none;
    background-color: #F5F5F5;
    background-image: -moz-linear-gradient(center top , #F5F5F5, #F1F1F1);
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 2px 2px 2px 2px;
    color: #666666;
    cursor: default;
    font-family: arial,sans-serif;
    font-size: 11px;
    font-weight: bold;
    height: 29px;
    line-height: 27px;
    margin: 11px 6px;
    min-width: 54px;
    padding: 0 8px;
    text-align: center;
}
input[type="button"]:hover {
    background-color: #F8F8F8;
    background-image: -moz-linear-gradient(center top , #F8F8F8, #F1F1F1);
    border: 1px solid #C6C6C6;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    color: #333333;
}
input[type="button"]:focus {
    border: 1px solid #4D90FE;
    outline: medium none;
}

input[type="reset"] {
    -moz-user-select: none;
    background-color: #F5F5F5;
    background-image: -moz-linear-gradient(center top , #F5F5F5, #F1F1F1);
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 2px 2px 2px 2px;
    color: #666666;
    cursor: default;
    font-family: arial,sans-serif;
    font-size: 11px;
    font-weight: bold;
    height: 29px;
    line-height: 27px;
    margin: 11px 6px;
    min-width: 54px;
    padding: 0 8px;
    text-align: center;
}
input[type="reset"]:hover {
    background-color: #F8F8F8;
    background-image: -moz-linear-gradient(center top , #F8F8F8, #F1F1F1);
    border: 1px solid #C6C6C6;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    color: #333333;
}
input[type="reset"]:focus {
    border: 1px solid #4D90FE;
    outline: medium none;
}

input[type="button"] {
    -moz-user-select: none;
    background-color: #F5F5F5;
    background-image: -moz-linear-gradient(center top , #F5F5F5, #F1F1F1);
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 2px 2px 2px 2px;
    color: #666666;
    cursor: default;
    font-family: arial,sans-serif;
    font-size: 11px;
    font-weight: bold;
    height: 29px;
    line-height: 27px;
    margin: 11px 6px;
    min-width: 54px;
    padding: 0 8px;
    text-align: center;
}
input[type="button"]:hover {
    background-color: #F8F8F8;
    background-image: -moz-linear-gradient(center top , #F8F8F8, #F1F1F1);
    border: 1px solid #C6C6C6;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    color: #333333;
}
input[type="button"]:focus {
    border: 1px solid #4D90FE;
    outline: medium none;
}

input[type="text"] {
height:27px;
border:1px solid  #0066FF;
padding:0 6px;
width:200px;
}

select {
height:27px;
border:1px solid  #0066FF;
padding:0 6px;
}
</style>
<!--按钮样式结束-->
</head>
<body>
<div id="mainDiv">
	<div id="topDiv">
	<div id="tmenu"><ul>
<li><a href="<%=basePath%>manager/admin_index.jsp">首页</a></li>
					<li><a href="<%=basePath%>manager/admin_manage.jsp">系统设置管理</a></li>
					<li><a href="<%=basePath%>manager/admin_assessWork.jsp">作品内容审查</a></li>
					<li><a href="<%=basePath%>manager/admin_viewResult.jsp">比赛结果查看</a></li>
					<li><a href="<%=basePath%>manager/admin_ExpertInfo.jsp">专家账号查看</a></li>
					<li><a href="<%=basePath%>manager/admin_log.jsp">日志审查</a></li>
					<li><a href="<%=basePath%>manager/admin_viewMangeAction.jsp">专家打分记录</a></li>
					<li><a href="<%=basePath%>manager/admin_managerApply.jsp">管理申报人员</a></li>
	</ul></div></div>
	<div id="centerDiv">
	  <div id="right"> 
	
<div id="form"  style="margin-top:-10px ; width=1150px ; margin-left:100px">
<fieldset>
    <legend>设置须知:</legend>
  
    <label for="username">设置规则如下:</label>
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.三项设置内容可以不在同一时间内同时设定
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    2.评审开始时间一定要设定在申报截止时间之后&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.各项材料得分比例必须在评审开始时间之前设定好，即评审开始时间之前可以修改<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.在报名开始时间开始之前可以修改任何设定内容，在报名开始时间之后不可再修改报名开始时间，评审时间的设定规则也是如此！
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.请管理人员认真按规则操作！！
</fieldset>
<fieldset>
    <legend>报名时间设定:</legend>
    <form method="post" name="applyForm">
    <input type="hidden" name="formId" value="1" />
    <div class="applySetting"></div>
    <div style="margin-left:530px">
      <input type="button" onclick="modifyApplyTime()" value="修改">
    </div>
    </form>
</fieldset>
<fieldset>
    <legend>评审时间设定:</legend>
    <form method="post" name="appraiseForm">
    <input type="hidden" name="formId" value="2"/>
    <div class="appraiseSetting"></div>
   <div style="margin-left:530px">
      <input type="button" onclick="modifyAppraiseTime()" value="修改">
    </div>
    </form>
</fieldset>
<div style="margin-top:20px;width=1150px">
<fieldset>
    <legend>各材料评分比例设定:</legend>
     <form method="post" name="proportionForm">
     <input type="hidden" name="formId" value="3"/>
     <div class="proportionSetting"></div>
    <div style="margin-left:530px">
      <input type="button" onclick="modifyProportion()" value="修改">
    </div>
    </form>
</fieldset>
</div>
</div>
</div>

</div>

</div>

<script type="text/javascript">
$("#test1").toggle(function(){$("#test").slideDown();},function(){$("#test").slideUp();});

</script>
<script src="<%=basePath%>manager/js/date.js" type="text/javascript"></script>
 
</body>
</html>
