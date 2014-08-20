<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>后台登录</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">

<script src="<%=basePath%>js/jquery-1.9.1.js" type="text/javascript"></script>


<STYLE>
TD {
	FONT-SIZE: 11px; COLOR: #000000; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serIf; TEXT-DECORATION: none
}
.input_1 {
	BORDER-RIGHT: #999999 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #999999 1px solid; PADDING-LEFT: 2px; LIST-STYLE-POSITION: inside; FONT-SIZE: 12px; PADDING-BOTTOM: 2px; MARGIN-LEFT: 10px; BORDER-LEFT: #999999 1px solid; COLOR: #333333; PADDING-TOP: 2px; BORDER-BOTTOM: #999999 1px solid; FONT-FAMILY: Arial, Helvetica, sans-serIf; LIST-STYLE-TYPE: none; HEIGHT: 18px; BACKGROUND-COLOR: #dadedf
}
</STYLE>
<META content="MSHTML 6.00.6000.16705" name=GENERATOR>

   <script type="text/javascript">
  		function login(){
			var username = document.getElementById("username").value;	
			var password = document.getElementById("password").value;
			var isnull = /^\s*$/;		
			if(isnull.test(username)){
				alert("请输入用户名");
				return false;
			}
	           
			$.get("../login.do",{"username":username,"password":password,"aboveboard":-1}, function(data){				
  				if(data == "管理员登录成功"){
  					window.location = "admin_index.jsp";
  				} else {
  					alert("用户名或密码错误");
  				}
   			});	
	 }

    </script>

</HEAD>
<BODY>
<TABLE cellSpacing=0 cellPadding=0 width=651 align=center border=0>
  <TBODY>
  
  <TR>
    <TD height=50></TD>
  </TR>
  <TR>
    <TD height=351><TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        
        <TR>
          <TD width=15 background=<%=basePath%>img/ileft.gif height=43></TD>
          <TD width=620 background=<%=basePath%>img/i_topbg2.gif><IMG 
            height=43 src="<%=basePath%>img/i_top1.gif" width=43></TD>
          <TD width=16><IMG height=43 src="<%=basePath%>img/iright.gif" 
            width=16></TD>
        </TR>
        <TR>
          <TD background=<%=basePath%>img/ileftbg.gif></TD>
          <TD vAlign=center background=img/bg.gif height=279><TABLE height=109 cellSpacing=0 cellPadding=0 width=369 align=center 
            border=0>
              <TBODY>
              
              <TR>
                <TD width=155><IMG height=140 
                  src="<%=basePath%>img/logo.jpg" width=155 useMap=#Map 
                  border=0></TD>
                <TD vAlign=top align=left width=214><TABLE cellSpacing=0 cellPadding=0 width=167 border=0>
                    <TBODY>
                    
                    <TR>
                      <TD vAlign=bottom width=167 height=30><A 
                     ><IMG 
                        height=19 src="<%=basePath%>img/adminsyteam.gif" 
                        width=154 border=0></A></TD>
                    </TR>
                    <TR>
                      <TD height=123><TABLE height=109 cellSpacing=0 cellPadding=0 
                        align=center border=0>
                          <FORM name=form2 action="../login.do" method=post>
                                    <input type="hidden" name="aboveboard" value=-1>                          
                              <TR>
                                <TD vAlign=bottom align=left width=44 height=28><DIV align=right><IMG height=14 
                              src="<%=basePath%>img/id.gif" width=43></DIV></TD>
                                <TD vAlign=bottom align=left width=114 
                              height=28><INPUT class=input_1 id=username size=15 
                              name=username id="username">
                                </TD>
                              </TR>
                              <TR>
                                <TD align=left height=20><DIV align=right><IMG height=14 
                              src="<%=basePath%>img/pass.gif" 
                            width=43></DIV></TD>
                                <TD height=20><INPUT class=input_1 id=password 
                              type=password size=15 name=password id="password"></TD>
                              </TR>
                              <TR>
                                <TD vAlign=center colSpan=2 height=25><DIV align=center>
                       &nbsp;&nbsp;&nbsp; <button type="button" onclick="login()">登录</button>&nbsp;
                            <button type="reset">重置</button>
                             </DIV></TD>
                              </TR>
                          </FORM>
                          
                        </TABLE></TD>
                    </TR>
                    </TBODY>
                  </TABLE></TD>
              </TR>
              </TBODY>
            </TABLE></TD>
          <TD background=<%=basePath%>img/irightbg.gif></TD>
        </TR>
        <TR>
          <TD><IMG height=29 src="<%=basePath%>img/i_bottom_left.gif" 
            width=15></TD>
          <TD background=<%=basePath%>img/i_bottom_bg.gif></TD>
          <TD width=16><IMG height=29 
            src="<%=basePath%>img/i_bottom_right.gif" 
      width=16></TD>
        </TR>
        </TBODY>
      </TABLE></TD>
  </TR>
  <TR>
    <TD height=1></TD>
  </TR>
  <TR>
    <TD>&nbsp;</TD>
  </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
