<%@page import="com.ucac.util.CheckTimeUtil"%>
<%@page import="com.ucac.po.Work"%>
<%@page import="com.ucac.dao.impl.EntityDaoImpl"%>
<%@page import="com.ucac.po.Applicant"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
Applicant applicant = (Applicant)request.getSession().getAttribute("applicant");
if(applicant == null) {
	response.sendRedirect("login.jsp");
	return;
}
//Applicant applicant = EntityDaoImpl.getInstance().findById(Applicant.class, 1);
Work work = EntityDaoImpl.getInstance().findById(Work.class, applicant.getId());
Boolean uploadTime = CheckTimeUtil.checkUpLoad();
%>
<c:choose>
		<c:when test="${empty touristState}">
		 	<jsp:forward page="ControlTouristButtonServlet.do?url=abc"></jsp:forward>
		</c:when>
</c:choose>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>个人中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    	<meta http-equiv="cache-control" content="no-cache">
    <!-- Jquery -->
    <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
    
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <script src="js/bootstrap.min.js"></script>
    
    <!-- sco.message  -->
	<link rel="stylesheet" type="text/css" href="sco/scojs.css">
    <script type="text/javascript" src="sco/sco.message.js"></script>
    
    <script type="text/javascript">
    	//加载所有的上传状态
    	function loadAllUploadStates(){
    		loadUploadState("regForm");
			loadUploadState("video");
			loadUploadState("pic1");
			loadUploadState("pic2");
			loadUploadState("material");
    	}
    	
    	//根据fieldName获取文件上传状态并加载显示
    	function loadUploadState(fieldName) {
    		$.getJSON("getUploadState.do?fieldName="+fieldName+"&rmd="+Math.random(), function(data){
    	 
    	  		 $("#uploadState_"+fieldName).html(data.value);
    		  
    		});
    	}
    </script>
    
    <!-- 上传 -->
<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
<script type="text/javascript" src="uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">
$(function() {
    $("#file_upload_regForm").uploadify({
    	'buttonText' : '请选择报名表',
    	'fileTypeDesc':'doc file',
    	'fileTypeExts':'*.doc',
    	'multi': false,
	'uploadLimit':1,
    	'fileObjName'   : 'regForm',
        'swf'      : 'uploadify/uploadify.swf',
        'uploader' : 'upload.do;jsessionid=<%=session.getId()%>',
        'onUploadSuccess':function(file,data,response){
        	$.scojs_message(data, $.scojs_message.TYPE_OK);
        	loadUploadState("regForm");
        }
    });
    $("#file_upload_video").uploadify({
    	'buttonText' : '请选择视频',
    	'fileTypeDesc':'flv file',
    	'fileTypeExts':'*.flv',
    	'multi': false,
	'uploadLimit':1,
    	'fileObjName'   : 'video',
        'swf'      : 'uploadify/uploadify.swf',
        'uploader' : 'upload.do;jsessionid=<%=session.getId()%>',
        'onUploadSuccess':function(file,data,response){
        	$.scojs_message(data, $.scojs_message.TYPE_OK);
        	loadUploadState("video");
        }
    });
    $("#file_upload_pic1").uploadify({
    	'buttonText' : '请选择展板图片1',
    	'fileTypeDesc':'jpg file',
    	'fileTypeExts':'*.jpg',
    	'multi': false,
	'uploadLimit':1,
    	'fileObjName'   : 'pic1',
        'swf'      : 'uploadify/uploadify.swf',
        'uploader' : 'upload.do;jsessionid=<%=session.getId()%>',
        'onUploadSuccess':function(file,data,response){
        	$.scojs_message(data, $.scojs_message.TYPE_OK);
        	loadUploadState("pic1");
        }
    });
    $("#file_upload_pic2").uploadify({
    	'buttonText' : '请选择展板图片2',
    	'fileTypeDesc':'jpg file',
    	'fileTypeExts':'*.jpg',
    	'multi': false,
	'uploadLimit':1,
    	'fileObjName'   : 'pic2',
        'swf'      : 'uploadify/uploadify.swf',
        'uploader' : 'upload.do;jsessionid=<%=session.getId()%>',
        'onUploadSuccess':function(file,data,response){
        	$.scojs_message(data, $.scojs_message.TYPE_OK);
        	loadUploadState("pic2");
        }
    });
    $("#file_upload_material").uploadify({
    	'buttonText' : '请选择申报材料',
    	'fileTypeDesc':'doc file',
    	'fileTypeExts':'*.doc',
    	'multi': false,
	'uploadLimit':1,
    	'fileObjName'   : 'material',
        'swf'      : 'uploadify/uploadify.swf',
        'uploader' : 'upload.do;jsessionid=<%=session.getId()%>',
        'onUploadSuccess':function(file,data,response){
        	$.scojs_message(data, $.scojs_message.TYPE_OK);
        	loadUploadState("material");
        }
    });
});
</script>

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
        top: -40px;
      }
      #table {
        text-align: left;
      }
    </style>
    


  </head>

  <body>
    <div class="container">
      
      <div id="logo"><img src="img/logo.gif"></div>
         <!--导航条 -->
