document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        const userId = document.getElementById('userId').value.trim();
        const password = document.getElementById('password').value.trim();
        let valid = true;

        if (userId === '') {
            alert('아이디를 입력하세요.');
            valid = false;
        }

        if (password === '') {
            alert('비밀번호를 입력하세요.');
            valid = false;
        }

        // 폼이 유효하지 않으면 제출을 막습니다.
        if (!valid) {
            event.preventDefault();
        }
    });
});
