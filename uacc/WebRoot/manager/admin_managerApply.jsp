<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理申报人员</title>
<script language="javascript" src="<%=basePath%>/manager/js/jquery.js"></script>
<script src="<%=basePath%>/manager/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/manager/js/liyixiang/searchApply.js" type="text/javascript"></script>
 <script src="<%=basePath%>/manager/js/smartpaginator.js" type="text/javascript"></script>
 <link href="<%=basePath%>/manager/css/smartpaginator.css" rel="stylesheet" type="text/css" />
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
	left: 0px;
	right: 0px;
	bottom: 20px;
	
}
#left{
width:158px;
height:100%;
background:url(slide.jpg) repeat-y;
position:absolute;
left:0px;
}
#lhead{
background:url(manager/images/left-head.jpg) left top no-repeat;
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
padding:20px 0 0 0;
font-size:12px;
font-family:"宋体";
}
#centerDiv>#right{
width:auto;
height:auto;
position:absolute;
top:5px;
right:0px;
left:0px;
bottom:0px;
}
#topDiv{
	width:100%;
	height:56px;

	background:url(images/head-bg.jpg) repeat-x;
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
background:url(images/menu-bg.jpg) repeat-x;
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
line-height:100%;
margin:-20px 0 0 -10;
overflow:hidden;
}
#bottomDiv{
	width:100%;
	height:20px;
	background:url(images/bottom.jpg) repeat-x;
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
background:url(images/menu-bg.jpg) repeat-x;
color:#FFFFFF;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
border:1px #06597D solid;
}
#left ul li a:hover{

background:url(images/menu-bg.jpg) 0px 25px;
color:#99FFCC;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
}
#form{
width:100%;
height:99%;
margin:0 auto;
_margin-left:0px;

}
fieldset{
width:100%;
margin:20 auto;
line-height:35px;
padding-left:20PX;
}
-->
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


<!--表格样式-->

<!--按钮样式-->
<style type="text/css">
input[type="submit"] {
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
#searchButton {
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
input[type="submit"]:hover {
    background-color: #F8F8F8;
    background-image: -moz-linear-gradient(center top , #F8F8F8, #F1F1F1);
    border: 1px solid #C6C6C6;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    color: #333333;
}
input[type="submit"]:focus {
    border: 1px solid #4D90FE;
    outline: medium none;
}

input[type="text"] {
height:27px;
border:1px solid  #0066FF;
padding:0 6px;
width:400px;
}
</style>
<link rel="stylesheet" href="<%=basePath%>/manager/css/jquery.ui.all.css">
	<script src="<%=basePath%>/manager/js/jquery-1.9.1.js"></script>
	<script src="<%=basePath%>/manager/js/jquery-ui.js"></script>
	<script src="<%=basePath%>/manager/js/jquery.ui.core.js"></script>
<script src="<%=basePath%>/manager/js/smartpaginator.js" type="text/javascript"></script>
 
 
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
	    <div id="current" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <span><font color="red">请管理员认真核对申报信息</font></span>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    输入申报人员账号:<input type="text"   id="ApplyUserName">
	     
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            
     <input name="input" type="button" value="搜索" onclick="searchApply();" id="searchButton"/>
     <input name="input" type="submit" value="导出"  onClick="exportExcel('<%=basePath%>') "/>
        </div>
	    <div id="form">


  <fieldset>
		<legend>导入人员规则与提醒:</legend>
	
		 <br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.需要上传各大高校的名单,必须为excel文件。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.<a href="<%=basePath%>manager/exmple.xls">模版下载。</a><br/>
        <form method="POST" onsubmit="return check_file();" enctype="multipart/form-data" action="<%=basePath%>manager/addApplicant.do">
                  选择文件: <input type="file" name="upfile" id="fileToUpload" onchange="check_file()"><br/>
         <input type="submit" value="上传" > 
        </form>
 </fieldset>
 <fieldset>
		<legend>删除申报人员规则与提醒:</legend>
	
		<label for="username">需满足如下条件:</label>
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.申报人员造假,冒充使用不合法方式参加比赛。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.管理员与申报人员做了准确的核实。<br/>
          
</fieldset>
 <form action="../deleteApply.do" method="post">
  <table  width="100%" cellspacing="1"    id="customers" margin=0>

	<tr><th><div align='center'>用户名</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团</div></th><th><div align='center'>带队老师</div></th><th><div align='center'>电话</div></th><th><div align='center'>邮箱</div></th><th><div align='center'>操作</div></th></tr>
  
</table>
</form>
</div>
</div>
</div>
</div>

<script language="javascript">
$("#test1").toggle(function(){$("#test").slideDown()},function(){$("#test").slideUp()})
 
  function check_file(){
     
        str=document.getElementById('fileToUpload').value.toUpperCase();
        suffix=".xls"; 
        
        if(!(str.indexOf(suffix.toUpperCase(), str.length - suffix.length) != -1 || str.indexOf(suffix, str.length - suffix.length) != -1)){
        alert('请上传.xls格式的excel文件!');
           document.getElementById('fileToUpload').value='';
           return  false;
        }
    }
    
    function exportExcel(url){
   
      win  = window.open(url+"manager/exportExcel.do?type=apply");
	    win.document.execCommand("SaveAs") ;
   
     }
    
</script>

</body>
</html>