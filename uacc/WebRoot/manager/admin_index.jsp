<%@ page language="java" import="java.util.*,com.ucac.po.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

    <c:choose>
		<c:when test="${empty touristState}">
			<c:set var="page" value="admin" scope="request"/>
			<jsp:forward page="adminIndexInfoServlet.do"></jsp:forward>
		</c:when>
	</c:choose>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base >

<title>后台首页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=basePath%>manager/js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="<%=basePath%>manager/js/Laihuiqiang/backIndex.js" type="text/javascript"></script>
<style type="text/css">
{
margin:0px;
padding:0px;
}

html,body {
	height: 100%;
	overflow: hidden;
}

html>body { /*-- for !IE6.0 --*/
	width: auto;
	height: auto;
	position: absolute;
	top: 0px;
	left: 0px;
	right: 0px;
	bottom: 0px;
}

body {
	border: 0px solid #ffffff;
	background-color: #ffffff;
	min-width: 230px;
}

#mainDiv {
	width: 100%;
	height: 100%;
	padding: 60px 0px 25px 0px;;
}

#centerDiv {
	width: 100%;
	height: 100%;
	background-color: #00CCFF;
	padding-left: 0px;
}

#mainDiv>#centerDiv { /*-- for !IE6.0 --*/
	width: auto;
	height: auto;
	position: absolute;
	top: 56px;
	left: -180px;
	right: 0px;
	bottom: 20px;
}

#left {
	width: 158px;
	height: 100%;
	background: url(<%=basePath%>manager/images/slide.jpg) repeat-y;
	position: absolute;
	left: 0px;
}

#lhead {
	background: url(<%=basePath%>manager/images/left-head.jpg) left top no-repeat;
	height: 25px;
	font-size: 14px;
	color: #FF9933;
	text-align: center;
	line-height: 25px;
}

#right {
	width: 100%;
	height: 100%;
	background: #ffffff;
	position: absolute;
	overflow-y: auto;
	border: 1px #003366 solid;
	padding: 20px 0 0 10px;
	font-size: 12px;
	font-family: "宋体";
}

#centerDiv>#right {
	width: auto;
	height: auto;
	position: absolute;
	top: 5px;
	right: 0px;
	left: 133px;
	bottom: 0px;
}

#topDiv {
	width: 100%;
	height: 56px;
	background: url(<%=basePath%>manager/images/head-bg.jpg) repeat-x;
	position: absolute;
	top: 0px;
	overflow: hidden;
}

#topDiv ul {
	list-style: none;
	font-size: 12px;
	width: 100%;
	margin: 0;
	padding: 0;
}

#topDiv ul li {
	float: left;
	width: 12%
}

#topDiv ul li a {
	display: block;
	width: 100%;
	height: 25px;
	line-height: 25px;
	background: url(<%=basePath%>manager/images/menu-bg.jpg) repeat-x;
	color: #FFFFFF;
	direction: none;
	text-align: center;
	border-bottom: 1px #000066 solid;
	border: 1px #06597D solid;
}

#tmenu {
	width: 1169px;
	position: absolute;
	left: 7%;
	bottom: 0;
	padding-left: 15%;
	margin-left: -15%;
}

#current {
	background: #ccc;
	height: 50px;
	line-height: 25px;
	margin: -20px 0 0 -10;
	overflow: hidden;
}

#bottomDiv {
	width: 100%;
	height: 20px;
	background: url(<%=basePath%>manager/images/bottom.jpg) repeat-x;
	position: absolute;
	bottom: 0px;
	bottom: -1px; /*-- for IE6.0 --*/
}

#left ul {
	list-style: none;
	font-size: 12px;
	margin: 50px 0 0 8px;
}

#left ul li a {
	display: block;
	width: 140px;
	height: 25px;
	line-height: 25px;
	background: url(<%=basePath%>manager/images/menu-bg.jpg) repeat-x;
	color: #FFFFFF;
	direction: none;
	text-align: center;
	border-bottom: 1px #000066 solid;
	border: 1px #06597D solid;
}

#left ul li a:hover {
	background: url(<%=basePath%>manager/images/menu-bg.jpg) 0px 25px;
	color: #99FFCC;
	direction: none;
	text-align: center;
	border-bottom: 1px #000066 solid;
}

#form {
	width: 80%;
	height: 99%;
	margin: 0 auto;
	_margin-left: 20%;
}

fieldset {
	width: 100%;
	margin: 20 auto;
	line-height: 35px;
	padding-left: 20PX;
}
-->
</style>
<!--表格样式-->
<style type="text/css">
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#customers td,#customers th {
	font-size: 1em;
	border: 1px solid #0099FF;
	padding: 3px 7px 2px 7px;
}

#customers th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #0099FF;
	color: #ffffff;
}

#customers tr.alt td {
	color: #000000;
	background-color: #EAF2D3;
}

