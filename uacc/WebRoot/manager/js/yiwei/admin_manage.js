$(document).ready(function(){
	$.post(
			"../manager/showSettingContent.do", 
			{
			
			},
  			function(json){
  				//将进度条显示在页面上	
				var strApplyHtml = "<font size='2' face='幼圆'>设置报名开始时间:</font>" +
    			"<input type='text' "+ "class='datepicker1'" + " name='applyBegin' " + "onblur='checkApplyBegin()'"+ "value="+ stringFormat(json[0].applyBegin) + ">" +
    			" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"<font size='2' face='幼圆'>设置报名截止时间:</font>" + 
    			"<input type='text' "+ "class='datepicker2'" + " name='applyEnd'" + "onblur='checkApplyEnd()'" + "value="+ stringFormat(json[0].applyEnd) + ">";
    			$(".applySetting").html(strApplyHtml);
    			
    			var strAppraiseHtml = "<font size='2' face='幼圆'>设置评审开始时间:</font>" +
    			"<input type='text' "+ "class='datepicker3'" + " name='appraiseBegin' " + "onblur='checkAppraiseBegin()'" + "value="+ stringFormat(json[0].appraiseBegin) + ">" +
    			" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"<font size='2' face='幼圆'>设置评审截止时间:</font>" + 
    			"<input type='text' "+ "class='datepicker4'" + " name='appraiseEnd' "  + "onblur='checkAppraiseEnd()'" +  "value="+ stringFormat(json[0].appraiseEnd) +">";
  				$(".appraiseSetting").html(strAppraiseHtml);
  				
  				var strProportionHtml = "<label for='amount1'>视频所占比例：</label>" +
  				"<input type='text' id='amount1' name='video' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].video) + ">" +
  				" <div id='slider-range-min1' style='width:1000px'></div></br>" +
  				"<label for='amount1'>展板所占比：</label>" + 
  				"<input type='text' id='amount2' name='picture' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].picture) + ">" +
  				"<div id='slider-range-min2' style='width:1000px'></div></br>" +
  				"<label for='amount1'>申报材料所占比例：</label>" +
  				"<input type='text' id='amount3' name='document' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].document) + ">" +
  				"<div id='slider-range-min3' style='width:1000px'></div></br>";
  				$(".proportionSetting").html(strProportionHtml);
  				loadSible(json[0].video,json[0].picture,json[0].document);
  				
  			},
  			"json"
  		);
		
});
function  loadSible(data1,data2,data3){
    $( "#slider-range-min1" ).slider({
        range: "min",
        value: data1,
        min: 1,
        max: 99,
        slide: function( event, ui ) {
          $( "#amount1" ).val(ui.value + "%" );
          stop:checkProportion();
        }
      });
      $( "#amount1" ).val( $( "#slider-range-min1" ).slider( "value" ) + "%" );
    
    
    
       $( "#slider-range-min2" ).slider({
        range: "min",
        value: data2,
        min: 1,
        max: 99,
        slide: function( event, ui ) {
          $( "#amount2" ).val(ui.value + "%" );
          stop:checkProportion();
        }
      });
      $( "#amount2" ).val( $( "#slider-range-min2" ).slider( "value" ) + "%" );
      
       $( "#slider-range-min3" ).slider({
  	
        range: "min",
        value: data3,
        min: 1,
        max: 99,
        slide: function( event, ui ) {
          $( "#amount3" ).val(ui.value + "%" );
          stop:checkProportion();
        }
      });
    
      $( "#amount3" ).val( $( "#slider-range-min3" ).slider( "value" ) + "%" );
      $('.datepicker1').datepicker({ onSelect: function(dateText, inst) {
    	  	var beginStr = applyForm.applyBegin.value;
    		var beginDate = new Date(beginStr);//将开始时间的字符串转化为时间
    		var endDate = new Date(applyForm.applyEnd.value);//将申报截止时间转化为时间
    		if(!(checkTimePattern(beginStr))) {
    			alert("申报开始时间格式不匹配");
    			//applyForm.applyBegin.focus();
    		}else if(getNowDate() >= beginDate) {
    			alert("该时间已过，不可设置");
    			//applyForm.applyBegin.focus();
    		}else if(beginDate >= endDate) {
    			alert("开始时间与截止时间有冲突");
    			//applyForm.applyBegin.focus();
    		}
      } });
      
      $('.datepicker2').datepicker({ onSelect: function(dateText, inst) {
    	  var beginStr = applyForm.applyBegin.value;
    		var endStr = applyForm.applyEnd.value;
    	 	var appraiseBStr = appraiseForm.appraiseBegin.value;//评审开始时间
    	 	var applyBDate = new Date(beginStr);
    	 	var applyEDate = new Date(endStr);
    	 	var appraiseBDate = new Date(appraiseBStr);
    	 	if(!(checkTimePattern(endStr))) {
    	 		alert("申报截止时间格式不匹配");
    			//applyForm.applyEnd.focus();
    	 	}else if(getNowDate() >= applyEDate) {
    	 		alert("改时间已过，不可设置");
    	 		//applyForm.applyEnd.focus();
    	 	}else if(applyEDate <= applyBDate) {
    	 		alert("开始时间与截止时间有冲突");
    	 		//applyForm.applyEnd.focus();
    	 	}else if(appraiseBDate <= applyEDate) {
    	 		alert("评审开始与申报截止时间有冲突");
    	 		//applyForm.applyEnd.focus();
    	 	}
      } });
      
      $('.datepicker3').datepicker({ onSelect: function(dateText, inst) {
    	  	var beginStr = appraiseForm.appraiseBegin.value;
    		var applyEStr = applyForm.applyEnd.value;//获取申报截止时间
    		var endStr = appraiseForm.appraiseEnd.value;
    		var beginDate = new Date(beginStr);
    		var applyEDate = new Date(applyEStr);
    		var endDate = new Date(endStr);
    		if(!(checkTimePattern(beginStr))) {
    			alert("评审开始时间格式不匹配");
    			//appraiseForm.appraiseBegin.focus();
    		}else if(getNowDate() >= beginDate) {
    			alert("该时间已过，不可设置该时间");
    			//appraiseForm.appraiseBegin.focus();
    		}else if(beginDate <= applyEDate) {
    			alert("报名截止与评审开始时间有冲突");
    			//appraiseForm.appraiseBegin.focus();
    		}else if(beginDate >= endDate) {
    			alert("开始和截止时间有冲突");
    			//appraiseForm.appraiseBegin.focus();
    		}
      } });
 
      $('.datepicker4').datepicker({ onSelect: function(dateText, inst) {
    	  var beginStr = appraiseForm.appraiseBegin.value;
    		var endStr = appraiseForm.appraiseEnd.value;
    		var beginDate = new Date(beginStr);
    		var endDate = new Date(endStr);
    		if(!(checkTimePattern(endStr))) {
    			alert("评审截止时间格式不匹配");
    			//appraiseForm.appraiseEnd.focus();
    		}else if(getNowDate() >= endDate) {
    			alert("该时间已过，不可设置该时间");
    			//appraiseForm.appraiseEnd.focus();
    		}else if(beginDate >= endDate) {
    			alert("开始和截止时间有冲突");
    			//appraiseForm.appraiseEnd.focus();
    		}
      } });
} 

