/**
 * 
 */

$(document).ready(function(){	
		
	$(".btn-size").click(function() {	
		var val = $(this).text();		
		$("#sizes").tagsinput('add', val);
	});
	$(".btn-brand").click(function() {
		var val = $(this).text();
		$("#brands").tagsinput('add', val);
	});
	$(".btn-category").click(function() {
		var val = $(this).text();
		$("#categories").tagsinput('add', val);
	});
	
});