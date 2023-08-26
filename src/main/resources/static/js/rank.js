var img_slide = document.querySelector('.img_slide');
var slideItems = document.querySelectorAll('.img_slide li');
var prevBtn = document.querySelector('.prev');
var nextBtn = document.querySelector('.next');
var currentIdx = 0;
var slideWidth = 400;
var slideMargin = 30;


//카테고리 선택    
    document.addEventListener('DOMContentLoaded', function() {
    var categoryNameDropdown = document.getElementById("categoryName");
    var initialCategory = categoryNameDropdown.value;

    // 이전 선택 값을 세션 스토리지에서 가져오기
    var previousCategory = sessionStorage.getItem("previousCategory");
    if (previousCategory) {
        categoryNameDropdown.value = previousCategory;
    }

    categoryNameDropdown.addEventListener("change", function() {
        var selectedValue = categoryNameDropdown.value;
        console.log("Selected category: " + selectedValue);

        // 폼을 서버로 제출합니다.
        var form = document.querySelector("#myForm"); // 폼 엘리먼트 선택
        console.log("Form element:", form);

        // 폼을 서버로 제출하는 submit 버튼 찾기
        var submitButton = form.querySelector("#submitBtn"); // 버튼 선택
        console.log(submitButton);
        
        if (submitButton) {
            console.log("Submit button found:", submitButton);
            submitButton.click();
        } else {
            console.log("Submit button not found.");
        }

        // 선택한 값을 세션 스토리지에 저장
        sessionStorage.setItem("previousCategory", selectedValue);
    });
});

    