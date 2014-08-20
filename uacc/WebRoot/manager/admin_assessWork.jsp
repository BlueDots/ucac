<%@ page pageEncoding = "UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "com.ucac.po.*"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>



<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台作品审查</title>
<script language="javascript" src="<%=basePath%>/manager/js/jquery.js"></script>
<script src="<%=basePath%>/manager/js/jquery-1.4.4.min.js" type="text/javascript"></script>

 <script src="<%=basePath%>/manager/js/smartpaginator.js" type="text/javascript"></script>
 <link href="<%=basePath%>/manager/css/smartpaginator.css" rel="stylesheet" type="text/css" />
 
 <%
	String nowpage = (request.getParameter("pages") == null?"1":request.getParameter("pages"));
  %>
 <script type="text/javascript">
$(document).ready(function() {

	showInfo(<%=nowpage%>);
	$("#assess").click(function(){
		CheckModify("assess");
	});
	$("#notAssess").click(function(){
		CheckModify("notAssess");
	});
});
function showInfo(nowpage){
$.getJSON("ShowWorkInfos.do",
	{
	"nowpage":nowpage
	},
 	function(data) {
			 		var results = data[0].results;		
					var linkNum = data[2].linkNum;
					var num = Math.floor(data[1].count/data[1].maxSize)+1;		              
				  var htmlStr = "<table  width='100%' cellspacing='1' id='customers' margin=0>"+
					"<tr>"+
					"<th><div align='center'>全选"+
					"<input  type='checkbox' value = '0' onClick='SelectAll(this)'>"+
					"</div></th>"+					
					"<th><div align='center'>编号</div></th>"+
					"<th><div align='center'>学校</div></th>"+
					"<th><div align='center'>社团</div></th>"+
					"<th><div align='center'>类别</div></th>"+
					"<th><div align='center'>操作</div></th>"+
					"<th><div align='center'>审核状态</div></th>"+
					"</tr>"; 	
 		if(results.length>0) {
 			for(var i = 0;i<results.length;i++) {
 				if(results[i].category == 1) {
							results[i].category = "理论学习类";
						}else if(results[i].category == 2) {
							results[i].category = "学术科技类";
						}else if(results[i].category == 3){
							results[i].category = "文艺体育类";
						}else if(results[i].category == 4) {
							results[i].category = "社会公益类";
						}else if(results[i].category == 5) {
							results[i].category = "实践创新类";
						}else if(results[i].category == 6) {
							results[i].category = "其他类";
						}
						htmlStr += "<tr>"+" <td><div align='center'>"+" <input name='' type='checkbox' value='"+results[i].id+"' id='item'>"+"</div></td>"+
						"<td><div align='center'>"+results[i].id+"</div></td>"+"<td><div align='center'>"+results[i].school+
						"</div></td>"+" <td><div align='center'>"+results[i].communityName+"</div></td>"+"<td><div align='center'>"+
						results[i].category+"</div></td>"+"<td><div align='center'>"+"<a href='<%=basePath%>personalWork.jsp?id="+results[i].id+"' target='_blank'>查看</a></div>";
						if(results[i].workState == 1)
							htmlStr+="<td><div align='center'>通过</div></td>";
						else if(results[i].workState == 0)
							htmlStr+="<td><div align='center'>未通过</div></td>";
						else if(results[i].workState == -1)
							htmlStr+="<td><div align='center'>未审核</div></td>";
						
						htmlStr+="</tr>";						
 			}	
 		}
 		
 		if(linkNum.length>0){
			$("#fenye").html("");
			for(var j=0;j<linkNum.length;j++){
				
				/*分页*/
				$("#fenye").append("<a href='manager/admin_assessWork.jsp?pages="+linkNum[j]+"'><font class='page_nav page_naVB' onclick=''>"+linkNum[j]+"</font></a>");
			}
		}
 						htmlStr+="</table>";
 						$("#workRecord").html(htmlStr);
 						//alert(htmlStr);
 	});
 	

    
}
 
   function Locate(){
   		window.location="detail.jsp";
   }
	 function SelectAll(target) {
            var items = document.getElementsByTagName("input");
            for (var i = 0; i < items.length; i++) {
                if(items[i].type=="checkbox")
                {
                    items[i].checked = target.checked;
                }
            }
        } 
 
   function CheckModify(assessType){
			var items = document.getElementsByTagName("input");
			var Modifynumber = 0;
			var ids = ""; 
			var id = 3;
			for ( var i = 0; i < items.length; i++) {
				if (items[i].type == "checkbox") {
					if (items[i].checked) {
						if(items[i].value !=0){
							ids+=items[i].value+",";
							Modifynumber++;
						}
					}
				}
			}
			if (Modifynumber == 0) {
				alert("请勾选审核的条目");
				return;
			} else {
			if(assessType == "assess") {
				if(confirm("审核通过？")){
				$.ajax({
					url:"manager/modifyAccountWorkState.do?workIds="+ids+"&assessType="+assessType,
					success:function(data) {
						if(data == "success") {
							alert("审核成功！");
							location = "manager/admin_assessWork.jsp?pages="+<%=nowpage%>; 
						}
						else {
							alert("系统错误");
						}
					 }
					});
				}
			}
			else {
				if(confirm("审核不通过？")){
					$.ajax({
					url:"manager/modifyAccountWorkState.do?workIds="+ids+"&assessType="+assessType,
					success:function(data) {
						if(data == "success") {
							alert("审核成功！");
							location = "manager/admin_assessWork.jsp?pages="+<%=nowpage%>; 
						}
						else {
							alert("系统错误");
						}
					 }
					});
				}
			}	
			}
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
	background:url(manager/images/bottom.jpg) repeat-x;
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
.STYLE1 {color: #FF0000}
</style>
<!--按钮样式结束-->
</head>
<body>
<div id="mainDiv">
	<div id="topDiv">
	<div id="tmenu" align="left"><ul>
 
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
	    <div id="current" align="right">
	      <span class="STYLE1">(必须勾选某个条目才能进行审核操作)</span> 
	      审核
	     <input type="button" value="通过" id="assess">
	     <!--<input name="input" type="button" value="通过" > -->
	     <input type="button" value="未通过" id="notAssess" >
        </div>
        
<div id="form">
<form action="admin-templates/" method="post">

<div id = "workRecord">

</div>
<div><br></div>
<div><br></div>
<div id="fenye"> </div>

</form>

</div>

</div>

</div>

	</div>


</body>
</html>