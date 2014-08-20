<%@page import="com.ucac.dao.impl.EntityDaoImpl"%>
<%@page import="com.ucac.po.Applicant"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
//Applicant applicant = (Applicant)request.getSession().getAttribute("user");
//if(applicant == null) {
//	response.sendRedirect("login.html");
//	return;
//}
//Applicant applicant = EntityDaoImpl.getInstance().findById(Applicant.class, 1);
if(!(session.getAttribute("admin")!=null || session.getAttribute("applicant")!=null)){
	response.sendRedirect("login.jsp");
 
}

String id = request.getParameter("id");
 if(id == null) {
	return;
} 
int applicantId = Integer.parseInt(id);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>作品详情</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

  <!-- Bootstrap -->
  <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
  <script src="js/jquery-1.8.2.min.js"></script>
  <script src="js/bootstrap.min.js"></script>

  <!-- videoJS -->

  <!-- Chang URLs to wherever Video.js files will be hosted -->
  <link href="videojs/video-js.css" rel="stylesheet" type="text/css">
  <!-- video.js must be in the <head> for older IEs to work. -->
  <script src="videojs/video.js"></script>

  <!-- Unless using the CDN hosted version, update the URL to the Flash SWF -->
  <script>
  videojs.options.flash.swf = "videojs/video-js.swf";
  </script>

  <!-- songyouwei -->
  <link rel="stylesheet" type="text/css" href="css/songyouwei.css">
  

</head>

<body>
  <div class="container">

    <div id="logo"><img src="img/logo.gif"></div>
    <div class="navbar">
      <div class="navbar-inner">
        <ul class="nav">
          <li class="active"><a href="#">回首页</a></li>
        </ul>
      </div>
    </div>

    
    <div id="work">
<% if(request.getSession().getAttribute("admin") != null) { %>
    <div class="work-item">
		<a href="Works/<%=applicantId %>-regform.html" class="btn btn-large btn-primary" target='_blank'>查看报名表</a>
    </div>
    <%} %>

      <div class="work-item">
        <div class="work-item-title"><h2 class="muted text-error">视频</h2></div>
        <div class="work-item--content">

          <!-- video -->
          <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="780" height="480"
          poster="http://www.videojs.com/img/poster.jpg"
          data-setup="{}">
          <source src="Works/<%=applicantId %>-video.flv" type='video/flv' />

        </video>
      </div>
     
     
    </div>

    <div class="work-item">
      <div class="work-item-title"><h2 class="muted text-error">展板</h2></div>
      <div class="work-item-content row">

        <!-- img -->
        <div id="myCarousel" class="carousel slide span6">
          <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
          </ol>
          <!-- Carousel items -->
          <div class="carousel-inner">
            <div class="active item"><img src="Works/<%=applicantId%>-pic1.jpg"></div>
            <div class="item"><img src="Works/<%=applicantId%>-pic2.jpg"></div>
          </div>
          <!-- Carousel nav -->
          <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
          <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
        </div>

        <div class="span6">
<a href="Works/<%=applicantId%>-pic1.jpg" target="_blank"><font color="red" size=5>查看展板1大图</font></a>&nbsp;&nbsp;
<a href="Works/<%=applicantId%>-pic2.jpg" target="_blank"><font color="red" size=5>查看展板2大图</font></a>
	</div>
      </div>
     
      
    </div>
    <div class="work-item">
		<a href="Works/<%=applicantId %>-material.html" class="btn btn-large btn-primary" target='_blank'>查看申报材料</a><br><br><br>
				<a
					href="Works/<%=applicantId %>-material.doc"
					class="btn" target='_blank'>下载申报材料Word文档</a>
    </div>

  </div>

</div>
</body>
<script type="text/javascript" src="js/checkBrowers.js"></script>
</html>