   function psCheck() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        var checkMsg = document.getElementById("checkMsg");
        
        if (password === confirmPassword) {
            checkMsg.style.color = "green";
            checkMsg.innerHTML = "비밀번호가 일치합니다.";
        } else {
            checkMsg.style.color = "red";
            checkMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
        }
    }
    
      function validateForm() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        
        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다!!");
            return false;
    }
    
    }
       $(document).ready(function() {
    function checkAvailability(url, inputElementId, messageElementId, buttonElementId) {
        $(inputElementId).on("input", function() {
            var inputValue = $(this).val();
            
            $.ajax({
                type: "GET",
                url: url + inputValue,
                success: function(response) {
                    var messageElement = $(messageElementId);
                    var buttonElement = $(buttonElementId);
                    
                    if (response === true) {
                        messageElement.text("사용 가능");
                        buttonElement.prop("disabled", false);
                    } else {
                        messageElement.text("중복");
                        buttonElement.prop("disabled", true);
                    }
                },
                error: function() {
                    $(messageElementId).text("에러");
                }
            });
        });
    }
    
    checkAvailability("/checkUserIdAvailability?user_id=", "#user_id", "#duplicationMessage", "#signupButton");
    checkAvailability("/checkUseremailAvailability?email=", "#email", "#duplicationMessageemail", "#signupButton");
});