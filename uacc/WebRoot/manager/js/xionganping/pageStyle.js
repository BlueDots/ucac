var queryResult=null;
var accountResult=null;/**保存生成的专家帐号**/
/***开始给分页按钮做样式**/
$(document).ready(function(){
	 
    /**鼠标经过时**/
 	$(".page_nav").mouseover(function(){
 		/**先全部设置为白的**/
		$(".page_nav").css({
			"background": "none repeat scroll 0 0 #FFFFFF",
			 "border": "1px solid #D7E2EE",
	         "border-radius": "3px 3px 3px 3px",
	         "color": "#333333",
	         "padding": "3px 7px",
	         "cursor":"pointer",
	         "text-decoration": "none" 
				});	
		/**在修改经过是的边框**/
 		$(this).css( {
 			 "border": "1px solid #4D90FE",
 		   "text-decoration": "none"});
	 });
 	/**鼠标移除时**/
	$(".page_nav").mouseout(function(){
 		/**先全部设置为白的**/
		$(".page_nav").css({
			"background": "none repeat scroll 0 0 #FFFFFF",
			 "border": "1px solid #D7E2EE",
	     "border-radius": "3px 3px 3px 3px",
	     "color": "#333333",
	    "padding": "3px 7px",
	    "cursor":"pointer",
	    "text-decoration": "none" });
				});	 
	
	
	 
		var progressbar = $( "#progressbar" );
		$( "#progressbar" ).hide();
		progressLabel = $( ".progress-label" );
		progressbar.progressbar({
		value: false,
		change: function() {
		progressLabel.text( progressbar.progressbar( "value" ) + "%" );
		},
		complete: function() {
		progressLabel.text( "Complete!" );
		}
		}); 
		
		
		/**给下拉选择框添加监听事件**/
		$("#categorySelect").change(function(){
	        back($(this)[0].value);
		});
		 
});

/*****************************************************动态加载查看部分**********************************************/
/**
 * 给分页们做的点击事件监听事件
 * **/
function  getValueByPage(value){
	 
     /**点击事件**/
  
		/**先全部设置为白的**/
		$(".page_nav").css({
			"background": "none repeat scroll 0 0 #FFFFFF",
			 "border": "1px solid #D7E2EE",
	     "border-radius": "3px 3px 3px 3px",
	     "color": "#333333",
	    "padding": "3px 7px",
	    "cursor":"pointer",
	    "text-decoration": "none" 
				});	
		/**在设置为黑的**/
	  $(this).css({
		  "background": "none repeat scroll 0 0 #006699",
	      "border": "0 none",
	     "border-radius": "3px 3px 3px 3px",
	     "color": "#FFFFFF",
	     "padding": "3px 7px",
	     "cursor":"pointer",
	     "text-decoration": "none" 
	  }); 
	  $( "#progressbar" ).show();
 
	   
	   
	   /**拿到需要加载的分类**/
	   
	   var category = $("#categorySelect")[0].value ;
	  
	  /**开始动态加载数据**/ 
	  $.getJSON("./showExperts.do", {
			 requestUrl:123,
			 requestType:"AJAX",
			 page:value,
			 category:category
		}, function(data) {
		
	     /**将进度条显示在页面上	**/	
		 $( "#progressbar" ).progressbar( "value",  100 );
		 $( "#progressbar" ).fadeOut("slow"); 
		   if(data.errorMesg !=null){
			 /**如果数据为空的话,那我就弹出出错的提醒**/
		     alert(data.errorMesg);   	
		 }else{
			  /**否则将数据显示在html 的table里面上**/
			 var  table  = $("#customers");
			 var  firstTr =$("#customers tr:first"); 
			 var  lastTr  = $("#customers tr:last"); 
			 /**清空表格**/
			 table.empty();
			 /**插入表头**/
			 table.append(firstTr);
			 /**插入数据**/
			 
			 var  results  = data[0].results; 
			 var  page  = data[1];
			 
			 for(var i = 0 ;i<results.length;i++){
			  	var tr  = $("<tr></tr>");
			  	/**准备好所有的列**/
			  	var  td1 = $("<td><div align='center'><input name='' type='checkbox' value=''></div></td");
			  	var  num  = (page.page-1)*page.maxSize+i+1;
			  	var  td2 =  $("<td >"+num+"</td>");
			  	var  hiddenInput  =$("<input type='hidden' name='id' value="+results[i].id+">");
			  	td2.append(hiddenInput);
			  	var  td3 = $("<td>"+results[i].username+"</td>");
			  	var  hiddenInput2  =$("<input type='hidden' name='username' value="+results[i].username+">");
			  	td3.append(hiddenInput2);
			  	var  td4 = $("<td>******</td>");
			  	var  td5  =null;
			  	switch (results[i].category) {
						case 1:td5=$("<td>理论学习类</td>");break;
						case 2:td5=$("<td>学术科技类</td>");break;
						case 3:td5=$("<td>文艺体育类</td>");break;
						case 4:td5=$("<td>社会公益类</td>");break;
						case 5:td5=$("<td>实践创新类</td>");break;
						case 6:td5=$("<td>其它类</td>");break;
						}
			  	var td6 = $("<td>"+results[i].expertName+"</td>");		
			  	var td7 =$("<td>"+results[i].expertTel+"</td>");	
				var td8 =$("<td>"+results[i].expertEmail+"</td>");
				/**准备好所有的列**/
				tr.append(td1);tr.append(td2);tr.append(td3);
				tr.append(td4);tr.append(td5);tr.append(td6);
				tr.append(td7);tr.append(td8);
				/**准备好所有的列**/
				table.append(tr);
			 }
			 /**插入尾部  结束**/
			 if(data!=null){
				 table.append(lastTr); 
			 }
		  }
		});
 }


