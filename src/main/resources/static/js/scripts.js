/**
 * 
 */

function checkPasswordMatch() {
	var password = $("#txtNewPassword").val();
	var confirmPassword = $("#txtConfirmPassword").val();
	if (password == "" && confirmPassword == "") {
		$("#checkPasswordMatch").html("");
		$("#updateUserInfoButton").attr("disabled", false);
	} else {
		if (password != confirmPassword) {

			$("#checkPasswordMatch").html("Passwords do not match");
			$("#updateUserInfoButton").attr("disabled", true);
		} else {
			$("#checkPasswordMatch").html("");
			$("#updateUserInfoButton").attr("disabled", false);
		}
	}		
}

$(document).ready(function(){
	$(".cartItemQty").on('change', function(){
		var id=this.id;
		
		$('#update-item-'+id).css('display', 'inline-block');
	});
	$("#txtConfirmPassword").keyup(checkPasswordMatch);
	$("#txtNewPassword").keyup(checkPasswordMatch);
	
});