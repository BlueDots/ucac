<%@ page language="java"
	import="java.util.*,com.ucac.vo.*,com.ucac.po.*" pageEncoding="UTF-8"%>


<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	QueryResult<Expert> experts  =(QueryResult<Expert>) request.getAttribute("experts");
       Page pages  = (Page)request.getAttribute("page"); 
       String category  =  request.getAttribute("category")+""; 
        if(experts==null && pages==null){
        //判断是不是重servlet进来的
        response.sendRedirect(basePath+"manager/showExperts.do?requestUrl=expertInfo");
        return ;
        }
%>
<!DOCTYPE  >
<html>
<head>


<title>后台专家帐号管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="社团评比系统后台,江西社团评比系统后台">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>manager/css/jquery-ui.css">
<script src="<%=basePath%>manager/js/jquery-1.9.1.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>manager/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="<%=basePath%>manager/js/xionganping/pageStyle.js"></script>
<style>
.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 50%;
	top: 4px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}
</style>
<style type="text/css">
<!--
* {
	margin: 0px;
	padding: 0px;
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
	left: 0px;
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
	background: url(<%=basePath%>manager/images/left-head.jpg) left top
		no-repeat;
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
	padding: 20px 0 0 0;
	font-size: 12px;
	font-family: "宋体";
}

#centerDiv>#right {
	width: auto;
	height: auto;
	position: absolute;
	top: 5px;
	right: 0px;
	left: 0px;
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
	line-height: 100%;
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
	width: 100%;
	height: 99%;
	margin: 0 auto;
	_margin-left: 0px;
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
</style>


<!--表格样式-->

