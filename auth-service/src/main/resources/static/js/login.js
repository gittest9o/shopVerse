document.addEventListener('DOMContentLoaded', function() {
    // Элементы DOM
    const loginForm = document.getElementById('login-form');
    const errorBlock = document.getElementById('error-message');
    const errorText = document.getElementById('error-text');
    const submitBtn = document.getElementById('submit-btn');
    const passwordToggle = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');

    // Переключение видимости пароля
    if (passwordToggle && passwordInput) {
        passwordToggle.addEventListener('click', function() {
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                this.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                this.classList.replace('fa-eye-slash', 'fa-eye');
            }
        });
    }

    // Обработка отправки формы
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Скрываем предыдущие ошибки
            hideError();

            // Получаем данные формы
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();
            const rememberMe = document.getElementById('remember').checked;

            // Валидация
            if (!email || !password) {
                showError('Пожалуйста, заполните все поля!');
                return;
            }

            // Проверка формата email
            if (!validateEmail(email)) {
                showError('Пожалуйста, введите корректный email');
                return;
            }

            // Блокировка кнопки во время запроса
            disableSubmitButton(true);

            try {
                // Отправка запроса на сервер
                const response = await fetch('/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password
                    })
                });

                if (response.ok) {
                    // Успешный логин - редирект на главную
                    window.location.href = '/';
                } else {
                    // Обработка ошибок сервера
                    const errorData = await response.text();
                    showError(errorData || 'Неизвестная ошибка сервера');
                }
            } catch (error) {
                // Обработка сетевых ошибок
                showError('Ошибка сети. Проверьте подключение к интернету');
            } finally {
                // Разблокировка кнопки
                disableSubmitButton(false);
            }
        });
    }

    // Функции для работы с UI
    function showError(message) {
        errorText.textContent = message;
        errorBlock.style.display = 'flex';
    }

    function hideError() {
        errorBlock.style.display = 'none';
    }

    function disableSubmitButton(disabled) {
        submitBtn.disabled = disabled;
        if (disabled) {
            submitBtn.innerHTML = '<i class="fas fa-spinner spinner"></i> Вход...';
        } else {
            submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Войти';
        }
    }

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});