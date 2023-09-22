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
<<<<<<< HEAD
=======

// 페이지 변수 가져오기
var paginationDiv = document.getElementById(".pagination");
var totalPages = parseInt(paginationDiv.getAttribute("data-total-pages"));
var currentPage = parseInt(paginationDiv.getAttribute("data-current-page"));

// 페이지 번호를 동적으로 생성하는 함수
function generatePagination() {
    paginationDiv.innerHTML = ""; // 기존 내용 초기화

    // 최대 7개의 페이지 번호를 생성
    for (var i = Math.max(0, currentPage - 3); i <= Math.min(totalPages - 1, currentPage + 3); i++) {
        var pageLink = document.createElement("a");
        pageLink.href = "/rank?page=" + i;

        if (i === currentPage) {
            pageLink.classList.add("active");
            pageLink.innerText = (i + 1) + "위";
        } else {
            pageLink.innerText = (i + 1);
        }

        paginationDiv.appendChild(pageLink);
    }

    // "..." 추가
    if (currentPage > 3) {
        var ellipsis1 = document.createElement("span");
        ellipsis1.innerText = "...";
        paginationDiv.insertBefore(ellipsis1, paginationDiv.children[1]);
    }

    if (currentPage < totalPages - 4) {
        var ellipsis2 = document.createElement("span");
        ellipsis2.innerText = "...";
        paginationDiv.insertBefore(ellipsis2, paginationDiv.children[paginationDiv.children.length - 1]);
    }
}

// 페이지 번호 생성 함수 호출
generatePagination();

>>>>>>> f01acf57149399c19b5c8a71beeea92f53bbe331
