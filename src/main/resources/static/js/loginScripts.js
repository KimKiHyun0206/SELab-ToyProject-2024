document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function (event) {
        const userId = document.getElementById('userId').value.trim();
        const password = document.getElementById('password').value.trim();
        let valid = true;

        console.log('아이디 확인')
        if (userId === '') {
            alert('아이디를 입력하세요.');
            valid = false;
        }

        console.log('비밀번호 확인')
        if (password === '') {
            alert('비밀번호를 입력하세요.');
            valid = false;
        }

        // 폼이 유효하지 않으면 제출을 막습니다.
        if (!valid) {
            event.preventDefault();
        } else {
            const data = {
                userId: userId,
                password: password,
            };

            console.info('fetch 전')
            fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            }).then(response => {
                console.log('then 진입')
                if (response.ok) {
                    console.log('로그인 성공했습니다');
                    alert('로그인이 완료되었습니다.');

                    const token = response.headers.get('Authorization');
                    console.log(token)
                    localStorage.setItem('Authorization', token);

                    window.location.replace("/");
                } else if (!response.ok) {
                    alert('로그인 정보가 일치하지 않습니다.');
                    window.location.replace("/users/login");
                }
            }).catch(error => {
                alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
                window.location.replace("/users/login");
            });

        }
        event.preventDefault();
    });
});