function modifyProportion() {
	 var  username = window.prompt("输入你的账号:"); 
	 if(username == '' || username==undefined || username=='undefined' || username.trim()=="") {
		alert("帐号输入错误");
	 }else {
		$.getJSON("../manager/checkAdminAgain.do",{"username":username},function(data){
        	 if(0==data.result){
        		 alert("帐号输入错误");
        	 }else if(1==data.result){
        		 $.post(
        					"../manager/updateSettingContent.do",
        					{
        						"formId":proportionForm.formId.value,
        						"video":proportion2Format(proportionForm.video.value),
        						"picture":proportion2Format(proportionForm.picture.value),
        						"document":proportion2Format(proportionForm.document.value),
        					},
        					function(date) {
        						alert(date);
        						reload();
        					}
        		);
         	 }
    });
	}
	
}

function modifyApplyTime() {
	var  username = window.prompt("输入你的账号:");  
	 if(username=='' || username==undefined || username=='undefined' || username.trim()==""){
		 alert("请输入用户名");
	 }else{
        $.getJSON("../manager/checkAdminAgain.do",{"username":username},function(data){
            	 if(0==data.result){
            		 alert("帐号输入错误");
            	 }else if(1==data.result){
            		 $.post(
          					"../manager/updateSettingContent.do",
          					{
          						"formId":applyForm.formId.value,
          						"applyBegin":applyForm.applyBegin.value,
          						"applyEnd":applyForm.applyEnd.value,
          					},
          					function(date) {
          						alert(date);
          						reload();
          					}
          			);   
             	 }
        });
	 }
	
}

function modifyAppraiseTime() {
	var  username = window.prompt("输入你的账号:"); 
	 if(username == '' || username==undefined || username=='undefined' || username.trim()=="") {
		alert("帐号输入错误");
	 }else {
		$.getJSON("../manager/checkAdminAgain.do",{"username":username},function(data){
      	 if(0==data.result){
      		 alert("帐号输入错误");
      	 }else if(1==data.result){
      		 $.post(
					"../manager/updateSettingContent.do",
					{
						"formId":appraiseForm.formId.value,
						"appraiseBegin":appraiseForm.appraiseBegin.value,
						"appraiseEnd":appraiseForm.appraiseEnd.value,
					},
					function(date) {
						alert(date);
						reload();
					}
			); 
      	 }
	 });
	 }
	
}

