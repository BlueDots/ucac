<%@ page language="java" import="java.util.*,com.ucac.vo.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	if (session.getAttribute("expert") == null) {
		response.sendRedirect("login.jsp");
	}
%>

<!DOCTYPE html>

<html lang="en">
<head>
<title>活动</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- Jquery -->
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="js/bootstrap.min.js"></script>

<!-- messenger  -->
<link rel="stylesheet" type="text/css" media="screen"
	href="css/messenger.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="css/messenger-theme-future.css">
<script type="text/javascript" src="js/messenger.min.js"></script>
<script type="text/javascript" src="js/messenger-theme-future.js"></script>
<!-- sco.message  -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/sco/scojs.css">
<script type="text/javascript" src="<%=basePath%>/sco/sco.message.js"></script>
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
<script type="text/javascript">
   function  checkScore(score){
        var  abc  =   score.toString().split(".");
        //alert(abc);
        if(score>=1 && score<=99) {
	        if(abc.length>=2){
	          if(abc[1].length<=2)
	          	return true;
	          else return false;
	        } 
	        else if(abc.length=1){
	            if(score>=1 && score<=99)
	            	return true;
	        }
    }
    else return false;
   }

	function onSubmit() {
		var videoS = document.getElementById("video").value;
		var pictureS = document.getElementById("picture").value;
		var documentS = document.getElementById("document").value;
		if (videoS.toString().length < 1 || pictureS.toString().length < 1 || documentS.toString().length < 1) {
			//alert("分数不能为空！");
			//alert("ssssss");
			$.scojs_message('分数不能为空！', $.scojs_message.TYPE_ERROR);
		} else if (checkScore(videoS)==false || checkScore(pictureS)==false
				|| checkScore(documentS)==false) {
			//alert("分数输入有误！");
			$.scojs_message('分数输入有误！分数应为：1-99，最多为两位小数', $.scojs_message.TYPE_ERROR);

		} else {
             var imath=Math.random();
			$.post("SubmitWork.do?run="+imath, {
				videoScore : videoS,
				pictureScore : pictureS,
				documentScore : documentS,
				expertId :
<%=request.getParameter("expertId")%>
	,
				applicantId :
<%=request.getParameter("applicantId")%>
	,
			}, function(data) {
				if (data == "Success") {
					//alert("评分成功！");
					$.scojs_message('评分成功！', $.scojs_message.TYPE_OK);
					window.setTimeout(function() {
						window.location = "expert.jsp?pages="
								+
<%=request.getParameter("pages")%>
	}, 1000);
				} else if (data == "FormatError" || data == "NumberError") {
					alert("分数输入有误！");
				}
			});
		}
	}

	function onUpdate() {
		var videoS = document.getElementById("video").value;
		var pictureS = document.getElementById("picture").value;
		var documentS = document.getElementById("document").value;
		if (videoS.length < 1 || pictureS.length < 1 || documentS.length < 1) {
		
			$.scojs_message('分数不能为空！', $.scojs_message.TYPE_ERROR);
	}else if (checkScore(videoS)==false || checkScore(pictureS)==false
				|| checkScore(documentS)==false) {
			//alert("分数输入有误！");
			$.scojs_message('分数输入有误！分数应为：1-99，最多为两位小数', $.scojs_message.TYPE_ERROR);
		} else {
	      var imath=Math.random();
			$.post("UpdateWork.do?run="+imath, {
				videoScore : videoS,
				pictureScore : pictureS,
				documentScore : documentS,
				expertId :
<%=request.getParameter("expertId")%>
	,
				applicantId :
<%=request.getParameter("applicantId")%>
	,
				id :
<%=request.getParameter("id")%>
	}, function(data) {
				if (data == "Success") {
					//alert("修改成功！");

					$.scojs_message('修改成功！', $.scojs_message.TYPE_OK);
					window.setTimeout(function() {
						window.location = "expert.jsp?pages="
								+
<%=request.getParameter("pages")%>
	}, 1000);
				} else if (data == "FormatError" || data == "NumberError") {
					alert("分数输入有误！");
				}
			});
		}
	}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
					  var imath=Math.random();
						$.getJSON(
										"ShowWork.do?run="+imath,
										{
											expertId :
<%=request.getParameter("expertId")%>
	,
											applicantId :
<%=request.getParameter("applicantId")%>
	},
										function(json) {

											if (json.length > 0) {

												if (json[1].score != 0.0) {

													$("#video").attr("value",
															json[1].videoScore);
													$("#picture")
															.attr(
																	"value",
																	json[1].pictureScore);
													$("#document")
															.attr(
																	"value",
																	json[1].documentScore);
													$("#submit")
															.append(
																	"<button class='btn btn-large btn-primary' type='button' onClick='onUpdate()'>修改</button>");
												} else {
													$("#submit")
															.append(
																	"<button class='btn btn-large btn-primary' type='button' onClick='onSubmit()'>提交</button>");
												}
											}
										});

					});
