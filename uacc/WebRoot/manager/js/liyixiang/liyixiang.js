//后台查看比赛结果
$(document).ready(function(){
	
	showResult(1);
		
});
function exportExcel(url){
 
	var category =$("#categorySelect").val();
	var sortType = $("#sortTypeSelect").val();
   win  = window.open(url+"manager/exportExcel.do?type=result&category="+category+"&sortType="+sortType);
	   win.document.execCommand("SaveAs") ;
  
    
}
function showResult(nowpage){
	//接收参数
	var requestURL = "viewResult.jsp";
	$("#customers").html(" <tr><th><div align='center'><input name='' type='checkbox' value='' onClick='SelectAll(this)'></div></th><th><div align='center'>社团编号</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团名称</div></th><th><div align='center'>社团类型</div></th><th><div align='center'>视频得分</div></th><th><div align='center'>电子展板所得分</div></th><th><div align='center'>审核材料所得分</div></th><th><div align='center'>总得分</div></th><th><div align='center'>排名</div></th></tr>");
	
	var category =$("#categorySelect").val();
	var sortType = $("#sortTypeSelect").val();

	
	//调用servlet
	$.getJSON("showWorkResult.do",{"nowpage":nowpage,"category":category,"sortType":sortType,"requestURL":requestURL},function(data){
		//解析json	
		var results = data[0].results;		
		var linkNum = data[2].linkNum;
		var num = Math.floor(data[1].count/data[1].maxSize)+1;
		
		
		if(results.length>0){
			//创建表头
			$("#customers").html(" <tr><th><div align='center'><input name='' type='checkbox' value='' onClick='SelectAll(this)'></div></th><th><div align='center'>社团编号</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团名称</div></th><th><div align='center'>社团类型</div></th><th><div align='center'>视频得分</div></th><th><div align='center'>电子展板所得分</div></th><th><div align='center'>审核材料所得分</div></th><th><div align='center'>总得分</div></th><th><div align='center'>排名</div></th></tr>");
		
			//加载数据
			for(var i = 0;i<results.length;i++){
				var ranking = results[i].ranking;
				var school = results[i].school;
				var communityName = results[i].communityName;
				var categoryType = results[i].category;
				var videoScore = results[i].videoScore;
				var pictureScore = results[i].pictureScore;
				var documentScore = results[i].documentScore;
				var Score = results[i].score;
				var applicantId = results[i].applicantId;
				
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
					
				//创建表格
				if(i%2==0){
					
					$("#customers").append("<tr class='alt'><td><div align='center'><input name='' type='checkbox' value=''> </div></td><td><div align='center'>"+applicantId+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'>"+ranking+"</div></td></tr>");

				}else{
					$("#customers").append("<tr><td><div align='center'><input name='' type='checkbox' value=''> </div></td><td><div align='center'>"+applicantId+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'>"+ranking+"</div></td></tr>");

				}					
			}			
		}
		
		if(linkNum.length>0){
			$("#fenye").html("");
			for(var j=0;j<linkNum.length;j++){
				
				/*分页*/
				$("#fenye").append("<a href='javascript:showResult("+linkNum[j]+")'><font class='page_nav page_naVB'>"+linkNum[j]+"</font></a>");
			}
		}
		
	
		
		
	});	
}

/*后台比赛结果搜索*/
function search(){
	
	$("#fenye").html("");
	//获取文本框内容
	var schoolName = $("#searchSchool").val();
	var communityName = $("#searchCommunity").val();
	
	$.getJSON("searchWorkResult.do",{"schoolName":schoolName,"communityName":communityName},function(data){
		
		if(data.length>0){
			//创建表头
			$("#customers").html(" <tr><th><div align='center'><input name='' type='checkbox' value='' onClick='SelectAll(this)'></div></th><th><div align='center'>社团编号</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团名称</div></th><th><div align='center'>社团类型</div></th><th><div align='center'>视频得分</div></th><th><div align='center'>电子展板所得分</div></th><th><div align='center'>审核材料所得分</div></th><th><div align='center'>总得分</div></th><th><div align='center'>排名</div></th></tr>");
		
			//加载数据
			for(var i = 0;i<data.length;i++){
				var ranking = data[i].ranking;
				var school = data[i].school;
				var communityName = data[i].communityName;
				var categoryType = data[i].categoryType;
				var videoScore = data[i].videoScore;
				var pictureScore = data[i].pictureScore;
				var documentScore = data[i].documentScore;
				var Score = data[i].Score;
				var applicantId = data[i].applicantId;
				
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
					
				//创建表格
				if(i%2==0){
					
					$("#customers").append("<tr class='alt'><td><div align='center'><input name='' type='checkbox' value=''> </div></td><td><div align='center'>"+applicantId+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'>"+ranking+"</div></td></tr>");

				}else{
					$("#customers").append("<tr><td><div align='center'><input name='' type='checkbox' value=''> </div></td><td><div align='center'>"+applicantId+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'>"+ranking+"</div></td></tr>");

				}					
			}			
		}else {
				
			//创建表头
			$("#customers").html(" <tr><th><div align='center'><input name='' type='checkbox' value='' onClick='SelectAll(this)'></div></th><th><div align='center'>社团编号</div></th><th><div align='center'>学校</div></th><th><div align='center'>社团名称</div></th><th><div align='center'>社团类型</div></th><th><div align='center'>视频得分</div></th><th><div align='center'>电子展板所得分</div></th><th><div align='center'>审核材料所得分</div></th><th><div align='center'>总得分</div></th><th><div align='center'>排名</div></th></tr>");
			
			alert("查询结果为空，请重新输入！");
		}	
		
	});
	
}

//类别下拉框选项
function categorySelect(){
	
	showResult(1);
	
}

//排序下拉框选项
function sortTypeSelect(){
	
	showResult(1);
}