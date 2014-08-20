 var highlightindex = -1;
$(document).ready(
		function() {
		 // 获得输入框节点
			var inputItem1 = $("#searchSchool");
			var inputOffset1 = inputItem1.offset();
			var autonode1 = $("#auto1");
		 // 设置提示框隐藏
			autonode1.hide().css("background-color","white").css("border", "1px black solid").css("position",
					"absolute").css("top",
					inputOffset1.top + inputItem1.height() - 185 + "px").css(
					"left", inputOffset1.left-200 + "px").width(
					inputItem1.width() + "px");

			// 当键盘抬起时触发事件执行访问服务器业务
			$("#searchSchool").keyup(
					function(event) {
						enventAtion(event,1,"school",autonode1,"searchSchool");
					});// 键盘抬起

			// 当文本框失去焦点时的做法
			inputItem1.focusout(function() {
				// 隐藏提示框
				highlightindex = -1;
				autonode1.empty();
				autonode1.hide();
			});
			
			
			
			 // 获得输入框节点
			var inputItem2 = $("#searchCommunity");
			var inputOffset2 = inputItem2.offset();
			var autonode2 = $("#auto2");
			// 设置提示框隐藏
			autonode2.hide().css("background-color","white").css("border", "1px black solid").css("position",
					"absolute").css("top",
					inputOffset2.top + inputItem2.height()  - 185  + "px").css(
					"left", inputOffset2.left-200 + "px").width(
					inputItem2.width() + "px");

			// 当键盘抬起时触发事件执行访问服务器业务
			$("#searchCommunity").keyup(
					function(event) {
						enventAtion(event,2,"communityName",autonode2,"searchCommunity");
					});// 键盘抬起

			// 当文本框失去焦点时的做法
			inputItem2.focusout(function() {
				// 隐藏提示框
				highlightindex = -1;
				autonode2.empty();
				autonode2.hide();
			});
	 
			
			
			/**专家提示**/
			
			 // 获得输入框节点
			var inputItem3 = $("#content3");
			var inputOffset3 = inputItem3.offset();
			var autonode3 = $("#auto3");
			// 设置提示框隐藏
			autonode3.hide().css("border", "1px black solid").css("position",
					"absolute").css("top",
					inputOffset3.top + inputItem3.height() + 5 + "px").css(
					"left", inputOffset3.left + "px").width(
					inputItem3.width() + "px");

			// 当键盘抬起时触发事件执行访问服务器业务
			$("#content3").keyup(
					function(event) {
						enventAtion(event,3,"expert",autonode3);
					});// 键盘抬起

			// 当文本框失去焦点时的做法
			inputItem3.focusout(function() {
				// 隐藏提示框
				highlightindex = -1;
				autonode3.empty();
				autonode3.hide();
			});
			
		});// reday

 


function getResult(word,flag,where,id) {
	
	// 延迟提交，这边设置的为400ms
	// 将文本框的内容发到服务器
 
	
	$.getJSON("./searchAuto.do", {
		 wordtext:encodeURI(word,"utf-8"),
		 condition:where
	}, function(data) {
	     
		// 将返回数据转换为JQuery对象
		$("#auto"+flag).html(" ");
		$.each(data, function(index, value) {
			// 获得返回的单词内容
            
			var newNode = $("<div>").html(value.word).attr("id", index);
			// 将返回内容附加到页面
			newNode.appendTo($("#auto"+flag));
			// 处理鼠标事件
			// 鼠标经过
			newNode.mouseover(function() {
				highlightindex = $(this).attr("id");
				$(this).css("background-color", "#ADD8E6");
				$("#"+id).val($(this).text());
			});
			// 鼠标离开
			newNode.mouseout(function() {
				$(this).css("background-color", "white");
				highlightindex = -1;
			});
			// 鼠标点击
			newNode.click(function() {
				highlightindex = -1;
				autonode.hide();
			});

		}); // each

		// 当返回的数据长度大于0才显示
		if (data.length > 0) {
			$("#auto"+flag).show();
		} else {
			$("#auto"+flag).hide();
		}
	});
 }

function  enventAtion(event,flag,where,autonode,id){
	var myevent = event || window.event;
	var mykeyCode = myevent.keyCode;
	// 字母，退格，删除，空格
	if (mykeyCode >= 65 && mykeyCode <= 90
			|| mykeyCode == 8 || mykeyCode == 46
			|| mykeyCode == 32) {
	 
		// 获得文本框内容
		var word = $("#"+id).val(); 
		 
	 
		if (word != "") {
			// 取消上次提交

			$("#auto"+flag).html(" ");
		    getResult(word,flag,where,id);
		 } else {
			autonode.hide();
			highlightindex = -1;
		}
	} else {
		// 获得返回框中的值
		var rvalue = $("#auto"+flag).children("div");
		// 上下键
		if (mykeyCode == 38 || mykeyCode == 40) {
			// 向上
			if (mykeyCode == 38) {
				if (highlightindex != -1) {
					$("#" + highlightindex).css(
							"background-color", "white");
					highlightindex--;
				}
				if (highlightindex == -1) {
					$("#" + highlightindex).css(
							"background-color", "white");
					highlightindex = rvalue.length - 1;
				}
				$("#" + highlightindex).css(
						"background-color", "#ADD8E6");
				$("#"+id).val(
						$("#" + highlightindex).text());
			}
			// 向下
			if (mykeyCode == 40) {

				if (highlightindex != rvalue.length) {
					$("#" + highlightindex).css(
							"background-color", "white");
					highlightindex++;
				}
				if (highlightindex == rvalue.length) {
					$("#" + highlightindex).css(
							"background-color", "white");
					highlightindex = 0;
				}

				$("#" + highlightindex).css(
						"background-color", "#ADD8E6");
				$("#"+id).val(
						$("#" + highlightindex).text());
			}
		}
		// 回车键
		if (mykeyCode == 13) {
			if (highlightindex != -1) {
				$("#"+id).val(
						$("#" + highlightindex).text());
				highlightindex = -1;
				autonode.hide();
			}
		}
	}
	} 