</script>


</head>
<style type="text/css">
#fanhui {
	margin-left: 250px;
	margin-top: -45px
}
</style>
<body>
	<div class="container">

		<div id="logo">
			<img src="img/logo.gif">
		</div>
		<div class="navbar">
			<div class="navbar-inner">
				<ul class="nav">
					<li class="active"><a href="#">首页</a></li>
					<li><a
						href="expert.jsp?pages=<%=request.getParameter("pages")%>">作品列表</a>
					</li>
				</ul>
			</div>
		</div>


		<div id="work">



			<div class="work-item">
				<div class="work-item-title">
					<h2 class="muted text-error">视频：</h2>
				</div>
				<div class="work-item--content">

					<!-- video -->
					<video id="example_video_1" class="video-js vjs-default-skin"
						controls preload="none" width="780" height="480" poster=""
						data-setup="{}">
						<source
							src="Works/<%=request.getParameter("applicantId")%>-video.flv"
							type='video/flv' />

					</video>
				</div>

				<div class="point">
					<!-- 打分 -->
					打分：<input class="span2" id="video" type="text" value=""
						placeholder="请输入分数" />
				</div>

			</div>

			<div class="work-item">
				<div class="work-item-title">
					<h2 class="muted text-error">展板：</h2>
				</div>
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
							<div class="active item">
								<img
									src="Works/<%=request.getParameter("applicantId")%>-pic1.jpg">
							</div>
							<div class="item">
								<img
									src="Works/<%=request.getParameter("applicantId")%>-pic2.jpg">
							</div>
						</div>
						<!-- Carousel nav -->
						<a class="carousel-control left" href="#myCarousel"
							data-slide="prev">&lsaquo;</a> <a class="carousel-control right"
							href="#myCarousel" data-slide="next">&rsaquo;</a>
					</div>

					<div class="span6">
<a href="Works/<%=request.getParameter("applicantId") %>-pic1.jpg" target="_blank"><font color="red" size=5>查看展板1大图</font></a>&nbsp;&nbsp;
<a href="Works/<%=request.getParameter("applicantId") %>-pic2.jpg" target="_blank"><font color="red" size=5>查看展板2大图</font></a>
					</div>
				</div>

				<div class="point">
					<!-- 打分 -->
					打分：<input class="span2" id="picture" type="text" value=""
						placeholder="请输入分数" />
				</div>

			</div>

			<div class="work-item">
				<a
					href="Works/<%=request.getParameter("applicantId")%>-material.html"
					class="btn btn-large btn-primary" target='_blank'>查看申报材料</a><br><br><br>
				<a
					href="Works/<%=request.getParameter("applicantId")%>-material.doc"
					class="btn" target='_blank'>下载申报材料Word文档</a>
				<div class="point">
					<!-- 打分 -->
					打分：<input class="span2" id="document" type="text" value=""
						placeholder="请输入分数" />
				</div>
			</div>

			<div id="submit"></div>

			<div id="fanhui">
				<a class="btn btn-large" type="button"
					href="expert.jsp?pages=<%=request.getParameter("pages")%>">返回</a>
			</div>


		</div>

	</div>
</body>
<script type="text/javascript" src="js/checkBrowers.js"></script>
</html>