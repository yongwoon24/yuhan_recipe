var img_slide = document.querySelector('.img_slide');
var slideItems = document.querySelectorAll('.img_slide li');
var prevBtn = document.querySelector('.prev');
var nextBtn = document.querySelector('.next');
var currentIdx = 0;
var slideWidth = 400;
var slideMargin = 30;

// 슬라이드 아이템의 개수
var slideCount = slideItems.length/2;

// 슬라이드 이동 함수
function moveSlide(num) {
    img_slide.style.left = -num * (slideWidth + slideMargin) + 'px';
    currentIdx = num;
}

// 버튼 클릭 이벤트 핸들러
nextBtn.addEventListener('click', function() {
    var slidesToShow = Math.floor(window.innerWidth / (slideWidth + slideMargin));
    var maxIdx = slideCount - slidesToShow;
    if (currentIdx < maxIdx) {
        moveSlide(currentIdx + 1);
    } else {
        moveSlide(0);
    }
});

prevBtn.addEventListener('click', function() {
    var slidesToShow = Math.floor(window.innerWidth / (slideWidth + slideMargin));
    var maxIdx = slideCount - slidesToShow;
    if (currentIdx > 0) {
        moveSlide(currentIdx - 1);
    } else {
        moveSlide(maxIdx);
    }
});

// 슬라이드 전체 너비 조정 함수
function adjustSlideWidth() {
    var totalSlideWidth = (slideWidth + slideMargin) * slideCount - slideMargin;
    img_slide.style.width = totalSlideWidth + 30 +'px';
}

// 초기화 함수
function initializeSlide() {
    // 현재 화면의 너비
    var screenWidth = window.innerWidth;

    // 넘길 수 있는 슬라이드의 개수를 계산
    var slidesToShow = Math.floor(screenWidth / (slideWidth + slideMargin));

    // maxIdx 초기화
    var maxIdx = slideCount - slidesToShow;

    // 슬라이드 전체 너비 조정
    adjustSlideWidth();
}

// 초기화 함수 호출
initializeSlide();

// 브라우저 창 크기가 변경될 때마다 초기화 함수와 슬라이드 조정 함수를 호출
window.addEventListener('resize', function() {
    initializeSlide();
    adjustSlideWidth();
});