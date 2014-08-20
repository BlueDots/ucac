<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE >
<html>
  <head>
     
    <title>找回密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
 
  <meta charset="utf-8">
  <title>找回密码</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
  <meta name="author" content="Muhammad Usman">

  <!-- The styles -->
  <link id="bs-css" href="<%=basePath%>css/bootstrap-cerulean.css" rel="stylesheet">
  <link href="<%=basePath%>css/bootstrap-responsive.css" rel="stylesheet">
  <link href="<%=basePath%>css/charisma-app.css" rel="stylesheet">
  <script src="js/jquery-1.8.2.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
       <!-- messenger  -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/messenger-theme-future.css">
    <script type="text/javascript" src="js/messenger.min.js"></script>
    <script type="text/javascript" src="js/messenger-theme-future.js"></script>  
    
     <!-- sco.message  -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>sco/scojs.css">
<script type="text/javascript" src="<%=basePath%>sco/sco.message.js"></script>
  
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
  		function find(){
			var username = document.getElementById("username").value;	
			var email = document.getElementById("email").value;		
			var verificationCode = document.getElementById("verificationCode").value;	
			var isnull = /^\s*$/;		
			//  var usernameRegex = /^[a-zA-Z]\w{5,9}$/;	
			if(isnull.test(username)){
				//alert("请输入正确的用户名");
      			$.scojs_message('请输入正确的用户名！', $.scojs_message.TYPE_ERROR);					
				return false;
			}
			if(isnull.test(email)){
				//alert("请输入邮箱哦");
      			$.scojs_message('请输入邮箱！', $.scojs_message.TYPE_ERROR);				
				return false;
			}
			if(isnull.test(verificationCode)){
				changeImage();
				//alert("请输入验证码！");
      			$.scojs_message('请输入验证码！', $.scojs_message.TYPE_ERROR);				
				return false;
			}
			
            //对电子邮件的验证
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(!myreg.test(email))
            {
                 //alert('提示\n\n请输入有效的邮箱！');
       			$.scojs_message('请输入有效的邮箱！', $.scojs_message.TYPE_ERROR);	
                return false;
	        }
	           
			$.get("findPassword.do",{"username":username,"email":email,"verificationCode":verificationCode}, function(data){				
      			if(data != "邮件发送成功，请注意查收！"){
      				$.scojs_message(data, $.scojs_message.TYPE_ERROR);	
      			} else {
      				$.scojs_message("邮件发送成功，请注意查收！", $.scojs_message.TYPE_OK);
      					
      			}	 
      			changeImage();
 			});	
	 }
	 
	 
	 function verOnClick() { 
		if (event.keyCode == 13) { 
		var button = document.getElementById("findout"); 
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
  
  <div><a href="<%=basePath%>login.jsp"><font size="5">进入首页</font></a></div>
  <div class="span12 center login-header" style="margin-top:50px;">
          <h2 class="STYLE1">找回密码</h2>
        </div>   
    <form method="post" action="findPassword.do">  
    <table align="center">
     <caption style="margin-top:20px;">请输入账号和绑定的邮箱</caption>
    <tr><td>账    号</td><td><input type="text" name="username" id="username" style="width: 200px; height: 30"></td></tr>
    <tr><td>邮    箱</td><td><input type="text" name="email" id="email" style="width: 200px; height: 30"></td></tr>
    <tr><td>验证码</td><td><input type="text" name="verificationCode" id="verificationCode" onkeydown="javascript:verOnClick();" style="width: 160px; height: 30"  ><a href="javascript:changeImage();">看不清</a></td></tr>
    <tr><td colspan="2" align="center"><img src="captcha.do" onclick="changeImage()" id="imgRandom"></td></tr>
    <tr><td  align="right" colspan="2"><input type="button" class="btn btn-primary" value="找回" id="findout" onclick="find()">&nbsp;<input class="btn btn-primary" type="button" value="重置"></td></tr>
   </table>
   </form>
</body>
</html>
  