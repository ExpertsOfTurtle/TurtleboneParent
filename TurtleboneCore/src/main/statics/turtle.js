var PROFILE = {
	username : getCookie("username"),
	tokenId : getCookie("tokenId")
}
function appendQueryURLForRequest() {
	return "?username=" + PROFILE.username + "&tokenId=" + PROFILE.tokenId;
}
function sendRequest(param, url, reponseType, callBack, failCallback) {
	var dataType = (reponseType == "json" ? reponseType : "text");
	var rs = $.ajax({
		type : "POST",
		url : url + appendQueryURLForRequest(),
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : dataType,
		success : callback,
		error : failCallback
	});
	return rs;
}
function loadPage(param, url, selStr, tipBefore, tipFail, successCallback, failCallback) {
	var dataType = "text";
	if (tipBefore) {
		$(selStr).html(tipBefore);
	} else{
		$(selStr).html("加載中...");
	}
	var rs = $.ajax({
		type : "POST",
		url : url + appendQueryURLForRequest(),
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : dataType,
		success : function (result) {
			$(selStr).html(result);
			if (successCallback) {
				successCallback(result);
			}
		},
		error : function () {
			if (tipFail) {
				$(selStr).html(tipFail);	
			} else {
				$(selStr).html("加載失敗");
			}
			if (failCallback) {
				failCallback();
			}
		}
	});
	return rs;
}
function defaultCallBack(result) {
	
}
function setCookie(name, value, second) {
	if (second == null || second == undefined) {
		second = 60 * 60 * 2;//2 hour
	}
	var exp = new Date();
	exp.setTime(exp.getTime() + second * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

// 读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
