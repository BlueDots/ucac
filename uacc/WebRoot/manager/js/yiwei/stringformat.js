function stringFormat(jsonString) {
	var strs = jsonString.split(" ");
	var datestrs = strs[0];
	var resultdate = datestrs.split("-");
	return resultdate[0] + "/" + resultdate[1] + "/" + +resultdate[2];
}

function proportion2Format(jsonPorprotion) {
	return  jsonPorprotion + "%";
	
}

function logdateFormat(logdate) {
	var strs = logdate.split(" ");
	return strs[0];
}