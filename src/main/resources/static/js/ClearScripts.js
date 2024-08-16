document.addEventListener('DOMContentLoaded', function() {
    const logoutLink = document.getElementById('logout');

    if (logoutLink) {
        logoutLink.addEventListener('click', function(event) {
            if (!confirm('로그아웃하시겠습니까?')) {
                event.preventDefault();
            }
        });
    }
});
