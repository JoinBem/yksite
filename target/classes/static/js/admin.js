/**
 * 
 */

$(".site_delete").on("click",function(event){
	event.preventDefault();
	$.post($(this).attr("href") ,
		function(data){
			window.location.href = "/u/sites";
		});
}); 

$('.site_input').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	$.post(url, data, function(data){
		if(data.domain == "domain.exists"){
			alert("域名已存在");
		}
		alert("保存成功");
		window.location.href = "/u/sites";
	});
});

$('.site_update').on("click", function(){
	var data = $(this).parents("form").serialize();
	var url = $(this).parents("form").attr("action");
	$.post(url, data, function(data){
		window.location.href = "/u/sites";
	});
});

$('.temp_input').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	
	  var fd = new FormData();
	  for (var i = 0; i < files.length; i++) {
	    fd.append("file", files[i]);
	  }
	  fd.append("tempname", $('*[name=tempname]').val());
	  fd.append("temptitle", $('*[name=temptitle]').val());
	  fd.append("tempcontent", $('*[name=tempcontent]').val());
	  fd.append("tempprice", $('*[name=tempprice]').val());
	  $.ajax({
	    url: url,
	    method: "POST",
	    data: fd,
	    contentType: false,
	    processData: false,
	    cache: false,
	    success: function(data){
	      window.location.href = "/u/temps";
	    }
	  });
});

$('.temp_update').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	$.post(url, data, function(data){
		window.location.href = "/u/temps";
	});
});

$(".temp_delete").on("click",function(event){
	event.preventDefault();
	$.post($(this).attr("href") ,
		function(data){
			window.location.href = "/u/temps";
		});
}); 

var files = [];
$(document).ready(function(){
  $("*[name='file']").change(function(e){
	  console.log(location.href);
    files = this.files;
    for (var i = 0; i < files.length; i++) {
        //console.log(files[i]);
      }
  });
});
