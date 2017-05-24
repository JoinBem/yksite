/**
 * 
 */

// 删除按钮
$(".site_delete").on("click",function(event){
	event.preventDefault();
	$.post($(this).attr("href") ,
		function(data){
			window.location.href = "/admin";
		});
}); 

//发布按钮
$('.site_input').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	$.post(url, data, function(data){
		if(data.domain == "domain.exists"){
			alert("域名已存在");
		}alert("保存成功");
	});
});

//修改按钮
$('.site_update').on("click", function(){
	var data = $(this).parents("form").serialize();
	var url = $(this).parents("form").attr("action");
	$.post(url, data, function(data){
		window.location.href = "/admin";
	});
});

var files = [];
$(document).ready(function(){
  $("input").change(function(){
    files = this.files;
    for (var i = 0; i < files.length; i++) {
        console.log(files[i]);
      }
  });
});
$("#upload-btn").click(function(){
  var fd = new FormData();
  for (var i = 0; i < files.length; i++) {
    fd.append("file", files[i]);
  }
  $.ajax({
    url: "/upload",
    method: "POST",
    data: fd,
    contentType: false,
    processData: false,
    cache: false,
    success: function(data){
      console.log(data);
    }
  });
  
});