/*********************************************************生成帐号部分********************************************************/

/**对生成帐号数量做一个动态的加载,并且在这里判断是现成的添加还是减少**/
function autoMakeAccount(){
	/**如果输入框中的数据为null**/
	 var  table  = $("#customers");
	 var  tabelTh = $("#customers tr");
	 var  firstTr =$("#customers tr:first"); 
	 var  lastTr  = $("#customers tr:last"); 
	 var  num  = $("#accountNum")[0].value;
	 if(tabelTh[1]!=null &&tabelTh[1].children[6].firstChild.localName=="input"){
		/**表示已经存在了 并且是加 需要对新和老的做一个验证**/
		  var thLength = tabelTh.length;
		
		 if(num>(thLength-2)){
	 	  /**添加之前先得把底部删除啊**/
		  var  addnum=num-thLength+2;
	      lastTr.remove();
		  if(0<num && num<100){
			  var  con = confirm("确认添加"+addnum+"条,注意添加的类型?");
			  if(con){
				  chooseWayToAdd(addnum,table);
			  }
		  }else{
			  alert("数据总量不能超过100,请重新输入");
		  }
	  }	else if(0<num && num<(thLength-2)){
		 /**表示直接是减**/
		  var  removeNum = thLength-num-2;
		  var  con = confirm("确认删除"+removeNum+"条?");
		  if(con){
			   for(var i=thLength-1;i>num;i--){
				   $(tabelTh[i]).remove();
			   }
		  }
		  table.append(lastTr);
	  }else{
		  alert("输入数据有误");
	  }
	}else{
		/**否则是需要清空重新添加的**/
	
		 if(0<num &&num<100){
			 var  con =null;
			 if(num>20){
	        	 con = confirm("一次添加"+num+"条容易出错,建议输入20,确定添加,注意添加的类型?"); 
			 }else{
				 con = confirm("确认添加"+num+"条,注意添加的类型?");
			 }
			  
			 /**开始添加新的***/
			 if(con){
				 /**先清空表格**/
				 table.empty();
				 table.append(firstTr);
				 chooseWayToAdd(num,table);
				
		       }
		 }else{
			 alert("输入的数量不正确,数量一次不能超多100");
		 }
	}
	
	
	
}
/**在这里选择增减**/
function  chooseWayToAdd(num,table){
	 var category = $("#categorySelect")[0].value ;
	 /**开始生成帐号 首先显示进度条**/
	 $( "#progressbar" ).show();
	 if(queryResult==null){ 
	     /**如果全局变量没有值,我们就重新去拿 **/
		 $.getJSON("./showExperts.do",{
		 requestUrl:123,
		 requestType:"AJAX",
		 maxSize:1000
	     },function(data){
	    	 if(data.errorMesg==null){
		      queryResult=data;/** 先把它保存为全局的**/
	    	 }else{
	    		 queryResult==false;
	    	 }
		    /**开始生成帐号，账号是默认一个字母,3个数字**/
		   /**插入表头**/
		    makeAccountToscream(category,num,data);
		     /**最后加上提交 和取消的按钮**/
	        var lastTr =  $("<tr><td colspan='8' style='border: 0 white;'><div align='right'>	<input type='submit'   value='提交'   /><input type='button'    value='取消' onClick='back("+category+")' /></div></td></tr>");
	        table.append(lastTr);  
	      /**将进度条显示在页面上	**/	
			 $( "#progressbar" ).progressbar( "value",  100 );
			 $( "#progressbar" ).fadeOut("slow");
	   });
		 
	 }else{
		 /**如果有值,我们就不需要再去拿值**/
		    makeAccountToscream(category,num,queryResult);
		     /**最后加上提交 和取消的按钮**/
	        var lastTr =  $("<tr><td colspan='8' style='border: 0 white;'><div align='right'>	<input type='submit'   value='提交'   /><input type='button'    value='取消' onClick='back("+category+")' /></div></td></tr>");
	        table.append(lastTr);  
	         /**将进度条显示在页面上	**/	
			 $( "#progressbar" ).progressbar( "value",  100 );
	    	 $( "#progressbar" ).fadeOut("slow");
	 }
}

