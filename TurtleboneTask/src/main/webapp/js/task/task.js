var TaskItem = {
	id:null
}
function onSelectTask(id) {
	TaskItem.id = id;
}
function loadAllTasks() {
	var rs = $.ajax({
		type:"GET",
		url:"/task/pages/loadMyTask?tokenId=DFS",
		//data : JSON.stringify(param),
		contentType:"application/json; charset=utf-8",
		dataType : "text",
		success : function (result) {
			$("#listTaskUL").append(result);
			$('#listTaskUL').listview('refresh');
		},
		error : function() {
			
		}
	});
}
function onCreateContract() {
	var title = $("#contractTitle").val();
	var content = $("#contractContent").val();
	var creator = PROFILE.username;
	var party = $("#party").val()
	var effectiveDate = $("#contractEffDate").val();
	var expiredDate = $("#contractExprDate").val();
	if (creator == null || creator == "") {
		alert("Login first");
		return;
	}
	var param = {
		"title":title,
		"content":content,
		"creator":creator,
		"type":"CONTRACT",
		"partyList":party,
		"effectiveDate":effectiveDate,
		"expiredDate":expiredDate
	}
	var url = "/contract/contract/create?tokenId=" + PROFILE.tokenId;
	var rs = $.ajax({
		type:"POST",
		url:url,
		data : JSON.stringify(param),
		contentType:"application/json; charset=utf-8",
		dataType : "json",
		success : function (result) {
			alert("Success");
		},
		error : function() {
			
		}
	});
}
function doSign(contractId,actionType) {
	var param = {
		"contractId":contractId,
		"username":PROFILE.username,
		"actionType":actionType
	}
	var url = "/contract/contract/sign?tokenId=" + PROFILE.tokenId;
	var rs = $.ajax({
		type:"POST",
		url:url,
		data : JSON.stringify(param),
		contentType:"application/json; charset=utf-8",
		dataType : "text",
		success : function (result) {
			
		},
		error : function() {
			
		}
	});
}
function loadDetails(action) {
		var url = "/contract/contract/" + action + "/" + ContractItem.id + "?tokenId=" + PROFILE.tokenId;
		var rs = $.ajax({
			type:"GET",
			url:url,
			contentType:"application/json; charset=utf-8",
			dataType : "text",
			success : function (result) {
				$("#contractDetailsContent").html(result);
				$("input[type=Button]").button();
			},
			error : function() {
				
			}
		});
}
