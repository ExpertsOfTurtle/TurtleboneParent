PROFILE = {
	tokenId : "DFS",
	username : "DFS"
}
var TaskItem = {
	id : null
}
function onInitTaskPercentage() {
	$("#taskPercentage").slider({
		range : 'min',
		min : 0,
		max : 100,
		value : 0,
		step : 5,
		slide : function(event, ui) {
			$("#labelTaskPercentage").html($(this).val());
		},
		stop : function(event, ui) {
		}
	});

}
function onSelectTask(id) {
	TaskItem.id = id;
}
function loadAllTasks() {
	var rs = $.ajax({
		type : "GET",
		url : "/task/pages/loadMyTask?tokenId=DFS",
		// data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#listTaskUL").append(result);
			$('#listTaskUL').listview('refresh');
		},
		error : function() {

		}
	});
}
function onCreateTask() {
	var title = $("#taskTitle").val();
	var content = $("#taskContent").val();
	var creator = PROFILE.username;
	var party = $("#party").val()
	var deadline = $("#taskDeadline").val();
	var difficulty = $("#taskDifficulty").val();
	var percentage = $("#taskPercentage").val();
	if (creator == null || creator == "") {
		alert("Login first");
		return;
	}
	var param = {
		"title" : title,
		"content" : content,
		"creator" : creator,
		"owner" : party,
		"deadline" : deadline,
		"percentage" : percentage,
		"difficulty" : difficulty
	}
	var url = "/task/task/create?tokenId=" + PROFILE.tokenId;
	var rs = $.ajax({
		type : "PUT",
		url : url,
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(result) {
			alert("Success， taskId=" + result.id);
		},
		error : function() {

		}
	});
}
function onModifyTask() {
	var taskId = $("#taskId").val();
	var title = $("#taskTitle").val();
	var content = $("#taskContent").val();
	var creator = PROFILE.username;
	var party = $("#party").val()
	var deadline = $("#taskDeadline").val();
	var difficulty = $("#taskDifficulty").val();
	var percentage = $("#taskPercentage").val();
	if (creator == null || creator == "") {
		alert("Login first");
		return;
	}
	var param = {
		"id" : taskId,
		"title" : title,
		"content" : content,
		"creator" : creator,
		"owner" : party,
		"deadline" : deadline,
		"percentage" : percentage,
		"difficulty" : difficulty
	}
	var url = "/task/task/modify?tokenId=" + PROFILE.tokenId;
	var rs = $.ajax({
		type : "POST",
		url : url,
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(result) {
			alert("Success， taskId=" + result.id);
			window.history.back(-1);
		},
		error : function() {

		}
	});
}
function doUpdateProgress(actionType, status) {
	var param = {
		"id" : $("#taskId").val(),
		"username" : PROFILE.username,
		"actionType" : actionType,
		"status" : status,
		"percentage" : $("#taskPercentage").val()
	}
	var url = "/task/task/updateProgress?tokenId=" + PROFILE.tokenId;
	var rs = $.ajax({
		type : "POST",
		url : url,
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			alert("Success， taskId=" + result.id);
			window.history.back(-1);
		},
		error : function() {

		}
	});
}
function loadTaskDetails(action) {
	var url = "/task/pages/" + action + "/" + TaskItem.id + "?tokenId="
			+ PROFILE.tokenId;
	var rs = $.ajax({
		type : "GET",
		url : url,
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#taskDetailsContent").html(result);
			$("input[type=Button]").button();
			onInitTaskPercentage();
		},
		error : function() {

		}
	});
}
