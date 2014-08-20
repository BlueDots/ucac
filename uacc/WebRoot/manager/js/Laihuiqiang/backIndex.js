$(document).ready(function() {
//	$.post("adminIndexInfoServlet.do");
    getBackupPath();//
	$("#backup").click(function(){
  		$.post("backupServlet.do",
 		 {
    		value:getFileType("#backupType"),
   			path:$("#backup").attr("name")
 		 },
 		 function(data,status){
    			alert("消息: " + data );
  			});
		});
	$("#recover").click(function(){
		var fileName = getRestoreFileName();
		if(!fileName){
			alert("请选择备份的文件!!");
			return;
		}
  		$.post("restoreServlet.do",
 		 {
    		value:getFileType("#restoreType"),
   			fileName:fileName
 		 },
 		 function(data,status){
    			alert("消息: " + data );
  		});
	});
	
	$("#calculate").click(function() {
		   var time = new Date();
		   if(!confirm("计算机会只有一次，确定计算?")){
		    	return;
		   }else{
			   /***加上用户名输入***/
			   var  username = window.prompt("输入你的账号:");  
				 if(username=='' || username==undefined || username=='undefined' || username.trim()==""){
					 alert("请输入用户名");
				 }else{
			         $.getJSON("./checkAdminAgain.do",{"username":username},function(data){
			             	 if(0==data.result){
			             		 alert("帐号输入错误");
			             	 }else if(1==data.result){
			                        /**这里是赖辉强的代码 **/
                                      	$.post("calculateWorkServlet.do",
			     					null,
			      		 			function(data,status){
			         				alert("消息: " + data);
			       				});
			     				$("#calculate").hide();
			     				$("#calculateState").text("已计算");
			              	 }
			         });
				 }
              /**结束**/
		     }
		});
	$("#controlState").click(function() {
		/**加入输入**/
		var  username = window.prompt("输入你的账号:");  
		 if(username=='' || username==undefined || username=='undefined' || username.trim()==""){
			 alert("请输入用户名");
		 }else{
	         $.getJSON("./checkAdminAgain.do",{"username":username},function(data){
	             	 if(0==data.result){
	             		 alert("帐号输入错误");
	             	 }else if(1==data.result){
	                        /** 这里是赖辉强的代码 **/
	             		 if($("#controlState").attr("value") == "点击开放") {
	         				$("#controlState").attr("value", "点击关闭");
	         					$("#openState").text("已开放");
	         					$.post("controlTouristStateServlet.do",
	         						{currentState:"0"},
	          		 				function(data,status){
	             					alert("消息: " + data);
	           					});
	         				} else {
	         					$("#controlState").attr("value", "点击开放");
	         					$("#openState").text("已关闭");
	         					$.post("controlTouristStateServlet.do",
	         						{currentState:"1"},
	          		 				function(data,status){
	             					alert("消息: " + data);
	           					});
	         				}
	              	 }
	         });
		 }

         /**结束**/
		
		 
		});
});
	function getFileType(id){
         if(jQuery(id+" option:selected").text() == "数据库"){
         	return "1";
         }else if(jQuery(id+" option:selected").text() == "上传文件"){
         	return "2";
         }else{
         	return "3";
         }
    }
	
    function getBackupPath(){
		var path;
		var file;
		$.post("FileOperation.xml", function(data) {
			$(data).find("file").each(function(i) {
				var file = $(this).attr("backup");
				path = $(this).children("path").text();
				$("#backup").attr("name",path);
			});
		});	
	}
    function getRestoreFileName(){
    	var $route=$('input[type="file"]').val();
    	if (navigator.userAgent.indexOf("MSIE") != -1) {  
            return $route.toString();  
        } else if (navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Mozilla") != -1) {  
        	 try {  
                 netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
             } catch(e) {  
                 alert('建议您使用IE浏览器!您也可以按照以下步骤进行设置 [1] 地址栏输入 "about:config" ; [2] 右键 新建 -> 布尔值 ; [3] 输入 "signed.applets.codebase_principal_support" (忽略引号).');  
        
             }   
             return $route.toString();
        } else {  
            return;  
        }  
    }
    
    