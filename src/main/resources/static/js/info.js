document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('editForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        const userId = document.getElementById('userId').value;
        if (userId) {
            // userId 변수를 문자열이 아닌 실제 값으로 사용
            window.location.href = `/edit/${userId}`;
        } else {
            alert('UserID가 없습니다.');
        }
    });
});