<!--按钮样式-->
<style type="text/css">
input[type="submit"] {
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

input[type="submit"]:hover {
	background-color: #F8F8F8;
	background-image: -moz-linear-gradient(center top, #F8F8F8, #F1F1F1);
	border: 1px solid #C6C6C6;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
	color: #333333;
}

input[type="submit"]:focus {
	border: 1px solid #4D90FE;
	outline: medium none;
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

input[type="text"] {
	height: 27px;
	border: 1px solid #0066FF;
	padding: 0 6px;
	width: 200px;
}
</style>
<!--按钮样式结束-->
<script type="text/javascript">
	function SelectAll(target) {
		var items = document.getElementsByTagName("input");
		for ( var i = 0; i < items.length; i++) {
			if (items[i].type == "checkbox") {
				items[i].checked = target.checked;
			}
		}
	}
</script>
<style type="text/css">
.page_nav {
	border: 1px solid #D7E2EE;
	border-radius: 3px 3px 3px 3px;
	color: #333333;
	padding: 3px 7px;
	cursor: pointer;
	text-decoration: none;
}

.page_naVB {
	background: none repeat scroll 0 0 #006699;
	border: 0 none;
	border-radius: 3px 3px 3px 3px;
	color: #FFFFFF;
	padding: 3px 7px;
	cursor: pointer;
	text-decoration: none;
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
				<div id="current" align="center">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;社团类型
					<select name="category" style="border:1px solid #0033FF"
						id="categorySelect"  >
						<option value="7" <%if (category.equals("7")) {%>
							selected="selected" <%}%>>显示全部</option>
						<option value="1" <%if (category.equals("1")) {%>
							selected="selected" <%}%>>理论学习类</option>
						<option value="2" <%if (category.equals("2")) {%>
							selected="selected" <%}%>>学术科技类</option>
						<option value="3" <%if (category.equals("3")) {%>
							selected="selected" <%}%>>文艺体育类</option>
						<option value="4" <%if (category.equals("4")) {%>
							selected="selected" <%}%>>社会公益类</option>
						<option value="5" <%if (category.equals("5")) {%>
							selected="selected" <%}%>>实践创新类</option>
						<option value="6" <%if (category.equals("6")) {%>
							selected="selected" <%}%>>其它类</option>
					</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 生成账号数量:<input type="text"
					  id="accountNum"> <input   type="submit" value="自动生成" 
						onClick="autoMakeAccount()">
					&nbsp;&nbsp;&nbsp;&nbsp; <input name="" type="submit" value="导出"
					onClick="exportExcel('<%=basePath %>') ">  
				</div>

				<div id="form">
					<form action="./expertsAction.do" method="post" onsubmit="return checkAll();">
						<div id="progressbar">
							<div class="progress-label">正在加载</div>
						</div>
						<table width="100%" cellspacing="1" id="customers">
                                
							<tr >
								<th width="7%">
									<div align="center">
										<input name="all" type="checkbox" value=""
											onClick="SelectAll(this)">
									</div>
								</th>
								<th width="12%" >编号</th>
								<th width="13%">账号</th>
								<th width="15%">密码</th>
								<th width="15%">类别</th>
								<th width="17%">姓名</th>
								<th width="22%">电话</th>
								<th width="29%">邮箱</th>
							</tr>
							<%
								if (experts != null) {
									Iterator<Expert> expertIterator = experts.getResults()
											.iterator();
									int num = 1;
									while (expertIterator.hasNext()) {
										Expert expert = expertIterator.next();
							%>
							<tr>
								<td>
									<div align="center">
										<input name="" type="checkbox" value="">
									</div></td>
							 
								<td ><%=(pages.getPage() - 1) * pages.getMaxSize() + num%><input type="hidden" name="id" value="<%=expert.getId()%>"></td>
								<td><%=expert.getUsername()%></td>
								<td>******</td>
								<td>
									<%
										switch (expert.getCategory()) {
												case 1:
									%>理论学习类<%
										;
													break;
												case 2:
									%>学术科技类<%
										;
													break;
												case 3:
									%>文艺体育类<%
										;
													break;
												case 4:
									%>社会公益类<%
										;
													break;
												case 5:
									%>实践创新类<%
										;
													break;
												case 6:
									%>其它类<%
										;
													break;
												}
									%>
								</td>
								<td><%=expert.getExpertName()%></td>
								<td><%=expert.getExpertTel()%></td>
								<td><%=expert.getExpertEmail()%></td>
							</tr>
							<%
								num++;
									}
							%>

							<tr>
								<td colspan="4" style="border: 0 white;">
									<div align="left">
										<%
											Iterator<Integer> pageIterator = pages.getLinkNum().iterator();
												if (pages.getLinkNum().get(0) != 1) {
										%>
										<font class="page_nav"  onclick="getValueByPage('<%=pages.getPage() - 1%>')">前一页</font>
										<%
											}
												while (pageIterator.hasNext()) {
													Integer a = pageIterator.next();
													if (a == pages.getPage()) {
										%>
										<font class="page_nav page_naVB"  onclick="getValueByPage('<%=a%>')" ><%=a%></font>
										<%
											} else {
										%>
										<font class="page_nav" onclick="getValueByPage('<%=a%>')"><%=a%></font>
										<%
											}
												}
												if (pages.getLinkNum().get(pages.getLinkNum().size() - 1) < pages
														.getCount() / pages.getMaxSize()) {
										%>
										<font class="page_nav"
										  onclick="getValueByPage('<%=pages.getCount() / pages.getMaxSize()%>')">后一页</font>
										<%
											}
										%>
									</div>
								</td>
								
								<td colspan="4" style="border: 0 white;">
									<div align="right">
										<input type="submit" name="submit" value="提交" 
										   class="updateButton"	  style="display: none;" />
										<input type="button"  class="updateButton"  name="submit" value="修改"  onclick="updateAction()"/>
										<input type="button"  class="updateButton" name="submit" value="取消"  style="display:none;"  onclick="getValueByPage('<%=pages.getPage()%>')"/>
									</div></td>
							</tr>
							<% }%>
						</table>
					</form>

				</div>

			</div>

		</div>


	</div>

	<script language="javascript">
$("#test1").toggle(function(){$("#test").slideDown();},function(){$("#test").slideUp();});
</script>

</body>
</html>
