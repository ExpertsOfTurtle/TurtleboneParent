var PROFILE = {
	username : null,
	tokenId : null
}
function onLogin() {
	var username = $("#loginUsername").val();
	var password = $("#loginPassword").val();
	var datestr = new Date().Format("yyyyMMddhhmmss");
	var md5_1 = hex_md5(username+password);
	var key = datestr + md5_1;
	var md5_2 = hex_md5(key);
	var param = {
		username:username,
		datetime:datestr,
		signData:md5_2
	};
	var rs = $.ajax({
		type : "POST",
		url : "/auth/token/create",
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		beforeSend: function(){
            console.log("create token");
        },
        success : function(result) {
			console.log(result);
			var json = null;
			var rs = "";
			try {
				json = JSON.parse(result);
				rs = "Success，your tokenId:" + json.tokenid;
				PROFILE.tokenId = json.tokenid;
				PROFILE.username = username;
				console.log(json);
			} catch(e) {
				console.log(e);
				rs = result;
			}
			$("#loginResult").html(rs);
		},
		error : function() {
			console.log("fail");
			$("#loginResult").html("Fail");
		}
	});
	return rs;
}