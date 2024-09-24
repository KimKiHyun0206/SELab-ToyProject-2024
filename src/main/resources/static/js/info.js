document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('editForm');
    form.addEventListener('submit', async function(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const userId = document.getElementById('userId').value;
        const token = localStorage.getItem('code-for-code-auth'); // 토큰 가져오기

        if (userId) {
            try {
                const response = await fetch(`/edit-info/${userId}`, {
                    method: 'GET',
                    headers: {
                        'code-for-code-auth': `${token}`
                    }
                });

                if (response.ok) {
                    // 성공적으로 요청이 완료된 경우
                    window.location.href = `/edit-info/'${userId}`; // 해당 페이지로 이동
                } else {
                    alert('페이지를 로드하는데 실패했습니다.');
                }
            } catch (error) {
                console.error('에러 발생:', error);
                alert('서버 오류가 발생했습니다.');
            }
        } else {
            alert('UserID가 없습니다.');
        }
    });
});



