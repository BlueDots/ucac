<%@ page pageEncoding = "UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "com.ucac.po.*"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<%
	String category = String.valueOf(request.getAttribute("category"));
	
	
	if(category==null){
		response.sendRedirect("/showExpert.do");
	}
	
	String nowpage = (request.getParameter("pages") == null?"1":request.getParameter("pages"));
	
 %>


<html>
  <head>
    <base href="<%=basePath%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台专家打分记录</title>
<script language="javascript" src="<%=basePath%>/manager/js/jquery.js"></script>
<script src="<%=basePath%>/manager/js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="<%=basePath%>/manager/js/jquery.chromatable.js"></script>
	<script src="<%=basePath%>/manager/js/liyixiang/searchApply.js"></script>
 <script src="<%=basePath%>/manager/js/smartpaginator.js" type="text/javascript"></script>
    <link href="<%=basePath%>/manager/css/smartpaginator.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function Modify(flag,expertId){

	if(flag == 1) {
		if(confirm("确定禁用？")) {
			$.ajax({
			url:"manager/modifyAccountState.do?expertId="+expertId,
			success:function(data) {
				if(data == "success") {
					location = "manager/admin_viewMangeAction.jsp?pages="+<%=nowpage%>; 
				}else {
					alert("系统错误！");
				}
			 }
			});
		}
	}
	else {
		if(confirm("确定启用？")) {
			$.ajax({
			url:"manager/modifyAccountState.do?expertId="+expertId,
			success:function(data) {
				if(data == "success") {
					location = "manager/admin_viewMangeAction.jsp?pages="+<%=nowpage%>; 
				}else {
					alert("系统错误！");
				}
			 }
			});
		}
	}
} 

 function showWork(expertId,category) {

     
	 var nCategory = 0;
	 if(category == "理论学习类") {
		nCategory = 1;
	 }
	 else if(category == "学术科技类") {
	 	nCategory = 2;
	 }
	 else if(category == "文艺体育类") {
	 	nCategory = 3;
	 }
	 else if(category == "社会公益类") {
	 	nCategory = 4;
	 }
	 else if(category == "实践创新类") {
	 	nCategory = 5
	 }
	 else if(category == "其他类") {
		nCategory = 6;
	 }
	
	
	  var   expertExcel   = $("#expertExcel");
	    expertExcel.click(function(){
               exportExcel('<%=basePath%>',nCategory,expertId);
          });
 
   
	 $.getJSON("ShowExpertRecord.do",
			  { 
			    expertId:expertId,
			    category:nCategory,
			  	nowpage:1,
			    pageSize:100,
			    isFront:false
			  },
			  function(json) {
			  		var htmlStr = "<table  width='100%' cellspacing='1'	id='customersID1' class='demo'  margin=0 ><thead>"+
					"<tr>"+
					"<th><div align='center'>全选"+
					"<input name='' type='checkbox' value='' onClick='SelectAll(this)'>"+
					"</div></th>"+
					"<th><div align='center'>专家姓名</div></th>"+
					"<th><div align='center'>社团名称</div></th>"+
					"<th><div align='center'>所在类别</div></th>"+
					"<th><div align='center'>视频得分</div></th>"+
					"<th><div align='center'>展板得分</div></th>"+
					"<th><div align='center'>材料得分</div></th>"+
					"<th><div align='center'>最后得分</div></th>"+
					"</tr>"+
					"</thead>";
				if(json.length<=1){
					alert("无数据！");
				} 
				if(json.length>1) {
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
							json[i].category = "其他类";
						}
						htmlStr += "<tr>"+" <td><div align='center'>"+" <input name='' type='checkbox' value=''>"+"</div></td>"+
						"<td><div align='center'>"+json[i].expertName+"</div></td>"+"<td><div align='center'>"+json[i].communityName+
						"</div></td>"+" <td><div align='center'>"+json[i].category+"</div></td>"+"<td><div align='center'>"+
						json[i].videoScore+"</div></td>"+"<td><div align='center'>"+json[i].pictureScore+"</div></td>"+
						"<td><div align='center'>"+json[i].documentScore+"</div></td>"+"<td><div align='center'>"+json[i].score+"</div></td>";
					}
				}
				htmlStr+="</tr>"+"</table>";
				$("#expertRecord").html(htmlStr);
				//alert(htmlStr);				
			  }
	 );
	
 }