function checkApplyBegin() {
	var beginStr = applyForm.applyBegin.value;
	if(!(checkTimePattern(beginStr))) {
		alert("申报开始时间格式不匹配");
		//applyForm.applyBegin.focus();
	}
}

function checkApplyEnd() {
	var endStr = applyForm.applyEnd.value;
	if(!(checkTimePattern(endStr))) {
 		alert("申报截止时间格式不匹配");
		//applyForm.applyEnd.focus();
 	}
}

function checkAppraiseBegin() {
	var beginStr = appraiseForm.appraiseBegin.value;
	if(!(checkTimePattern(beginStr))) {
		alert("评审开始时间格式不匹配");
		//appraiseForm.appraiseBegin.focus();
	}
}

function checkAppraiseEnd() {
	var endStr = appraiseForm.appraiseEnd.value;
	if(!(checkTimePattern(endStr))) {
		alert("评审截止时间格式不匹配");
			//appraiseForm.appraiseEnd.focus();
	}
}
/**
 *检测比例函数 
*/
function checkProportion() {
	var video = (proportionForm.video.value).split("%")[0];//获取比例的数值
	var picture = (proportionForm.picture.value).split("%")[0];
	var document = (proportionForm.document.value).split("%")[0];
	
	if(video >=100 || video <=0) {
		alert("视频比例值不在0-100之间");
		//proportionForm.video.focus();
	}else if(picture >=100 || picture <=0) {
		alert("展板比例值不在0-100之间");
		//proportionForm.picture.focus();
	}else if(document >=100 || document <=0) {
		alert("申报材料比例值不在0-100之间");
		//proportionForm.document.focus();
	}
}

/**
 *获取当前时间函数
 */
function getNowDate() {
	return new Date();
}

/**
 * 时间格式匹配函数
 */
function checkTimePattern(dateStr) {
	var reg = new RegExp("^([0-9]{4})[/-]([0]?[1-9]|[1|2][0-9]|[3][0|1])[/-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$");
	if(reg.test(dateStr)) {
		return true;
	}else {
		return false;
	}
}

function reload() {
	$.post(
			"../manager/showSettingContent.do", 
			{
			
			},
  			function(json){
  				//将进度条显示在页面上	
				var strApplyHtml = "<font size='2' face='幼圆'>设置报名开始时间:</font>" +
    			"<input type='text' "+ "class='datepicker1'" + " name='applyBegin' " + "onblur='checkApplyBegin()'"+ "value="+ stringFormat(json[0].applyBegin) + ">" +
    			" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"<font size='2' face='幼圆'>设置报名截止时间:</font>" + 
    			"<input type='text' "+ "class='datepicker2'" + " name='applyEnd'" + "onblur='checkApplyEnd()'" + "value="+ stringFormat(json[0].applyEnd) + ">";
    			$(".applySetting").html(strApplyHtml);
    			
    			var strAppraiseHtml = "<font size='2' face='幼圆'>设置评审开始时间:</font>" +
    			"<input type='text' "+ "class='datepicker3'" + " name='appraiseBegin' " + "onblur='checkAppraiseBegin()'" + "value="+ stringFormat(json[0].appraiseBegin) + ">" +
    			" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"<font size='2' face='幼圆'>设置评审截止时间:</font>" + 
    			"<input type='text' "+ "class='datepicker4'" + " name='appraiseEnd' "  + "onblur='checkAppraiseEnd()'" +  "value="+ stringFormat(json[0].appraiseEnd) +">";
  				$(".appraiseSetting").html(strAppraiseHtml);
  				
  				var strProportionHtml = "<label for='amount1'>视频所占比例：</label>" +
  				"<input type='text' id='amount1' name='video' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].video) + ">" +
  				" <div id='slider-range-min1' style='width:1000px'></div></br>" +
  				"<label for='amount1'>展板所占比：</label>" + 
  				"<input type='text' id='amount2' name='picture' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].picture) + ">" +
  				"<div id='slider-range-min2' style='width:1000px'></div></br>" +
  				"<label for='amount1'>申报材料所占比例：</label>" +
  				"<input type='text' id='amount3' name='document' style='border: 0; color: #f6931f; font-weight: bold; font-size:25px' onblur='checkProportion()'" + "value="+ proportion2Format(json[0].document) + ">" +
  				"<div id='slider-range-min3' style='width:1000px'></div></br>";
  				$(".proportionSetting").html(strProportionHtml);
  				loadSible(json[0].video,json[0].picture,json[0].document);
  				
  			},
  			"json"
  		);
}
