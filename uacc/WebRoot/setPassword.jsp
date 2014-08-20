<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%String checkCode1 = request.getParameter("checkCode");
		if(checkCode1==null){
			response.sendRedirect("login.jsp");
			return;
		}
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>找回密码</title>

<!-- The styles -->
<link id="bs-css" href="<%=basePath%>css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link id="bs-css" href="<%=basePath%>css/bootstrap-cerulean.css" rel="stylesheet">
<link href="<%=basePath%>css/bootstrap-responsive.css" rel="stylesheet">
<link href="<%=basePath%>css/charisma-app.css" rel="stylesheet">
  <script src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
  <script src="<%=basePath%>js/bootstrap.min.js"></script>

    <!-- messenger  -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger-theme-future.css">
    <script type="text/javascript" src="js/messenger.min.js"></script>
    <script type="text/javascript" src="js/messenger-theme-future.js"></script>  
  
  <!-- sco.message  -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>sco/scojs.css">
<script type="text/javascript" src="<%=basePath%>sco/sco.message.js"></script>
  
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

<!-- The fav icon -->

<style type="text/css">
<!--
.STYLE1 {
	font-size: 36px
}
-->
</style>
<style type="text/css">
#checkcode {
	width: 115px;
}
</style>

<%
request.setCharacterEncoding("utf-8");
response.setCharacterEncoding("utf-8");
System.out.println(request.getParameter("username"));
System.out.println(request.getParameter("checkCode"));

String username = request.getParameter("username");
String checkCode = request.getParameter("checkCode");
 %>
 
<script type="text/javascript">
	
	
	function checkpwd(){
	
	  var p1=document.form1.password1.value;//获取密码框的值
	  var p2=document.form1.password2.value;//获取重新输入的密码值
	  if(p1==""){
	  // alert("请输入密码！");//检测到密码为空，提醒输入//
	  	$.scojs_message('请输入密码', $.scojs_message.TYPE_ERROR);			   	
	   	document.form1.password1.focus();//焦点放到密码框
	   	return false;//退出检测函数
	  }          //如果允许空密码，可取消这个条件
	  
	  if(p1!= p2){//判断两次输入的值是否一致，不一致则显示错误信息
	   // 	alert("两次输入的密码不一致！");
	   $.scojs_message('两次输入的密码不一致!', $.scojs_message.TYPE_ERROR);
	   return false;
	  }else{
	   //密码一致，可以继续下一步操作 
	   
	   		$.get("setPassword.do",{"username":"<%=username%>","password":p2,"checkCode":"<%=checkCode%>"}, function(data){				
  			   alert(data);
								 
 			});
	  }
	}  	  
	   function psdOnClick() { 
			if (event.keyCode == 13) { 
				var button = document.getElementById("subpsd"); 
				button.click(); 
				return false; 
			} 
	   } 
 
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="row-fluid">
				<div class="span12 center login-header" style="margin-top:50px;">
					<h2 class="STYLE1">修改密码</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid center">
				<div class="well span5 center login-box">
					<div class="alert alert-info">请输入新的密码</div>
					
					<form class="form1"  name="form1" method="post" action="setPassword.do?username=<%=username%>&checkCode=<%=checkCode%>">
						<div style="margin-left:29px" class="input-prepend" title="密码"
							data-rel="tooltip">
							输入新密码 <span class="add-on"><i class="icon-lock"></i>
							</span><input class="input-large" name="password1" id="password1"
								type="password"  />
						</div>
						<div style="margin-left:28px" class="input-prepend" title="密码" 
							data-rel="tooltip">

							再次输入密码 <span class="add-on"><i class="icon-lock"></i>
							</span><input class="input-large" name="password2" id="password2"  onkeydown="psdOnClick();"
								type="password"  />
						</div>
						 
						
						<div class="clearfix"></div>

						<div style="margin-left:130px;" class="span3">
							<button class="btn btn-primary" type="button"  id="subpsd" onclick="checkpwd()"
								style="margin-top:0px" >确定</button>
						</div>
						<div class="span3">
							<a class="btn btn-success" type="reset" style="margin-top:0px"
								href="login.jsp">返回</a>
						</div>
					</form>
				</div>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
		<!--/fluid-row-->

		<!--/.fluid-container-->

	</div>
</body>
</html>

