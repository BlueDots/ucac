<%@ page language="java" import="java.util.*,com.ucac.vo.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
 
    <title>江西社团评比系统比赛结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<title>个人中心</title>
    <!-- Bootstrap -->
    <link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <script src="<%=basePath%>/js/jquery-1.9.1.js"></script>
    <script src="<%=basePath%>/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>/js/liyixiang/liyixiang.js"></script>

	<script src="<%=basePath%>/build/js/messenger-theme-future.js"></script>
	<script src="<%=basePath%>/build/js/messenger.min.js"></script>
	<link href="<%=basePath%>/build/css/messenger.css" rel="stylesheet" media="screen">
	<link href="<%=basePath%>/build/css/messenger-theme-future.css" rel="stylesheet" media="screen">
    <!-- sco.message  -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/sco/scojs.css">
    <script type="text/javascript" src="<%=basePath%>/sco/sco.message.js"></script>
    
    <style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }
      ul#myTab {
        padding-left: 200px;
      }
      div#profile {
        padding-left: 210px;
      }
      div.hero-unit {
        height: 40px;
        width: 100px
      }
      #info p {
        font-size: 14px;
        text-align:center;
      }
      #info h4 {
        font-size: 20px;
        text-align:center;
      }
      #table1 {
        position: relative;
        top: -53px;
      }
      #table {
        text-align: left;
      }
      #search {
        position: relative;
        top: 50px;
        left: 420px;
      }
    </style>

  </head>
  
  <body>
    <div class="container">
      
      <div id="logo"><img src="<%=basePath%>/img/logo.gif"></div>
         <!--导航条 -->
<div class="navbar" style="margin-top:15px;">
  <div class="navbar-inner">
    <a class="brand" href="">首页</a>
    <ul class="nav">
    <%
    if(request.getSession().getAttribute("user")!=null){
     %>
     <li><a href="personal.jsp">个人中心</a></li>
     <%
     } 
     %>
      <li><a href="<%=basePath%>touristsResult.jsp" class="brand" >评选结果</a></li>
    </ul>
  </div>
</div>
    </div>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="span10">
        <!--公告 -->
    <ul class="nav nav-pills nav-list" id="myTab">
      <li class="active"><a href="#home"></a></li>
      <li><a href="#profile">公告</a></li>
    </ul>
     
    <div class="tab-content">
      <div class="tab-pane active" id="home">...</div>
      <div class="tab-pane" id="profile">
        <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传视频、展板、报名表、申报材料。各高校就申报的社团文化精品活动要提交2000字左右的申报材料（可附活动图片）、10分钟左右的视频材料（格式FLV，大小720*576，码率384，确保画质清晰、播放流畅）以及2块电子展板（尺寸1280*800，格式JPG），并相应提供有关宣传、荣誉、表彰等方面证明的原件复印件，以A4纸样大小汇编成册。
        <marquee behavior=alternate><font color = "red">视频40%，展板20%，申报材料40%</font></marquee>
        </p>
      </div>
      </div>
      </div>
  </div>
</div>

<!--我的作品 -->
  <div class="container" id = "table1">
      <form class="form-search" id = "search">
        <input type="text" class="input-medium search-query" placeholder="请输入学校名称" id="searchSchool">
        	
        <input type="text" class="input-medium search-query" placeholder="请输入社团名称" id="searchCommunity">
       
        <input type="button" class="btn" value="查询" onclick="search();">
      	<input type="button" class="btn" value="显示全部" onclick="showResult(1);">
      </form>
        <div id="auto1"></div> <div id="auto2"></div>
    <ul class="nav nav-tabs">
      <li class="active">
        <a href="#">评选排行</a>
      </li>
    </ul>
      
      <table class="table table-bordered table-striped" id = "ScoreTable">
          
      </table>
      
        <div class="pagination pagination-right" id="fenye">
       
        </div>     
       
  	</div>
  </body>
<script type="text/javascript" src="js/checkBrowers.js"></script>
<script type="text/javascript">
  $(function () {
    $('#myTab a:last').tab('show');
  });
</script>
      <script src="<%=basePath%>/js/liyixiang/search.js"></script>  
</html>
