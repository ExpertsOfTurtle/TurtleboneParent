var DREAM_PAGE = {
	pageOffset : 0,
	pageSize : 10
}
function onSubmit() {
	$("#dreamDebug").html("onSubmit<br>");
	var rs = uploadPic();
	$.when(rs).done(function(data){
		$("#dreamDebug").append("uploadPic done<br>");
		if (data == null || data.flag == undefined || data.flag == null || data.flag == true) {
			createDream();
		}
	});
}
function createDream() {
	var content = $("#content").val();
	var dreamer = $("#dreamer").val();
	var date = $("#dreamdate").val();
	if (dreamer == "" || content == "") {
		alert("Input content & select dreamer!");
		return;
	}
	var param = {
		"content" : content,
		"username" : dreamer,
		"datetime" : date,
		"dreampic" : IMG_PATH
	};
	var rs = $.ajax({
		type : "POST",
		url : "/core/dream/create",
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			alert("Success");
			$("#content").val("");
			$("#dreamer").val("");
			$("#dreamdate").val("");
		},
		error : function() {

		}
	});
	return rs;
}
function loadNext() {
	var param = {
		"type" : "",
		"pageSize" : DREAM_PAGE.pageSize,
		"offset" : DREAM_PAGE.pageOffset
	}
	var rs = $.ajax({
		type : "POST",
		url : "/core/dream/query",
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			wf = result;
			if (result != null && result.length > 0) {
				$("#listDreamUl").append(result);
				$('#listDreamUl').listview('refresh');
				DREAM_PAGE.pageOffset += DREAM_PAGE.pageSize;
			} else {
				alert("No data");
			}
		},
		error : function() {

		}
	});
}
function uploadPic() {
	var filePath = $("#img").val();
	IMG_PATH = "";
	if (filePath == null || filePath == "") {
		$("#dreamDebug").append("filePath:" + filePath + "<br>");
		return null;
	}
	var rs = $.ajax({
		type : "POST",
		url : "/core/dream/uploadImg",
		data : new FormData($('#fileForm')[0]),
		contentType : false,
		processData : false,
		dataType : "json",
		beforeSend: function(){
            console.log("sending...");
        },
        success : function(result) {
			console.log("done");
			console.log(result);
			$("#dreamDebug").append("uploadPic result:" + result.resultStr + "<br>");
			IMG_PATH = result.resultStr;
		},
		error : function() {
			$("#dreamDebug").append("uploadPic fail<br>");
			console.log("fail");
		}
	});
	return rs;
}