function makeAccountToscream(category,num,data){
	   
	  var  allAccount= showAccount(num,data);
	  /**保存生成的帐号 ,用来做增减的判断**/
	  accountResult=allAccount;
	  /**生成的帐号现在需要去生成表格的tr**/
	  var  table  = $("#customers");
      var  tabelTh = $("#customers tr");
	  var  oldSize=0; 
      if(tabelTh.length>1){
    	  oldSize=tabelTh.length-1; 
	   }
	  /**插入数据**/
      for(var  i=0;i<allAccount.length;i++){
    	  var size  = i+1+oldSize;
         var tr = $("<tr></tr>");
 	  	var  td1 = $("<td><div align='center'><input name='' type='checkbox' value=''></div></td");
	    var  td2 =  $("<td >"+size+"</td>");
	    var  td3 = $("<td>"+allAccount[i]+"</td>");
	    var  hiddeName  = $("<input type='hidden' name='username' value="+allAccount[i]+">");
	    td3.append(hiddeName);
	    var  td4 = $("<td>*******</td>");
	    var  td5 =$("<td></td>");
	    
	    var  select =$("<select name='category' style='border:1px solid #0033FF'></select>");
	    var  option1 =$("<option value='1' >理论学习类</option>");
	    var  option2 =$("<option value='2' >学术科技类</option>");
	    var  option3 =$("<option value='3' >文艺体育类</option>");
	    var  option4 =$("<option value='4' >社会公益类</option>");
	    var  option5 =$("<option value='5' >实践创新类</option>");
	    var  option6 =$("<option value='6' >其它类</option>");
	    
	    switch (category) {
		case "1":option1.attr("selected","selected");break;
		case "2":option2.attr("selected","selected");break;
		case "3":option3.attr("selected","selected");break;
		case "4":option4.attr("selected","selected");break;
		case "5":option5.attr("selected","selected");break;
		case "6":option6.attr("selected","selected");break;
		}
	    
	    select.append(option1);select.append(option2);select.append(option3);
	    select.append(option4);select.append(option5);select.append(option6);
	    td5.append(select);
        var  td6 =$("<td><input type='text' name='expertName'></td>");
        var  td7 =$("<td><input type='text' name='expertTel'></td>");
        var  td8 =$("<td><input type='text' name='expertEmail'></td>");
		tr.append(td1);tr.append(td2);tr.append(td3);
		tr.append(td4);tr.append(td5);tr.append(td6);
		tr.append(td7);tr.append(td8);
		table.append(tr);
      } 		 
    
}

