var ContractItem = {
	id : null
}
function onSelectContract(id) {
	ContractItem.id = id;
}
function loadAllContractList() {
	$("#listContractUL").html("");
	$(document).undelegate("#contractListPage", "pageinit", loadAllContractList);
	
	var rs = $.ajax({
		type : "POST",
		url : "/contract/contract/list" + appendQueryURLForRequest(),
		// data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#listContractUL").append(result);
			$('#listContractUL').listview('refresh');
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
		"title" : title,
		"content" : content,
		"creator" : creator,
		"type" : "CONTRACT",
		"partyList" : party,
		"effectiveDate" : effectiveDate,
		"expiredDate" : expiredDate
	}
	var url = "/contract/contract/create" + appendQueryURLForRequest();
	var rs = $.ajax({
		type : "POST",
		url : url,
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(result) {
			alert("Success");
		},
		error : function() {

		}
	});
}
function doSign(contractId, actionType) {
	var param = {
		"contractId" : contractId,
		"username" : PROFILE.username,
		"actionType" : actionType
	}
	var url = "/contract/contract/sign" + appendQueryURLForRequest();
	var rs = $.ajax({
		type : "POST",
		url : url,
		data : JSON.stringify(param),
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {

		},
		error : function() {

		}
	});
}
function loadContractDetails(action) {
	var url = "/contract/contract/" + action + "/" + ContractItem.id
			+ appendQueryURLForRequest();
	var rs = $.ajax({
		type : "GET",
		url : url,
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(result) {
			$("#contractDetailsContent").html(result);
			$("input[type=Button]").button();
		},
		error : function() {

		}
	});
}