</script>
<script type="text/javascript">

$(document).ready(function() {
	showExperts(<%=nowpage%>);
})

function searchCategory(){
	showExperts(1);
}

function showExperts(nowpage){

	var category = $("#categorySelect").val();
	$.getJSON("showExpert.do",
	{ 
	"nowpage":nowpage,
	"category":category,
	"requestType":"AJAX"
	},
	function(json){
	    var htmlStr = "<thead><tr><th width='5%'><div align='center' >编号</div></th><th width='15%'><div align='center'>专家编号</div></th><th width='15%'><div align='center'>所在类别</div></th><th width='15%'><div align='center'>专家姓名</div></th><th width='15%'><div align='center'>专家状态</div></th><th><div align='center'>查看记录</div></th></tr></thead>";
		var linkNum = json[2].linkNum;
		var num = Math.floor(json[1].count/json[1].maxSize)+1;					
				if(json.length>0) {
					for(var i = 0;i<json[0].results.length;i++) {	
						if(json[0].results[i].category == 1) {
							json[0].results[i].category = "理论学习类";
						}else if(json[0].results[i].category == 2) {
							json[0].results[i].category = "学术科技类";
						}else if(json[0].results[i].category == 3){
							json[0].results[i].category = "文艺体育类";
						}else if(json[0].results[i].category == 4) {
							json[0].results[i].category = "社会公益类";
						}else if(json[0].results[i].category == 5) {
							json[0].results[i].category = "实践创新类";
						}else if(json[0].results[i].category == 6) {
							json[0].results[i].category = "其他类";
						}
						htmlStr += "<tr>"+" <td width='5%'><div align='center'>"+(i+1)+"</div></td>"+"<td width='15%'><div align='center'>"+json[0].results[i].id+"</div></td>"+
						"<td width='15%'><div align='center'>"+json[0].results[i].category+"</div></td>"+" <td width='15%'><div align='center'>"+json[0].results[i].expertName+"</div></td>";
						if(json[0].results[i].expertState == 1){
							htmlStr+="<td width='15%'><div align='center'><font color = 'blue'>启用</font></div></td>";
						}else {
							htmlStr+="<td width='15%'><div align='center'><font color = 'red'>禁用</font></div></td>";
						}
						htmlStr+="<td><div align='center'>"+" <input type='button' value='查看打分记录'  onClick='showWork("+json[0].results[i].id+",\""+json[0].results[i].category+"\")'> ";
						if(json[0].results[i].expertState == 1) {
							htmlStr += "<input name='' type='button' value='禁用' onClick='Modify(1,"+json[0].results[i].id+")'>";
							}
						else  {
							htmlStr += "<input name='' type='button' value='启用' onClick='Modify(0,"+json[0].results[i].id+")'>";
							}
						htmlStr += "</div></td></tr>";
					}
				}				
				
				$("#customersID1").html(htmlStr);	
		if(linkNum.length>0){
			$("#fenye").html("");
			for(var j=0;j<linkNum.length;j++){
				
				/*分页*/
				$("#fenye").append("<a href='manager/admin_viewMangeAction.jsp?pages="+linkNum[j]+"'><font class='page_nav page_naVB' onclick=''>"+linkNum[j]+"</font></a>");
			}
		}					
				
				});	
}

	
</script>
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
background:url(images/slide.jpg) repeat-y;
position:absolute;
left:0px;
}
#lhead{
background:url(images/left-head.jpg) left top no-repeat;
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

	background:url(manager/images/head-bg.jpg) repeat-x;
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
background:url(manager/images/menu-bg.jpg) repeat-x;
color:#FFFFFF;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
border:1px #06597D solid;
}
#tmenu{
width:1169px;
position:absolute;
left:80px;
bottom:2px;
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

