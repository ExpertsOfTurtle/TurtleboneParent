
function loadAllDecideList() {

	var rs = $.ajax({
		type:"GET",
		url:"/core/choose/selectpage",
		//data : JSON.stringify(param),
		contentType:"application/json; charset=utf-8",
		dataType : "text",
		success : function (result) {
			$("#listDecideUL").append(result);
			$('#listDecideUL').listview('refresh');
		},
		error : function() {
			
		}
	});
	
	
}