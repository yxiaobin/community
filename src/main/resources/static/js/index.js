$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
	$("#hintModal").modal("show");


	//发送ajax请求
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function (e,xhr,options){
	// 	xhr.setRequestHeader(header,token);
	// });


	//获取标题和内容
	var title   = $("#recipient-name").val();
	var content = $("#message-text").val();

	//post
	$.post(
		CONTEXT_PATH+"/discuss/add",
		{
			"title":title,
			"content":content
		},
		function (data){
			// string ->json
			data = $.parseJSON(data);
			//注入返回信息
			$("hintModal").text(data.msg);
			//页面交互且自动关闭
			setTimeout(function(){
				$("#hintModal").modal("hide");
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);


		}
	);




}