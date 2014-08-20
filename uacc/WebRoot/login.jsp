<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:choose>
		<c:when test="${empty touristState}">
		 	<jsp:forward page="ControlTouristButtonServlet.do"></jsp:forward>
		</c:when>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

  <title>用户登录</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
  <meta name="author" content="Muhammad Usman">

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
.STYLE1 {font-size: 36px}
-->
    </style>
    <style type="text/css">
    #checkcode{
      width:115px;

    }
    </style>
    <script type="text/javascript">
     function isStart(){
     			
     			$.scojs_message("游客入口尚未开放，请耐心等待!", $.scojs_message.TYPE_ERROR);
     }
      function register(){
          window.location="activation.jsp";
      }
      
      function go(){
         
          window.location="touristsResult.jsp";
      }
      
      function selectGo(){
      		var username = document.getElementById("username").value;	
			var password = document.getElementById("password").value;		
			var verificationCode = document.getElementById("verificationCode").value;	
			var isnull = /^\s*$/;		
		//	var usernameRegex = /^[a-zA-Z]\w{5,9}$/;	
			if(isnull.test(username)){
				//alert("请输入用户名!");
      			$.scojs_message('请输入用户名', $.scojs_message.TYPE_ERROR);			
				return false;
			}
			if(isnull.test(password)){
				//alert("请输入密码！");
      			$.scojs_message('请输入密码！', $.scojs_message.TYPE_ERROR);			
				return false;
			}
			if(isnull.test(verificationCode)){
				changeImage();
				//alert("请输入验证码！");
				$.scojs_message('请输入验证码', $.scojs_message.TYPE_ERROR);	
				return false;
			}		
     		 $.get("login.do",{"username":username,"password":password,"verificationCode":verificationCode,"aboveboard":1}, function(data){				
  			   switch (data) {
				case "申报人员登录成功":
					window.location = "personal.jsp";
					break;
				case "专家登录成功":
					window.location = "expert.jsp";
					break;
				default:
					//alert(data);
					$.scojs_message(data, $.scojs_message.TYPE_ERROR);					
					changeImage();
					break;
				}				 
 			});
         
      }
      
      function butOnClick() { 
		if (event.keyCode == 13) { 
		var button = document.getElementById("login"); 
		button.click(); 
		return false; 
		} 
	 } 
      
      
//重新获取验证字符
     function changeImage()
     {
      //单击触发图片重载事件，完成图片验证码的更换
         document.getElementById("imgRandom").src = document.getElementById("imgRandom").src + '?';
     }
    </script>
</head>

<body>
</br></br>
    
   <div align="right"><a href="<%=basePath%>1-material.html" target="_blank" style="color: red;">申报人员操作指南</a>&nbsp;&nbsp;<a style="color: red;" href="<%=basePath%>2-material.html" target="_blank">专家操作指南</a></div>
    <div class="container-fluid">
    <div class="row-fluid">
    
      <div class="row-fluid">
        <div class="span12 center login-header" style="margin-top:50px;">
          <h2 class="STYLE1">全省高校学生社团文化精品活动展演</h2></br><h2 class="STYLE2">预选环节评比系统</h2>
        </div>
        <!--/span-->
      </div><!--/row-->
      
      <div class="row-fluid center" >
        <div class="well span5 center login-box">
          <div class="alert alert-info">
            请输入用户名和密码登录系统.
          </div>
          <form class="form-horizontal"  method="post"  action="login.do">
          <input type="hidden" name="aboveboard" id="aboveboard" value=1>
            <fieldset>
            <div style="margin-left:-40px" class="input-prepend" title="用户名" data-rel="tooltip">
              用户名
                <span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large" name="username" id="username" type="text"  />
            </div>
              <div class="clearfix"></div>

            <div style="margin-left:29px;" class="input-prepend" title="密码" data-rel="tooltip">
              密&nbsp;&nbsp;&nbsp;&nbsp;码
                <span class="add-on"><i class="icon-lock"></i></span><input class="input-large" name="password" id="password" type="password"  /><a href="findPassword.jsp">忘记密码</a>
            </div>
           
            <div class="input-prepend" title="验证码" data-rel="tooltip" style="margin-left:-45px;"> 
              验证码
                <span class="add-on"><i class="icon-qrcode"></i></span><input class="input-large" name="verificationCode"  id="verificationCode" type="text"  onkeydown="javascript:butOnClick();"/>
               <br> <a href="javascript:changeImage();"><img id="imgRandom" onclick="changeImage()" src="captcha.do">看不清换一张</a>
            </div>   
            
              <div class="clearfix"></div>
              <div class="row-fluid">
                <div style="margin-left:65px;" class="span3"><button type="button" class="btn btn-primary" id="login" onClick="selectGo()">登录</button></div>
                <div class="span3"><button class="btn btn-info" type="button" onClick="register()">激活</button></div>
                 <c:choose>
                <c:when test="${touristState==0}">
                <div class="span3"><button class="btn btn-success disabled" type="button" onClick="isStart()">游客入口</button></div>
                </c:when>
                <c:otherwise>
                <div class="span3"><button class="btn btn-success" type="button" onClick="go()">游客入口</button></div>
                </c:otherwise>
                </c:choose>
              </div>
            </fieldset>
          </form>
        </div><!--/span-->
      </div><!--/row-->
        </div><!--/fluid-row-->
    
  </div><!--/.fluid-container--> 
  <div align="center">推荐使用浏览器:IE8,IE9,IE10、谷歌、火狐、百度、欧朋,其他可能有兼容问题.</div>
       
</body>
<script type="text/javascript" src="js/checkBrowers.js"></script>
</html>
    