.page_nav {
	border: 1px solid #D7E2EE;
	border-radius: 3px 3px 3px 3px;
	color: #333333;
	padding: 3px 7px;
	cursor: pointer;
	text-decoration: none;
}

.page_naVB {
	background: none repeat scroll 0 0 #0099FF;
	border: 0 none;
	border-radius: 3px 3px 3px 3px;
	color: #FFFFFF;
	padding: 8px 10px;
	cursor: pointer;
	text-decoration: none;
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
width:400px;
}
#customersID1
{
	
}
</style>
<!--按钮样式结束-->
<!--表头固定-->

<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
.demo{border:solid 1px #D5D5D5;border-collapse:collapse;width:100%;}
.demo td{border:1px solid #D5D5D5;font-size:12px;padding:7px 5px;}
.demo th{background-color:#EEE;border-right:1px solid #D5D5D5;font-size:13.5px;line-height:120%;font-weight:bold;padding:8px 5px;text-align:left;}
 
</style>

<!--表头固定-->
<script type="text/javascript" >

$(document).ready(function() {
$("#customersID1").chromatable({
		width: "100%",
	       scrolling: "yes"
	});
$("#customersID2").chromatable({
		width: "100%",
	       scrolling: "yes"
	});	

});

function SelectAll(target) {
            var items = document.getElementsByTagName("input");
            for (var i = 0; i < items.length; i++) {
                if(items[i].type=="checkbox")
                {
                    items[i].checked = target.checked;
                }
            }
        };
function CheckModify(){
            var items = document.getElementsByTagName("input");
			var Modifynumber=0;
            for (var i = 0; i < items.length; i++) {
                if(items[i].type=="checkbox")
                {
                    if(items[i].checked == true)
					{
					    Modifynumber = Modifynumber + 1;
					}
                }
            }
			if(Modifynumber == 0)
			{
			   alert("您还选择，请勾选您要导出或打印的条目!");
			}else{
			   alert("操作成功!");
			}
        } ;	


  $("#check").click(function(){
  $("#record").fadeOut();
  });
function ShowAll()
{
	location.reload(); 
}

function exportExcel(url,category,expertId){
	 
 
   win  = window.open(url+"manager/exportExcel.do?type=record&category="+category+"&expertId="+expertId);
	   win.document.execCommand("SaveAs") ;
  
    
}
</script>

</head>
<body>
<div id="mainDiv">
	<div id="topDiv">
	<div id="tmenu" align="left">
	  <ul>
 
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
	<div id="current" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专家评审类型
	<select name="category" style="border:1px solid #0033FF"
						id="categorySelect" onchange="searchCategory();">
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
					</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入专家姓名&nbsp;&nbsp;&nbsp;<input type="text" name="搜索" id="expertName"><input name="" type="button" value="检索" onclick="searchExpert();">
	&nbsp;&nbsp;&nbsp;&nbsp; <input name="Input" type="button" value="导出" 	 id="expertExcel">
 
         <input name="" type="button" value="显示全部" onClick="ShowAll()"></div>
     

<!-- 专家 -->
<table  width="100%" cellspacing="1"    id="customersID1" class="demo"  margin=0>

<thead><tr><th width="5%"><div align='center' >编号</div></th><th width="15%"><div align='center'>专家编号</div></th><th width="15%"><div align='center'>所在类别</div></th><th width="15%"><div align='center'>专家姓名</div></th><th width="15%"><div align='center'>专家状态</div></th><th><div align='center'>查看记录</div></th></tr></thead>

</table>
<div><br></div>
<div><br></div>

<div id="fenye"   > </div>

<div id="record">


<div id = "expertRecord">

</div>

<div id="smart-paginator"   > </div>


</div>
</div>
</div>

</div>




<script language="javascript">
$("#test1").toggle(function(){$("#test").slideDown()},function(){$("#test").slideUp()})

</script>
 

<!--表头固定js-->
</body>
</html>