<div class="navbar" style="margin-top:15px;">
  <div class="navbar-inner">
    <a class="brand" href="#">首页</a>
    <ul class="nav">
      <li class="active"><a href="personal.jsp">个人中心</a></li>
					<c:choose>
						<c:when test="${touristState==1}">
							<li><a href="touristsResult.jsp">评选结果</a>
							</li>
						</c:when>
						<c:otherwise>
							<li><a><span  onclick="javascript:$.scojs_message('游客入口尚未开放，请耐心等待!', $.scojs_message.TYPE_ERROR);">评选结果</span></a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
  </div>
   <%if(work != null && work.getWorkState()==0)
                {
              %>
              <h4 style="color: red;">  你的作品正在审核...如一直未通过,请检查邮箱(包括垃圾箱)是否收到未通过提醒.</h4>
              <%}%>
</div>
    </div>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="span8">
        <!--公告 -->
    <br><ul class="nav nav-pills nav-list" id="myTab">
      
      <li><a href="#profile">规则</a></li>
    </ul>
     
    <div class="tab-content">
      <div class="tab-pane active" id="home">...</div>
      <div class="tab-pane" id="profile">
        <p>
        上传视频、展板、报名表、申报材料。各高校就申报的社团文化精品活动要提交2000字左右的申报材料（可附活动图片）、10分钟左右的视频材料（格式FLV，大小720*576，码率384，确保画质清晰、播放流畅）以及2块电子展板（尺寸1280*800，格式JPG），并相应提供有关宣传、荣誉、表彰等方面证明的原件复印件，以A4纸样大小汇编成册。
        </p>
      </div>
      </div>
      </div>
    <div class="span4">
      <!--欢迎 -->
      <div class="hero-unit">
        <div id = "info">
          <p><%=applicant.getUsername() %>，您好!</p>
          <h4><a href="logout.do">退出</a></h4>
            
        </div>

      </div>

    </div>
  </div>
</div>

<!--我的作品 -->
  <div class="container" id = "table1">
 
    <ul class="nav nav-tabs">
      <li class="active">
        <a href="#">我的作品</a>
      </li>
    </ul>
      <!--表格-->
      <table class="table table-bordered table-striped" id = "table">
      <tr class="success">
        <th><div align = "center">学校</div></th><th><div align = "center">社团</div></th><th><div align = "center">类别</div></th><th><div align = "center">展板</div></th><th><div align = "center">视频</div></th><th><div align = "center">申报材料</div></th><th><div align = "center">最终分数</div></th><th><div align = "center">上传文件</div></th>
      </tr>
      <tr>
        <td><%=applicant.getSchool() %></td><td><%=applicant.getCommunityName() %></td>
        <%
        String category = null;
        switch (applicant.getCategory()) {
		case 1:
			category = "理论学习类";
			break;
		case 2:
			category = "学术科技类";
			break;
		case 3:
			category = "文艺体育类";
			break;
		case 4:
			category = "社会公益类";
			break;
		case 5:
			category = "实践创新类";
			break;
		case 6:
			category = "其它类";
			break;
		}
         %>
        <td><%=category%></td>
        <td>
            <div align = "center">
            <%if(work != null){
            	out.print(work.getPictureScore());
              }else{
              	out.print("0");
              }
            %>
            </div>  
        </td>
        <td>
          <div align = "center">
            <%if(work != null){
            	out.print(work.getVideoScore());
              }else{
              	out.print("0");
              }
            %>
            </div>
        </td>
        <td>     
            <div align = "center">
            <%if(work != null){
            	out.print(work.getDocumentScore());
              }else{
              	out.print("0");
              }
            %>
            </div>
        </td>
        <td>
            <div align = "center">
            <%if(work != null){
            	out.print(work.getScore());
              }else{
              	out.print("0");
              }
            %>
            </div>
        </td>

        <td>
            <div align = "center">
            <% if(uploadTime==true&&(work == null || (int)work.getPictureScore()== 0)){%>
            <a href="#myModal" role="button" class="btn" data-toggle="modal" onclick="loadAllUploadStates()">上传</a>
             <% }%>  
              <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h3 id="myModalLabel">文件上传</h3>
                </div>
                <div class="modal-body">
                  <form id="uploadForm" action="upload.do" method="post" enctype="multipart/form-data">
                  报&nbsp;&nbsp;名&nbsp;&nbsp;表：<div id="uploadState_regForm"></div><input type = "file" name="regForm" id="file_upload_regForm"><br><br>
                  视&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;频：<div id="uploadState_video"></div><input type = "file" name="video" id="file_upload_video"><br><br>
                  展&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;板1：<div id="uploadState_pic1"></div><input type = "file" name="pic1" id="file_upload_pic1"><br><br>
                  展&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;板2：<div id="uploadState_pic2"></div><input type = "file" name="pic2" id="file_upload_pic2"><br><br>
                  申报材料：<div id="uploadState_material"></div><input type = "file" name="material" id="file_upload_material"><br><br>
                  </form>
                </div>
                <div class="modal-footer">
                  <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                </div>
              </div>
              <a href="personalWork.jsp?id=<%=applicant.getId() %>" target="_blank" role="button" class="btn">查看</a>
              <div id="myTxt" class="modal hide fade" tabindex="-1"  >
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h3 id="myModalLabel">浏览</h3>
                </div>
                <div class="modal-body">
                </div>
                <div class="modal-footer">
                  <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                </div>
              </div>
              </div>
        </td>
      </tr>
      
      </table>

  </div>
     
   

  </body>
<script type="text/javascript" src="js/checkBrowers.js"></script>

<script type="text/javascript">
  $(function () {
    $('#myTab a:last').tab('show');
  })
</script>


</html>