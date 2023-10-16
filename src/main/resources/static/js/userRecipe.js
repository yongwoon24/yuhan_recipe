$(document).ready(function() {
    $("#like-link").click(function(e) {
        e.preventDefault();
        increaseLikeCount();
    });
});

function increaseLikeCount() {
    var currentCount = parseInt($("#like-count").text(), 10);
    var newCount = currentCount + 1;
    $("#like-count").text(newCount);
    
    // 서버로 좋아요 수 업데이트 요청 전송
    $.post("/update-like", { newCount: newCount }, function(data) {
        console.log("서버 응답:", data);
    });
}

