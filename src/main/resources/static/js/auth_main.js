document.addEventListener('DOMContentLoaded', function () {
    // "문제 풀기 시작하기" 버튼 클릭 시 문제 페이지로 이동
    const startButton = document.querySelector('.btn');
    startButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작 막기
        window.location.href = '/solutions/list'; // 문제 페이지로 이동
    });

    // 간단한 애니메이션 예시: Hero 섹션의 제목에 페이드 인 애니메이션 적용
    const heroTitle = document.querySelector('.hero h1');
    heroTitle.style.opacity = 0;
    heroTitle.style.transition = 'opacity 2s';
    window.addEventListener('load', function () {
        heroTitle.style.opacity = 1; // 페이지 로드 후 제목을 점차 표시
    });

    // 각 feature 섹션에 마우스 오버 시 배경색 변경
    const features = document.querySelectorAll('.feature');
    features.forEach(function (feature) {
        feature.addEventListener('mouseover', function () {
            feature.style.backgroundColor = '#f0f8ff'; // 배경색 변경
        });
        feature.addEventListener('mouseout', function () {
            feature.style.backgroundColor = ''; // 원래 배경색으로 복구
        });
    });

    // 홈 버튼 클릭 시 홈 페이지로 이동
    const homeButton = document.querySelector('a[th\\:href="@{/}"]');
    homeButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작 방지
        window.location.href = '/'; // 홈 페이지로 이동
    });

    // 문제 버튼 클릭 시 문제 페이지로 이동
    const solutionButton = document.querySelector('a[th\\:href="@{/solution/list}"]');
    solutionButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작 방지
        window.location.href = '/users/solutions/list'; // 문제 페이지로 이동
    });

    // 랭킹 버튼 클릭 시 랭킹 페이지로 이동
    const rankingButton = document.querySelector('a[th\\:href="@{/users/ranking}"]');
    rankingButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작 방지
        window.location.href = '/users/ranking'; // 랭킹 페이지로 이동
    });

    // 마이페이지 버튼 클릭 시 마이페이지로 이동
    const myPageButton = document.querySelector('a[th\\:href="@{/users/info}"]');
    myPageButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작 방지
        const token = localStorage.getItem('code-for-code-auth');
        window.location.href = '/users/info'; // 마이페이지로 이동
    });

    document.addEventListener('DOMContentLoaded', function () {
        const logoutButton = document.querySelector('a[href="/api/users/logout"]');
        if (logoutButton) {
            logoutButton.addEventListener('click', async function (event) {
                event.preventDefault();
                const token = localStorage.getItem('code-for-code-auth');
                if (token) {
                    try {
                        const response = await fetch('/api/users/logout', {
                            method: 'DELETE',
                            headers: {
                                'code-for-code-auth': `${token}`
                            }
                        });

                        if (response.ok) {
                            localStorage.removeItem('code-for-code-auth');
                            alert('성공적으로 로그아웃되었습니다.');
                            window.location.href = '/';
                        } else {
                            alert('로그아웃에 실패했습니다. 다시 시도해주세요.');
                        }
                    } catch (error) {
                        console.error('로그아웃 에러:', error);
                        alert('서버 오류가 발생했습니다.');
                    }
                } else {
                    alert('로그아웃할 수 있는 토큰이 없습니다.');
                }
            });
        } else {
            console.error('로그아웃 버튼을 찾을 수 없습니다.');
        }
    });
});