function back(category){
	window.location="./showExperts.do?requestUrl=expertInfo&category="+category;
}

/**生成随机字母**/
function getPass()
{
	var tmpCh = "";
    tmpCh += String.fromCharCode(Math.floor( Math.random() * 26) + "a".charCodeAt(0)); 
    return tmpCh;
}

/**生成帐号算法**/
function  accountMath(){
	var  a=new Array(); 
     var  b  = new Array();
	/**首先是随机的拿到1 ， 2,3 ，4位 ,用来生成字母**/
	lenPass = Math.round(Math.random()*3);
	a[lenPass]=getPass(); 
	b.push(lenPass);
    for(var  h=0 ;h<3;h++){
        /**在随机拿 1 2 3 4 中剩下的值 来生成数字**/
		    for(var k =0 ;;k++){ 
		    	var  flag  = 1 ;
		    	var len  = Math.round(Math.random()*3);
		    	/**在1 2 3 4 中生成随机数并检测是否已经存在**/
		    
		        for(var i =0 ;i<b.length;i++){
		    	  if(len==b[i]){
		    		   flag  = 0 ;
		    			break;
		    		}
		    	}
		        if(flag==1){
		        	/**表示不存在,那随机放入一个值**/
		        	 
		        	b.push(len);
		        	a[len] =  Math.round(Math.random()*9)+0;
		        	 break;
		        }	
		      }
	  }
    return a.join("");
 
}
/**过滤账号 传入一个帐号 并且需要匹配本账号,需要和数据库帐号比较  data是数据库中的数据 正确返回true,错误返回false **/
function filterAccount(account,allAccount,data){
	/**先检测下自己的先**/
	var   result  = true;
	for(var i = 0 ;i<allAccount.length;i++){
		if(account == allAccount[i]){
		result = false;
		break;
		}
	}
	
	if(result==false){
		return result;
	} 
	/**过滤页面***/
	 var  tabelTh = $("#customers tr");
	if(accountResult!=null){
		for(var i=0;i<accountResult.length;i++){
			if(accountResult[i]==account){
				result =false;
				break;
			}
		}
	 
		if(!result){
			return result;
		}
	} else  if(tabelTh.length>1 && tabelTh[1].children[6].firstChild.localName=="input"){
		for(var i=1;i<tabelTh.length-1;i++){
			if(tabelTh[i].children[2].innerHTML==account){
				result =false;
				break;
			}
		}
	 
		if(!result){
			return result;
		}
		
	}
	/**过滤数据库同名***/
  if(data.errorMesg == null){
	var  queryResult = data[0].results;
	for(var h = 0 ;h<queryResult.length;h++){
		if(queryResult[h]==account){
			result=false;
		}
	}
	 }
	return result;
	/**在检测data**/
}

