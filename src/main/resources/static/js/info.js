document.addEventListener('DOMContentLoaded', function() {
    // "유저 정보 수정하기" 버튼을 클릭했을 때의 이벤트 처리
    const editButton = document.querySelector('button[th\\:action]');
    editButton.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작 방지
        const userId = event.target.getAttribute('th\\:action').split('/').pop();
        window.location.href = `/edit/${userId}`; // 수정 페이지로 이동
    });
});