input[type="button"] {
	-moz-user-select: none;
	background-color: #F5F5F5;
	background-image: -moz-linear-gradient(center top, #F5F5F5, #F1F1F1);
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px 2px 2px 2px;
	color: #666666;
	cursor: default;
	font-family: arial, sans-serif;
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
	background-image: -moz-linear-gradient(center top, #F8F8F8, #F1F1F1);
	border: 1px solid #C6C6C6;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
	color: #333333;
}

input[type="button"]:focus {
	border: 1px solid #4D90FE;
	outline: medium none;
}

.STYLE1 {
	font-size: 20px;
	color: #000000;
}

.STYLE2 {
	color: #FF0000
}
</style>

</head>

<body>
	<div id="mainDiv">
		<div id="topDiv">
			<div id="tmenu">
				<ul>
				<li><a href="<%=basePath%>manager/admin_index.jsp">首页</a></li>
					<li><a href="<%=basePath%>manager/admin_manage.jsp">系统设置管理</a></li>
					<li><a href="<%=basePath%>manager/admin_assessWork.jsp">作品内容审查</a></li>
					<li><a href="<%=basePath%>manager/admin_viewResult.jsp">比赛结果查看</a></li>
					<li><a href="<%=basePath%>manager/admin_ExpertInfo.jsp">专家账号查看</a></li>
					<li><a href="<%=basePath%>manager/admin_log.jsp">日志审查</a></li>
					<li><a href="<%=basePath%>manager/admin_viewMangeAction.jsp">专家打分记录</a></li>
					<li><a href="<%=basePath%>manager/admin_managerApply.jsp">管理申报人员</a></li></ul>
			</div>
		</div>
		<div id="centerDiv">
			<div id="right">
				<div id="current">
					</br>
					<% Admin  admin = (Admin)request.getSession().getAttribute("admin") ;%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="STYLE1">管理员<%=admin.getUsername()%>,欢迎进入社团评比系统后台&nbsp;&nbsp;<span
						class="STYLE2"><a href="../logout.do?value=1">快速退出</a> </span> </span>
				</div>
				<div id="form">
					<fieldset>
						<legend>基本权限:</legend>

						<label for="username">您拥有的基本权限:</label> <br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.分配专家账号（账号
						密码自动生成，其它信息得手动录入）;<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.系统时间设置（报名开始时间，报名截止时间，评审开始时间，评审结束时间）;<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.比赛结果查看（可审核，导出，打印）;<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.专家账号信息查看（可导出，打印）;<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.信息备份与恢复（包括数据库与上传文件的备份与恢复）;<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.专家打分记录查看（查看某个专家打分记录，禁用账号等）;
					</fieldset>
					<fieldset>
						<legend>备份功能:</legend>
						<label for="boy">选择备份文件：</label> <select name="FileType" id="backupType"
							style="border:1px solid #0033FF">
							<option>数据库</option>
							<option>上传文件</option>
							<option>全部</option>
						</select> &nbsp;&nbsp;
						 <input type="button" name="save" value="备份" id="backup"/> (备份采用覆盖的方式，请谨慎操作!)
					</fieldset>
					<fieldset>
						<legend>恢复功能:</legend>
						<label for="boy">选择恢复文件：</label> <select name="FileType" id="restoreType"
							style="border:1px solid #0033FF">
							<option>数据库</option>
							<option>上传文件</option>
							<option>全部</option>
						</select> &nbsp;&nbsp;选择恢复源 <input type="file" name="resotre" id="restoreFile"/>
						 <input type="button" name="back" value="恢复" id="recover"/>
					</fieldset>
					<fieldset>
						<legend>计算分数与排名:</legend>
						<label for="boy">作品分数与排名状态:&nbsp;&nbsp;</label> 
						<c:choose>
						<c:when test="${ranking==0}">
							<span id="calculateState">未计算</span>&nbsp;&nbsp;&nbsp; 
						</c:when>
						<c:otherwise>
							<span id="calculateState">已计算</span>&nbsp;&nbsp;&nbsp; 
						</c:otherwise>
						</c:choose>
						<input type="button" name="startCalculation" value="点击计算" id="calculate" />
					</fieldset>
					<fieldset>
						<legend>游客开放:</legend>
						<label for="boy">游客开放状态：&nbsp;</label> 
						
						<c:choose>
						<c:when test="${touristState==0}">
							<span id="openState">未开放</span>
								&nbsp;&nbsp;&nbsp;
						<input type="button" name="save" value="点击开放" id="controlState" />
						</c:when>
						<c:when test="${touristState==1}">
							<span id="openState">已开放</span>
								&nbsp;&nbsp;&nbsp;
						<input type="button" name="save" value="点击关闭" id="controlState" />
						</c:when>
						</c:choose>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
	<script language="javascript">
		$("#test1").toggle(function() {
			$("#test").slideDown();
		}, function() {
			$("#test").slideUp();
		});
	</script>
</body>
</html>
