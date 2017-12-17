var DREAM_PAGE = {
	pageNumber : 0,
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
//	var dreamer = $("#dreamer").val();
	var dreamer = PROFILE.username;
	var date = $("#dreamdate").val();
	var dreamType = $("#dreamType").val();
	if (dreamer == "" || content == "") {
		alert("Input content & select dreamer!");
		return;
	}
	var param = {
		"content" : content,
		"username" : dreamer,
		"datetime" : date,
		"dreampic" : IMG_PATH,
		"dreamType" : dreamType
	};
	var rs = $.ajax({
		type : "POST",
		url : "/dream/dream/create" + appendQueryURLForRequest(),
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			alert("Success");
			$("#content").val("");
			$("#dreamer").val("");
			$("#dreamdate").val("");
			$("#dreamType").val("1");
		},
		error : function() {

		}
	});
	return rs;
}
//初始化頁面時加載數據
function loadFirst() {
	$(document).undelegate("#listDreamPage", "pageinit", loadFirst);
	DREAM_PAGE.pageNumber = 0;
	loadNext();
}
function loadNext() {
	var pn = DREAM_PAGE.pageNumber;
	var param = {
		"type" : "",
		"pageSize" : DREAM_PAGE.pageSize,
		"pageNumber" : pn
	}
	console.log("DREAM_PAGE.pageNumber:" + pn);
	
	var rs = $.ajax({
		type : "POST",
		url : "/dream/dream/query" + appendQueryURLForRequest(),
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			wf = result;
			if (result != null && result.length > 0) {
				if (pn == 0) {
					$("#listDreamUl").html("");
				}
				$("#listDreamUl").append(result);
				$('#listDreamUl').listview('refresh');
				DREAM_PAGE.pageNumber = pn + 1;
			} else {
				alert("No more data");
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
		url : "/dream/dream/uploadImg" + appendQueryURLForRequest(),
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
/*
function loadDreamDetails(id) {
	var url = "/dream/dream/detail/" + id + appendQueryURLForRequest();
	var rs = $.ajax({
		type:"GET",
		url:url,
		contentType:"application/json; charset=utf-8",
		dataType : "text",
		success : function (result) {
			$("#dreamDetailsContent").html(result);
			$("input[type=Button]").button();
		},
		error : function() {
			
		}
	});
}*/