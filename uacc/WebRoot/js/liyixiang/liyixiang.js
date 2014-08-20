

/*查看比赛结果*/
$(document).ready(function(){
	
		showResult(1);
	
});


/*比赛结果搜索*/
function search(){
	
	
	$("#fenye").html("");

	/*获取文本框内容*/
	var schoolName = $("#searchSchool").val();
	var communityName = $("#searchCommunity").val();
	
	
	$.getJSON("searchWorkResult.do",{"schoolName":schoolName,"communityName":communityName},function(data){
		
		if(data.length>0){
			
			//创建表头
			$("#ScoreTable").html("<tr class='success'><th><div align = 'center'>名次</div></th><th><div align = 'center'>学校</div></th><th><div align = 'center'>社团</div></th><th><div align = 'center'>类别</div></th><th><div align = 'center'>视频</div></th><th><div align = 'center'>展板</div></th><th><div align = 'center' >申报材料</div></th><th><div align = 'center'>分数</div></th><th><div align = 'center'>操作</div></th></tr>");
		
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
				$("#ScoreTable").append("<tr><td><div align = 'center'>"+ranking+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'><a class='btn' data-dismiss='modal' aria-hidden='true'  href = \"ShowWorkDetail.do?applicantId="+applicantId+"&videoScore="+videoScore+"&pictureScore="+pictureScore+"&documentScore="+documentScore+"\" target='_blank'>"+"查看"+"</a></div></td></tr>");				
			}
		}else {
				
			//创建表头
			$("#ScoreTable").html("<tr class='success'><th><div align = 'center'>名次</div></th><th><div align = 'center'>学校</div></th><th><div align = 'center'>社团</div></th><th><div align = 'center'>类别</div></th><th><div align = 'center'>视频</div></th><th><div align = 'center'>展板</div></th><th><div align = 'center' >申报材料</div></th><th><div align = 'center'>分数</div></th><th><div align = 'center'>操作</div></th></tr>");
			
			
			$.scojs_message('查询结果为空，请重新输入！', $.scojs_message.TYPE_ERROR);

		}
		
		
		
	});
}

function showResult(nowpage){
	
	//接收参数
	var requestURL = "viewResult.jsp";
	//当前页面
	var pageFront = 1;
	var pageLast = 1;
	//默认为全部显示
	var category = "7";
	//默认为降序
	var sortType = "1";
	   
	//调用servlet
	$.getJSON("showWorkResult.do",{"nowpage":nowpage,"category":category,"sortType":sortType,"requestURL":requestURL},function(data){
		//解析json	
		
		var results = data[0].results;		
		var linkNum = data[2].linkNum;
		var num = Math.floor(data[1].count/data[1].maxSize)+1;
				
		if(results.length>0){
			//创建表头
			$("#ScoreTable").html("<tr class='success'><th><div align = 'center'>名次</div></th><th><div align = 'center'>学校</div></th><th><div align = 'center'>社团</div></th><th><div align = 'center'>类别</div></th><th><div align = 'center'>视频</div></th><th><div align = 'center'>展板</div></th><th><div align = 'center' >申报材料</div></th><th><div align = 'center'>分数</div></th><th><div align = 'center'>操作</div></th></tr>");
		
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
						
				$("#ScoreTable").append("<tr><td><div align = 'center'>"+ranking+"</div></td><td><div align = 'center'>"+school+"</div></td><td><div align = 'center'>"+communityName+"</div></td><td><div align = 'center'>"+category+"</div></td><td><div align = 'center'>"+videoScore+"</div></td><td><div align = 'center'>"+pictureScore+"</div></td><td><div align = 'center'>"+documentScore+"</div></td><td><div align = 'center'>"+Score+"</div></td><td><div align = 'center'><a   class='btn' data-dismiss='modal' aria-hidden='true' href = \"ShowWorkDetail.do?applicantId="+applicantId+"&videoScore="+videoScore+"&pictureScore="+pictureScore+"&documentScore="+documentScore+"\" target='_blank'>"+"查看"+"</a></div></td></tr>");
				
				
			}
			
			//上一页
			if(nowpage>1){
				pageFront = nowpage-1;
			}else{
				pageFront = 1;
				$.scojs_message('这里是首页', $.scojs_message.TYPE_OK);
				
			}
			
			
			//下一页
			if(nowpage<num){
				pageLast = nowpage+1;
			}else{
				pageLast = num;
				$.scojs_message('这里是尾页', $.scojs_message.TYPE_OK);
			}
					
		}
		
		if(linkNum.length>0){
			
			
			$("#fenye").html("<ul><li><a href='javascript:showResult("+pageFront+")'>上一页</a></li>");
			
			for(var j=0;j<linkNum.length;j++){
				
				$("#fenye").append("<ul><li><a href='javascript:showResult("+linkNum[j]+")'>"+linkNum[j]+"</a></li>");

				
			}
			
			$("#fenye").append("<ul><li><a href='javascript:showResult("+pageLast+")'>下一页</a></li></ul>");


		}
		
	});
}
