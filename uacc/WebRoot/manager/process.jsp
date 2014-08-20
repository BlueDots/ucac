<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'process.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" href="<%=basePath%>manager/css/jquery-ui.css">
    <script type="text/javascript" src="<%=basePath%>manager/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>manager/js/jquery-ui.js"></script>
     
 <script>
 
</script>
  </head>
 <style>
.ui-progressbar {
position: relative;
}
.progress-label {
position: absolute;
left: 50%;
top: 4px;
font-weight: bold;
text-shadow: 1px 1px 0 #fff;
}
</style>
  <body>
    <div id="progressbar"><div class="progress-label">正在加载</div>  </div>
   </body>
</html>
