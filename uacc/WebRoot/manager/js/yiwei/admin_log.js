$(document).ready(function() {
	$.post(
		"../manager/showLogInfo.do",
		{
			logdate : "",
		},
		function(json){
			var strHtml = "<table width='100%' cellspacing='1' id='customers' margin='0'>"
				+ "<tr>"
				+ "<th><div align='left'>日志内容</div></th>";
			if(json.length > 1) {
				alert(json[json.length-1].msg);
				for ( var i = 0; i < json.length-1; i++) {
					strHtml += "<tr><td>"
						+ logdateFormat(json[i].date)
						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
						+ json[i].operator
						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
						+ json[i].content
						+ "</td></tr>";
				}
			}else {
				alert(json[json.length-1].msg);
				strHtml += "<tr><td>无相符内容</td></tr>";
				strHtml += "</table>";
			}
			$(".logForm").html(strHtml);
			load(json.length-1);
		},
		"json"
	);
});

function findLog() {
	$.post(
		"../manager/showLogInfo.do",
		{
			logdate : $("#logId").val(),
		},
		function(json) {
			var strHtml = "<table width='100%' cellspacing='1' id='customers' margin='0'>"
				+ "<tr>"
				+ "<th><div align='left'>日志内容</div></th>";
			if(json.length > 1) {
				alert(json[json.length-1].msg);
				for ( var i = 0; i < json.length-1; i++) {
					strHtml += "<tr><td>"
						+ logdateFormat(json[i].date)
						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
						+ json[i].operator
						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
						+ json[i].content
						+ "</td></tr>";
				}
			}else {
				alert(json[json.length-1].msg);
				strHtml += "<tr><td>无相符内容</td></tr>";
				strHtml += "</table>";
			}
			$(".logForm").html(strHtml);
			load(json.length-1);
		},
		"json"
	);
}

function load(num) {
	$( ".datepicker" ).datepicker();
	$('#smart-paginator').smartpaginator({
		totalrecords : num,
		recordsperpage : 10,
		go : '前往',
		theme : 'green',
		datacontainer : 'customers',
		dataelement : 'tr'
	});
}

function SelectAll(target) {
	var items = document.getElementsByTagName("input");
	for ( var i = 0; i < items.length; i++) {
		if (items[i].type == "checkbox") {
		    items[i].checked = target.checked;
		}
	}
}
