function searchApply(){
	
	//获取文本内容
	var searchText = $("#ApplyUserName").val();
	
	
	$.getJSON("../searchApplyInfo.do",{"applyName":searchText},function(data){
		
		
			if(data.length>0){
									
				$("#customers").html("	<tr><th><div align='center'>用户名</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团</div></th><th><div align='center'>带队老师</div></th><th><div align='center'>电话</div></th><th><div align='center'>邮箱</div></th><th><div align='center'>操作</div></th></tr>");
				
				for(var i=0;i<data.length;i++){
					
					var applyName = data[i].applyName;
					var school = data[i].school;
					var communityName = data[i].communityName;
					var teacherName = data[i].teacherName;
					var teacherPhone = data[i].teacherPhone;
					var teacherEmail = data[i].teacherEmail;
					
					$("#customers").append("<tr><td><div align = 'center'>"+applyName+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+teacherName+"</div></td><td><div align = 'center'>"+teacherPhone+"</div></td><td><div align = 'center'>"+teacherEmail+"</div></td><td><div align = 'center'><input type='hidden' id='applyName'  value='"+applyName+"'><input type='button' value='删除' onclick='deleteApply();'></div></td></tr>");
				}
		
			}else{
				
				alert("查询结果为空，请重新输入！");
				$("#customers").html("	<tr><th><div align='center'>用户名</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团</div></th><th><div align='center'>带队老师</div></th><th><div align='center'>电话</div></th><th><div align='center'>邮箱</div></th><th><div align='center'>操作</div></th></tr>");

				
			}
		
	});
	
}

function deleteApply(){
	
	var applyName = $("#applyName").val();

	
	$.get("../deleteApply.do?applyName="+applyName,function(data){
		
		alert(data);
		
		$("#customers").html("	<tr><th><div align='center'>用户名</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团</div></th><th><div align='center'>带队老师</div></th><th><div align='center'>电话</div></th><th><div align='center'>邮箱</div></th><th><div align='center'>操作</div></th></tr>");
		
	});
	
	
	
}

function searchExpert(){
	
	var expertName = $("#expertName").val();
	
	
	$.getJSON("./searchExpertInfo.do",{"expertName":expertName},function(data){
		
		if(data.length>0){
			
			$("#customersID1").html("<thead><tr><th width='5%'><div align='center'>编号</div></th><th width='15%'><div align='center' width='10%'>专家编号</div></th><th width='15%'><div align='center'>所在类别</div></th><th width='15%'><div align='center'>专家姓名</div></th><th width='15%'><div align='center'>专家状态</div></th><th><div align='center'>查看记录</div></th></tr></thead>");
		
			for(var i=0;i<data.length;i++){
				
				var expertId = data[i].expertId;
				
				var categoryType = data[i].category;
				
				var expertName = data[i].expertName;
				
				var expertState = data[i].expertState;
				
				//类别判断
				if(categoryType==1){
					var category = "理论学习类";
				}else if(categoryType==2){
					var category = "学术科技类";
				}else if(categoryType==3){
					var category = "文艺体育类";
				}else if(categoryType==4){
					var category = "社会公益类";
				}else if(categoryType==5){
					var category = "实践创新类";
				}else{
					var category = "其他类";
				}
				
				if(expertState==1){
					var state = "启用";
					
				}else if(expertState==0){
					var state = "禁用";
				}
				
				if(expertState==1){
					var Restate = "禁用";
					
				}else if(expertState==0){
					var Restate = "启用";
				}
					
				$("#customersID1").append("<tr><td width='5%'><div align = 'center'>"+i+1+"</div></td><td width='15%'><div align = 'center'>"+expertId+"</div></td><td  width='15%'><div align = 'center'>"+category+"</div></td><td  width='15%'><div align = 'center'>"+expertName+"</div></td><td  width='15%'><div align = 'center'>"+state+"</div></td><td><div align='center'> <input id='check' name='' type='button' value='查看打分记录' onClick='showWork("+expertId+",\""+category+"\");'/>&nbsp;&nbsp;<input id='abid' name='abid' type='button' value='"+Restate+"' onClick='Modify("+expertState+","+expertId+");'></div></td></tr>");
			}
			
		}else{
			
			alert("查询结果为空，请重新输入！");
			$("#customersID1").html("<thead><tr><th width='5%'><div align='center'>编号</div></th><th width='15%'><div align='center' width='10%'>专家编号</div></th><th width='15%'><div align='center'>所在类别</div></th><th width='15%'><div align='center'>专家姓名</div></th><th width='15%'><div align='center'>专家状态</div></th><th><div align='center'>查看记录</div></th></tr></thead>");

		}
		
		
		
	});
	
}