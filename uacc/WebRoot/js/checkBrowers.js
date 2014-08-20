
function flashChecker(){
	var hasFlash = 0;        		 //是否安装了flash
	var flashVersion = 0; 	 		 //flash版本
	var isIE = /*@cc_on!@*/0;        //是否IE浏览器
	if(isIE){
		try{
		var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
		if(swf){
			hasFlash = 1;
			VSwf = swf.GetVariable('$version');
			flashVersion = parseInt(VSwf.split(' ')[1].split(',')[0]);
		}
		}catch(e){
  			}
	}
	else{
		if (navigator.plugins && navigator.plugins.length > 0){
			var swf = navigator.plugins['Shockwave Flash'];
			if (swf){
				hasFlash = 1;
				var words = swf.description.split(' ');
				for (var i = 0; i < words.length; ++i)
				{
					if (isNaN(parseInt(words[i]))) continue;
					flashVersion = parseInt(words[i]);
				}
			}
		}
	}
	return {f:hasFlash,v:flashVersion};
}


var fls = flashChecker();
if(fls.f){
 

}
else{
  var div  = $("<div id='brower' align='center'></div>"); 
   alert("您的浏览器没有安装Flash,将不能使用上传作品观赏视频等功能!请您下载,或者更换浏览器!");
	div.html("您的浏览器没有安装Flash,将不能使用<font color='red'>上传作品</font>、<font color='red'>观赏视频</font>等功能!请您下载<a href='http://get.adobe.com/cn/flashplayer/' target='_blank'>falsh player</a>,或者更换浏览器!");
       $("body").append(div); 
}
 