/**拿到最终的帐号  在这里生成20个账号**/
function  showAccount(count,data){
	 var   allAccount  = new Array();
	 var   first  = 0 ;
	for(var i =0 ;;i++){
		var account=accountMath();
		 if(filterAccount( account,allAccount,data)){
			 allAccount.push(account);
			 first++;
		 }
		 if(first==count){
			 break;
		 }
	} 
	return allAccount;
}
/***************************************************提交专家帐号******************************************************/
/**判断提交的数据中是否有电话重复和邮箱重复 num=1表示和自己比**/
function checkTelAndEmail(arrayObject,data,num){
	 var  flag =true; 
	 
	for(var i =0 ;i<data.length;i++){
		 
		if(num==1 &&(arrayObject.expertTel != undefined  || arrayObject.expertEmail!= undefined)){
		    if(arrayObject.id!=data[i].id && arrayObject.expertTel != undefined  && arrayObject.expertTel == data[i].expertTel){
		    	flag = false;
			    break;
		    }
			if(arrayObject.id!=data[i].id &&  arrayObject.expertEmail!= undefined && arrayObject.expertEmail==data[i].expertEmail){
				flag = false;
			    break;
			}
			 	
		 }else if(num!=1 &&(arrayObject.expertTel != undefined  ||  arrayObject.expertEmail!= undefined)){
			  
			 if(arrayObject.expertTel != undefined && arrayObject.expertTel ==data[i].expertTel){
				 flag = false;
				 break;
			 }
			 /***在这里判断更新的**/
			 if(arrayObject.expertEmail!= undefined  && arrayObject.expertEmail==data[i].expertEmail){
				 flag = false;
				 break;
			 } 
			
		 }
	 }
	return flag;
}

function  TableObject(id,expertTel,expertEmail,msg,expertName){
	 this.id=id;
	 this.expertTel=expertTel;
	 this.expertEmail=expertEmail;
	 this.msg = msg;
	 this.expertName=expertName;
}
 
 
/**将表格的数据中的数据转化问数组对象**/
function  makeTableToArrayObject(allTh){
     var	arrayObject = new Array(); 
	 for(var i =1 ;i<allTh.length-1;i++){
    	 var  id = allTh[i].children[1].firstChild.textContent;
    	 var  expertTel = allTh[i].children[6].firstChild.value;
    	 var  expertEmail = allTh[i].children[7].firstChild.value;
    	 var  expertName = allTh[i].children[2].firstChild.textContent;
    	 
    	 /**数据不能为空**/
    	 if(allTh[i].children[6].firstChild.localName == "input" &&$.trim(expertTel)==""){
    	   break;
    	   }
    	 if(allTh[i].children[7].firstChild.localName == "input" && $.trim(expertEmail)==""){
    		   break;
    	  }
    	 if(allTh[i].children[5].firstChild.localName == "input" && $.trim(allTh[i].children[5].firstChild.value)==""){
    		   break;
    	  }
         
    	 var object  = new TableObject(id,expertTel,expertEmail,null,expertName);
         arrayObject.push(object);
    	  
      }
    
   return arrayObject;
}
/**拿到错误的数组对象**/
function  getErrorArrayObject(arrayObject,allTh){
	  
	  var errorObject = new Array();
	  var flag  = true;   /**1 代表的是好的   0代表的是自己的出错了,2代表数据库出错**/
	 
	  /**先过滤自己的额**/
	 
	 for(var i=0 ;i<arrayObject.length;i++){
		 
		 if(!checkTelAndEmail(arrayObject[i],arrayObject,1)){
			 arrayObject[i].msg="你填写了相同的邮箱";
			 errorObject.push(arrayObject[i]);
			 flag=false;/**在自己的部分中重复了**/
		 }
	 }
	 
	 
	 if(flag){
		
		 /**如果自己的没有错误,则过滤数据库的**/
		 if(queryResult!=null){/**如果数据存在前段**/
			 
			 var  dbresults = queryResult[0].results;
			 for(var h =0 ;h<arrayObject.length;h++){
				 if(!checkTelAndEmail(arrayObject[h],dbresults,0)){
					 arrayObject[h].msg="你填写的邮箱和数据库中邮箱重复";
					 errorObject.push(arrayObject[h]);
					 flag  =false;
				   }
			 }
	 
		 
		  } 
	 } 
	
	  if(!flag){
		  //渲染表格
		 renderTable(errorObject,allTh); 
	 }
	 return  flag;
  
}
/**渲染表格 填写重复着显示黄色,数据库重复用红色**/
function  renderTable(errorObject,th){
	  /**先将所有行的第二列样式清掉**/
   
	 
	  for(var  h=1;h<th.length-1;h++){
		  th[h].children[2].bgColor="white";  
	  }
	  
	  for(var i=0;i<errorObject.length;i++){
    	var  id  = new Number(errorObject[i].id);
    	var  color = null;
    	if(errorObject[i].msg=="你填写了相同的邮箱"){
    		color="yellow";
    	}else if(errorObject[i].msg=="你填写的邮箱和数据库中邮箱重复"){
    		color="red";
    	}else  if(errorObject[i].msg=="邮箱不正确"){
    		color = "green"
    	}else if(errorObject[i].msg=="电话不正确"){
    		color = "blue"
    	}
      
       var  td=th[id].children[2];
         td.bgColor=color;
	   }
	  alert("请注意检查,是不是电话或邮箱重复或者格式不正确[黄色:页面中重复],[红色:与数据库中重复]，[绿色:邮箱格式不对],[蓝色:电话格式不对]");
   
	 
}

