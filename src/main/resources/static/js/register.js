document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');

    if (registerForm) {
        registerForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 기본 폼 제출 방지

            // 폼 데이터 추출
            const userId = document.getElementById('userId').value.trim();
            const password = document.getElementById('password').value.trim();
            const repeatPassword = document.getElementById('repeatPassword').value.trim();
            const email = document.getElementById('email').value.trim();
            let valid = true;

            // 유효성 검사
            if (userId === '') {
                alert('아이디를 입력하세요.');
                valid = false;
            }

            if (password === '') {
                alert('비밀번호를 입력하세요.');
                valid = false;
            }

            if (repeatPassword === '') {
                alert('비밀번호 확인을 입력하세요.');
                valid = false;
            }

            if (password !== repeatPassword) {
                alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
                valid = false;
            }

            if (email === '') {
                alert('이메일을 입력하세요.');
                valid = false;
            }

            // 유효성 검사 통과 시 서버에 요청
            if (valid) {
                const formData = new FormData(registerForm);

                fetch(registerForm.action, {
                    method: 'POST',
                    body: formData
                })
                    .then(response => {
                        if (response.ok) {
                            // 성공적으로 회원가입 완료된 경우
                            window.location.href = '/user/login'; // 로그인 페이지로 리디렉션
                        } else {
                            // 오류 처리 (예: 회원가입 실패 메시지 표시)
                            return response.text().then(errorText => {
                                alert(`회원가입 실패: ${errorText}`);
                            });
                        }
                    })
                    .catch(error => {
                        console.error('서버 오류:', error);
                    });
            }
        });
    }
});
