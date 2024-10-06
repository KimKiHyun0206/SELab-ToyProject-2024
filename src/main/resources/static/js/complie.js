async function compileCode() {
    const language = document.getElementById("language").value;
    const code = document.getElementById("code").value;

    const token = localStorage.getItem('code-for-code-auth');
    console.log('저장된 토큰:', token);

    if (token) {
        try {
            const response = await fetch('/api/compile', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    'code-for-code-auth': `${token}`
                },
                body: JSON.stringify({language, code})
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `Server error: ${response.statusText}`);
            }

            const data = await response.json();

            outputElement.textContent = data.data || 'No output';
        } catch (error) {
            outputElement.textContent = `Error: ${error.message}`;
        }
    }
}
