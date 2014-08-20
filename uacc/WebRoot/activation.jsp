<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!--
    Charisma v1.0.0

    Copyright 2012 Muhammad Usman
    Licensed under the Apache License v2.0
    http://www.apache.org/licenses/LICENSE-2.0

    http://usman.it
    http://twitter.com/halalit_usman
  -->
  <meta charset="utf-8">
  <title>用户激活</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
  <meta name="author" content="Muhammad Usman">

  <!-- The styles -->
  <link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
  <style type="text/css">
    body {
    padding-bottom: 40px;
    }
    .sidebar-nav {
    padding: 9px 0;
    }
  </style>
  <link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
  <link href="css/bootstrap-responsive.css" rel="stylesheet">
  <link href="css/charisma-app.css" rel="stylesheet">
  
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
    
    <!-- Jquery -->
    <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
    
	<!-- sco.message  -->
	<link rel="stylesheet" type="text/css" href="sco/scojs.css">
    <script type="text/javascript" src="sco/sco.message.js"></script>
    
    <script type="text/javascript">
      function activation(){
		  $.scojs_message("请稍候......", $.scojs_message.TYPE_OK);
    	  $.ajax({
 	         type: "GET",
 	         url:"activation.do",
 	         data:$("#activationForm").serialize(),// 要提交的表单 
 	         success: function(msg) {
 	        	if(msg == "激活成功！") {
 	        		$.scojs_message(msg, $.scojs_message.TYPE_OK);
 	        		window.setTimeout(function(){window.location = "login.jsp"},1000);
 	        	} else {
 	        		$.scojs_message(msg, $.scojs_message.TYPE_ERROR);
 	        	}
 	         }
 	     });
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
    <div class="container-fluid">
    <div class="row-fluid">
    
      <div class="row-fluid">
        <div class="span12 center login-header">
          <h2 class="STYLE1">欢迎您激活</h2>
        </div>
        <!--/span-->
      </div><!--/row-->
      
      <div class="row-fluid center" style="margin-top:-10px;">
        <div class="well span5 center login-box">
          <div class="alert alert-info">
            请填写相应的信息.
          </div>
          <form id="activationForm" class="form-horizontal"  method="post">
            <fieldset>
            <div style="margin-left:-80px;" class="input-prepend" title="账号" data-rel="tooltip">
            账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号
                <span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large" name="username" id="username" type="text" value="" />
            </div>
              <div class="clearfix"></div>

            <div style="margin-left:-80px;" class="input-prepend" title="密码" data-rel="tooltip">
            密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码
                <span class="add-on"><i class="icon-lock"></i></span><input class="input-large" name="password" id="password" type="password" value="" />
            </div>
            <div style="margin-left:-80px;" class="input-prepend" title="确认密码" data-rel="tooltip">
            确认密码
                <span class="add-on"><i class="icon-lock"></i></span><input class="input-large" name="password2" id="checkpassword" type="password" value="" />
            </div>
            <div style="margin-left:-80px;" class="input-prepend" title="社团名称" data-rel="tooltip">
            社&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;团
                <span class="add-on"><i class="icon-home"></i></span><input class="input-large" name="communityName" id="groupname" type="text" value="" />
            </div>
            <div style="margin-left:-80px;" class="input-prepend" title="活动类别" data-rel="tooltip">
            活动类别
                <span class="add-on"><i class="icon-th-list"></i></span><select  name="category" id="actioncategory" value="1">
                  <option value="1">理论学习类</option>
                  <option value="2">学术科技类</option>
                  <option value="3">文艺体育类</option>
                  <option value="4">社会公益类</option>
                  <option value="5">实践创新类</option>
                  <option value="6">其他类</option>
                </select>
                
            </div>

            <div class="clearfix"></div>
            <div style="margin-left:-80px;" class="input-prepend" title="带队老师" data-rel="tooltip">
            带队老师
                <span class="add-on"><i class="icon-user"></i></span><input class="input-large" name="teacherName" id="teachername" type="text" value="" />
            </div>
            <div class="clearfix"></div>
            	 
            <div style="margin-left:-80px;" class="input-prepend" title="带队老师电话" data-rel="tooltip">
            老师电话
                <span class="add-on"><i class="icon-volume-up"></i></span><input class="input-large" name="teacherPhone" id="teacherphone" type="text" value="" />
            </div>  
            <div class="clearfix"></div> 
            <div style="margin-left:-80px;" class="input-prepend" title="带队老师邮箱" data-rel="tooltip">
            老师邮箱
                <span class="add-on"><i class="icon-folder-open"></i></span><input class="input-large" name="teacherEmail" id="teacheremail" type="text" value="" />
            </div>  
             
            <div class="clearfix"></div>
            <div style="margin-left:40px;" class="input-prepend" title="验证码" data-rel="tooltip">
            验&nbsp;&nbsp;证&nbsp;&nbsp;码
                <span class="add-on"><i class="icon-qrcode"></i></span><input class="input-large" name="verificationCode" id="checkcode" type="text" value="" />
                <a href="javascript:changeImage();"><img id="imgRandom" onclick="changeImage()" src="captcha.do">看不清换一张</a>
            </div> 
            <div class="clearfix"></div>
              <div class="row-fluid">
                <div style="margin-left:120px;" class="span3"><button class="btn btn-primary" type="button" onClick="activation()">激活</button></div>
                <div class="span3"><a class="btn btn-success"  href = "login.jsp">返回</a></div>
              </div>
            </fieldset>
          </form>
        </div><!--/span-->
      </div><!--/row-->
        </div><!--/fluid-row-->
    
  </div><!--/.fluid-container-->

  
  
    
</body>
</html>
