var TaskItem = {
	id : null,
	action : null
}
var TASK_PAGE = {
	pageNumber : 0,
	pageSize : 10
}
function onInitInputPage() {
	//$("#taskDifficulty").slider();
	//$("#taskPercentage").slider();
	$("input[type=number]").slider();
}
function onLoadTaskListPage() {
	$(document).undelegate("#taskListPage", "pageinit", onInitInputPage);
	TASK_PAGE.pageNumber = 0;
	loadAllTasks();
}
function onSelectTask(id) {
	TaskItem.id = id;
}
function filterTask() {
	var filterDeadlineFrom = $("#filterDeadlineFrom").val();
	var filterDeadlineTo = $("#filterDeadlineTo").val();
	var filterTaskType = $("#filterTaskType").val();
	var filterStatus = $("#filterStatus").val();
	if (filterDeadlineFrom.trim() == "" || filterDeadlineTo.trim() == "") {
		filterDeadlineFrom = null;
		filterDeadlineTo = null;
	}
	var param = {
		"type" : filterTaskType.trim() == "" ? null : filterTaskType,
		"status" : filterStatus.trim() == "" ? null : filterStatus,
		"deadlineFrom" : filterDeadlineFrom,
		"deadlineTo" : filterDeadlineTo
	}
	var rs = $.ajax({
		type : "POST",
		url : "/task/pages/loadMyTask" + appendQueryURLForRequest(),
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#listTaskUL").html(result);
			$('#listTaskUL').listview('refresh');
		},
		error : function() {

		}
	});
}
function loadAllTasks() {
	var rs = $.ajax({
		type : "POST",
		url : "/task/pages/loadMyTask" + appendQueryURLForRequest(),
		data : JSON.stringify({}),
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
	var progress = $("#taskProgress").val();
	var total = $("#taskTotal").val();
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
		"progress" : progress,
		"difficulty" : difficulty,
		"total" : total
	}
	var url = "/task/task/create" + appendQueryURLForRequest();
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
	var progress = $("#taskProgress").val();
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
		"progress" : progress,
		"difficulty" : difficulty
	}
	var url = "/task/task/modify" + appendQueryURLForRequest();
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
		"progress" : $("#taskProgress").val()
	}
	var url = "/task/task/updateProgress" + appendQueryURLForRequest();
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
	if (action == null || action == undefined) {
		action = TaskItem.action;
	}
	var url = "/task/pages/" + action + "/" + TaskItem.id + appendQueryURLForRequest();
	var rs = $.ajax({
		type : "GET",
		url : url,
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#taskDetailsContent").html(result);
			$("input[type=Button]").button();
			onInitInputPage();
		},
		error : function() {

		}
	});
}