/**********************************************最终提交的时候做的判断**************************************/
function  checkAll(){
	var  flag  =true;
	var  tabelTh = $("#customers tr");
 	var   arrayObject = makeTableToArrayObject(tabelTh);
 	
 	
 	if((tabelTh.length-2)!=arrayObject.length){
 		flag=false;
 		alert("漏填数据了!");
 	}else{
 		 flag  = getErrorEmailORTel(arrayObject,tabelTh);
 	}
 	
  
	if(flag ){
		flag=getErrorArrayObject(arrayObject,tabelTh);	
	}
	 return flag;
}
var   text  = 0 ;
var  oldData =null;//先保存一个旧的数据
/**********************************************修改时的响应动作************************************************/
function  updateAction(){
	 $(".updateButton").toggle();
	 alert("现在,你可以点击你需要修改的专家帐号进行修改了");
	 /**给表格添加点击事件**/
	 $("#customers").click(function(eventObject){
		 var  eventObject  = eventObject;
		  /**拿到事件源 **/
		 var  eventTarget= eventObject.target;
		 /**判断是不是第 5 6 7 列**/
		 if(eventTarget.cellIndex==5 || eventTarget.cellIndex==6 || eventTarget.cellIndex==7 || eventTarget.cellIndex==4){
			/**去后台加载数据***/
			 if(queryResult==null){
				  $.getJSON("./showExperts.do",{
						 requestUrl:123,
						 requestType:"AJAX",
						 maxSize:1000
					     },function(data){
					    	 if(data.errorMesg==null){
					 		    queryResult=data;/** 先把它保存为全局的**/
					 	    	 }else{
					 	    		queryResult=false;
					 	    	 }
						 });
			 } 
			 
			 
			 /**开始将td变成文本框，当然先保存老的数据**/
			 if(eventTarget.firstChild!=null){
			   if(eventTarget.firstChild.localName=="input"){
				 oldData = eventTarget.firstChild.value;
			    }else{
				 oldData= eventTarget.innerHTML;
			   }
			 }
			 /**将dom对象转化为jquery对象**/
			 var $enventTarget = $(eventTarget);
			 var  idValue  = eventTarget.parentElement.children[1].firstElementChild.defaultValue;
			 var $tr  =$(eventTarget.parentElement.children[2]);
			 var  username = $tr[0].textContent  ;
			 $tr.empty();
			 $tr.html(username);
			 $tr.append("<input type='hidden' value="+username+" name='username"+idValue+"'>");
			/********************将分类加上********/
			if(text==0){
			 var  tabelTh = $("#customers tr");
			
			    for(var hh =1 ; hh<tabelTh.length-1;hh++){
			    	var idText  = tabelTh[hh].children[1].firstElementChild.defaultValue;
			    	  var  option1 =$("<option value='1' >理论学习类</option>");
					    var  option2 =$("<option value='2' >学术科技类</option>");
					    var  option3 =$("<option value='3' >文艺体育类</option>");
					    var  option4 =$("<option value='4' >社会公益类</option>");
					    var  option5 =$("<option value='5' >实践创新类</option>");
					    var  option6 =$("<option value='6' >其它类</option>");
			    	  switch ($.trim(tabelTh[hh].children[4].textContent)) {
						case "理论学习类":option1.attr("selected","selected");break;
						case "学术科技类":option2.attr("selected","selected");break;
						case "文艺体育类":option3.attr("selected","selected");break;
						case "社会公益类":option4.attr("selected","selected");break;
						case "实践创新类":option5.attr("selected","selected");break;
						case "":option6.attr("selected","selected");break;
						}
			          var  select =$("<select name='category"+idText+"' style='border:1px solid #0033FF'></select>");
					    select.append(option1);select.append(option2);select.append(option3);
					    select.append(option4);select.append(option5);select.append(option6);
				    $(tabelTh[hh].children[4]).empty();
				    $(tabelTh[hh].children[4]).append(select);
			     }
			 }
			    text=1;
			 
			 switch(eventTarget.cellIndex){
			  case 5 :{$enventTarget.empty(); $enventTarget.append("<input type='text' name='expertName"+idValue+"' value="+oldData+">");}break;
			  case 6 :{$enventTarget.empty();$enventTarget.append("<input type='text' name='expertTel"+idValue+"' value="+oldData+">");}break;
			  case 7 :{$enventTarget.empty(); $enventTarget.append("<input type='text' name='expertEmail"+idValue+"' value="+oldData+">");}break;
			 }
		  }
	 });
}


