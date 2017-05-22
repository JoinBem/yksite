/**
 * 
 */

$('.input_btn').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	$.post(url, data, function(data){
		
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