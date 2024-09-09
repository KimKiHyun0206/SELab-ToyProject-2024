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

        if (valid) {
            const data = {
                userId: userId,
                password: password,
            };

            fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (response.ok) {
                        const token = response.headers.get("Authorization");
                        if (token !== null) {
                            alert('로그인이 완료되었습니다.');
                            window.location.href = '';
                        } else {
                            alert('로그인에 성공했지만 토큰을 가져오지 못했습니다.');
                        }
                    } else if(!response.ok) {
                        response.json().then(data => {
                            alert('로그인 정보가 일치하지 않습니다.');
                            window.location.href = '/users/login';
                        });
                    }
                })
                .catch(error => {
                    console.error('서버 오류:', error);
                    alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
                });
        }
    });
});