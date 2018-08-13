var changeEmailBtn = false;
var changePasswordBtn = false;

$(document).ready(function() {
	getUserDetails();
	
	$("#changeEmailRow").hide();
	$("#changeEmailBtnsRow").hide();
	
	$("#newPasswordRow").hide();
	$("#newPasswordAgainRow").hide();
	$("#changePasswordBtnsRow").hide();
});

function getUserDetails(){
	$.ajax({
		url:"getUserDetails",
		type: "POST",
		success: function(result){
			$("#fullname").val(result.fullname);
			$("#username").val(result.username);
			$("#email").val(result.email);
		}
	});
}

function enableEmailTextBox(){
	changeEmailBtn = !changeEmailBtn;
	$("#email").prop("disabled", !changeEmailBtn);
	if(changeEmailBtn){
		$("#changeEmailRow").show();
		$("#changeEmailBtnsRow").show();
		$("#changeEmailBtn").hide();
		$("#changePasswordBtn").prop("disabled", true);
	}
}

function cancelChangeEmail(){
	changeEmailBtn = !changeEmailBtn;
	$("#email").prop("disabled", !changeEmailBtn);
	$("#changeEmailRow").hide();
	$("#changeEmailBtnsRow").hide();
	$("#changeEmailBtn").show();
	$("#changePasswordBtn").prop("disabled", false);
}

function saveChangeEmail(){
	data = {
			newEmail: $("#email").val(),
			userPass: $("#passwordToChangeEmail").val()
	}
	$.ajax({
		url:"saveNewEmail",
		type: "POST",
		data: data,
		async: false,
		success: function(result){
			if(result){
				$("#passwordToChangeEmail").val("");
				cancelChangeEmail();
				alert("Your email has been changed!");
			}else{
				alert("Your password is wrong!");
			}
		}
	});
}

function enablePasswordTextbox(){
	changePasswordBtn = !changePasswordBtn;
	$("#password").prop("disabled", !changePasswordBtn);
	if(changePasswordBtn){
		$("#newPasswordRow").show();
		$("#newPasswordAgainRow").show();
		$("#changePasswordBtnsRow").show();
		$("#changePasswordBtn").hide();
		$("#changeEmailBtn").prop("disabled", true);
	}
}

function cancelChangePassword(){
	changePasswordBtn = !changePasswordBtn;
	$("#password").prop("disabled", !changePasswordBtn);
	$("#newPasswordRow").hide();
	$("#newPasswordAgainRow").hide();
	$("#changePasswordBtnsRow").hide();
	$("#changePasswordBtn").show();
	$("#changeEmailBtn").prop("disabled", false);
}

function saveChangePassword(){
	data = {
			currentPass: $("#password").val(),
			newPass: $("#newpassword").val(),
			newPassConf: $("#newpasswordagain").val()
	}
	$.ajax({
		url:"saveNewPassword",
		type: "POST",
		data: data,
		async: false,
		success: function(result){
			if(result){
				$("#password").val("");
				$("#newpassword").val("");
				$("#newpasswordagain").val("");
				cancelChangePassword();
				alert("Your password has been changed!");
			}else{
				alert("Passwords must be matched.")
			}
		}
	});
}

function changeFullName(){
	data = {
			newfullname: $("#fullname").val()
	}
	$.ajax({
		url:"changeFullname",
		type: "POST",
		data: data,
		async: false,
		success: function(result){
			if(result){
				//alert("Your full name has been changed.");
			}else{
				alert("There is an error while changing your full name. Please try again!");
			}
		}
	});
}

//function changeUsername(){
//	data = {
//			newusername: $("#username").val()
//	}
//	$.ajax({
//		url:"changeUsername",
//		type: "POST",
//		data: data,
//		async: false,
//		success: function(result){
//			if(result){
//				//alert("Your username has been changed.");
//			}else{
//				alert("Username already exist. Please try again!");
//			}
//		}
//	});
//}