/*****************************************************基本验证  正则****************************************/
/**电子邮箱的验证**/
function valid_email(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
	return patten.test(email);
}

/***电话号码的验证***/
function checkMobile(sMobile){
    
	  var  test  = true;
	  for(var i= 0 ;i<sMobile.length;i++){
		   if(sMobile[i]<'0' || sMobile[i]>'9'){
		      test=false;
			  break;	  
		  }
	  }
	  
      return test;
} 
 /*****验证电话和电子邮箱*******/
function  getErrorEmailORTel(arrayObject,tabelTh){
	var  errorList  = new Array(); 
	var flag  = true;
	for(var  i=0;i<arrayObject.length;i++){
		  /**如果是添加的话都是有值得,如果是更新的话,就有可能没有值**/
		  if(arrayObject[i].expertTel!=undefined){
			  if(!checkMobile(arrayObject[i].expertTel)){
				  flag=false;
				  arrayObject[i].msg="电话不正确";
				  errorList.push(arrayObject[i]);
			  }
		  }
		  
		  if(arrayObject[i].expertEmail!=undefined){
			  if(!valid_email(arrayObject[i].expertEmail)){
				  flag=false;
				  arrayObject[i].msg="邮箱不正确";
				  errorList.push(arrayObject[i]);
			  }
		  }
	  } 
	
	if(!flag){
		renderTable(errorList,tabelTh);
	}
	return flag;
 }
   

function exportExcel(url){
//     var  username = window.prompt("输入你的账号:");  
//	 if(username=='' || username==undefined || username=='undefined' || username.trim()==""){
//		 alert("请输入用户名");
//	 }else{
//         $.getJSON("./checkAdminAgain.do",{"username":username},function(data){
//             	 if(0==data.result){
//             		 alert("帐号输入错误");
//             	 }else if(1==data.result){
             		var category =$("#categorySelect").val();
             	    win  = window.open(url+"manager/exportExcel.do?type=expert&category="+category);
             		win.document.execCommand("SaveAs") ;
             	          		 
//              	 }
//         });
//	 }
//	
    
}


 
