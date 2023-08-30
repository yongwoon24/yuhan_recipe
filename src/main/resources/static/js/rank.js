//카테고리 선택    
document.addEventListener('DOMContentLoaded', function() {
    var categoryNameDropdown = document.getElementById("categoryName");
    var periodDropdown = document.getElementById("period");

    // 이전 선택 값을 세션 스토리지에서 가져오기
    var previousCategory = sessionStorage.getItem("previousCategory");
    var previousPeriod = sessionStorage.getItem("previousPeriod");
    if (previousCategory) {
        categoryNameDropdown.value = previousCategory;
    }
    if (previousPeriod){
        periodDropdown.value = previousPeriod;
    }

    categoryNameDropdown.addEventListener("change", function() {
        var selectedCategoryValue = categoryNameDropdown.value;

        // 폼을 서버로 제출합니다.
        var form = document.querySelector("#myForm"); // 폼 엘리먼트 선택

        // 폼을 서버로 제출하는 submit 버튼 찾기
        var submitButton = form.querySelector("#submitBtn"); // 버튼 선택
        
        if (submitButton) {
            submitButton.click();
        } else {
            console.log("Submit button not found.");
        }

        // 선택한 값을 세션 스토리지에 저장
        sessionStorage.setItem("previousCategory", selectedCategoryValue);
    });
    periodDropdown.addEventListener("change", function() {
        var selectedPeriodValue = periodDropdown.value;

        // 폼을 서버로 제출합니다.
        var form = document.querySelector("#myForm"); // 폼 엘리먼트 선택

        // 폼을 서버로 제출하는 submit 버튼 찾기
        var submitButton = form.querySelector("#submitBtn"); // 버튼 선택
        
        if (submitButton) {
            submitButton.click();
        } else {
            console.log("Submit button not found.");
        }

        // 선택한 값을 세션 스토리지에 저장
        sessionStorage.setItem("previousPeriod", selectedPeriodValue);
    });
});
