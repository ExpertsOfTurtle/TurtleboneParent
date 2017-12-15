var PROFILE = {
	username : getCookie("username"),
	tokenId : getCookie("tokenId")
}
function appendQueryURLForRequest() {
	return "?username=" + PROFILE.username + "&tokenId=" + PROFILE.tokenId;
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
