document.addEventListener('DOMContentLoaded', function () {
        const loginForm = document.getElementById('loginForm');

        if (loginForm) {
            loginForm.addEventListener('submit', function (event) {
                    const userId = document.getElementById('userId').value.trim();
                    const password = document.getElementById('password').value.trim();
                    let valid = true;

                    const tokenValue = localStorage.getItem('Authorization');
                    console.log(tokenValue);
                    if (tokenValue) {
                        fetch('/api/users/login/token', {
                            method: 'POST',
                            headers: {
                                'Authorization': tokenValue
                            },
                            body: null
                        }).then(data => {
                            console.log(data.status);
                            if (data.status.valueOf() === 200) {
                                console.log('토큰 로그인 성공했습니다');
                                window.location.replace("/");
                            }
                        })
                        event.preventDefault();
                    } else {
                        console.log('아이디 확인');
                        if (userId === '') {
                            alert('아이디를 입력하세요.');
                            valid = false;
                        }

                        console.log('비밀번호 확인');
                        if (password === '') {
                            alert('비밀번호를 입력하세요.');
                            valid = false;
                        }

                        if (!valid) {
                            event.preventDefault();
                        } else {
                            const data = {
                                userId: userId,
                                password: password,
                            };

                            console.info('fetch 전');
                            fetch('/api/users/login', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(data)
                            }).then(data => {
                                console.log('로그인 성공했습니다');
                                alert('로그인이 완료되었습니다.');

                                const token = data.headers.get("Authorization");  // JSON 응답에서 토큰 추출
                                console.log(token);

                                if (token) {
                                    // 토큰을 로컬 스토리지에 저장
                                    localStorage.setItem('Authorization', token);

                                    // 메인 페이지로 이동
                                    window.location.replace("/");
                                } else {
                                    alert('토큰을 받아올 수 없습니다.');
                                }
                            }).catch(error => {
                                alert(error.message || '서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
                                window.location.replace("/users/login");
                            });
                        }
                    }
                    event.preventDefault();
                }
            )
            ;
        } else {
            checkLoginStatus();
        }
    }
)
;


function checkLoginStatus() {
    const token = localStorage.getItem('Authorization');
    if (!token) {
        // 토큰이 없으면 로그인 페이지로 리다이렉트
        alert('로그인이 필요합니다.');
        window.location.replace("/users/login");
    } else {
        console.log('로그인 상태 확인: 토큰이 있습니다.');
    }
}


function fetchWithToken(url, options = {}) {
    // 로컬 스토리지에서 토큰 가져오느거
    const token = localStorage.getItem('Authorization');

    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.replace("/users/login");
        return;
    }


    const headers = {
        ...options.headers,
        'Authorization': `Bearer ${token}`
    };

    return fetch(url, {
        ...options,
        headers: headers
    }).then(response => {
        if (response.status === 401) {
            alert('인증 오류가 발생했습니다. 다시 로그인하세요.');
            window.location.replace("/users/login");
        }
        return response;
    }).catch(error => {
        console.error('에러 발생:', error);
        alert('서버 오류가 발생했습니다.');
    });
}


