/**
 * 
 */

$('.input_btn').on("click", function(){
	var url = $(this).parents("form").attr("action");
	var data = $(this).parents("form").serialize();
	$.post(url, data, function(data){
		
	});
});