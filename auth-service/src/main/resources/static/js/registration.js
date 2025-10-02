document.addEventListener('DOMContentLoaded', function() {
    // Элементы DOM
    const registrationForm = document.getElementById('registration-form');
    const emailErrorBlock = document.getElementById('emailErrorBlock');
    const emailErrorText = document.getElementById('emailErrorText');
    const submitBtn = document.getElementById('submit-btn');
    const togglePassword = document.getElementById('togglePassword');
    const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    // Элементы для отображения ошибок
    const firstNameError = document.getElementById('firstNameError');
    const lastNameError = document.getElementById('lastNameError');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');

    // Функции для работы с UI
    function showError(element, message) {
        element.textContent = message;
        element.style.display = 'block';
    }

    function hideError(element) {
        element.textContent = '';
        element.style.display = 'none';
    }

    function showEmailError(message) {
        emailErrorText.textContent = message;
        emailErrorBlock.style.display = 'flex';
    }

    function hideEmailError() {
        emailErrorText.textContent = '';
        emailErrorBlock.style.display = 'none';
    }

    function disableSubmitButton(disabled) {
        submitBtn.disabled = disabled;
        if (disabled) {
            submitBtn.innerHTML = '<i class="fas fa-spinner spinner"></i> Регистрация...';
        } else {
            submitBtn.innerHTML = '<i class="fas fa-user-plus"></i> Зарегистрироваться';
        }
    }

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    // Переключение видимости пароля
    function togglePasswordVisibility(input, icon) {
        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    }

    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            togglePasswordVisibility(passwordInput, this);
        });
    }

    if (toggleConfirmPassword) {
        toggleConfirmPassword.addEventListener('click', function() {
            togglePasswordVisibility(confirmPasswordInput, this);
        });
    }

    // Обработка отправки формы
    if (registrationForm) {
        registrationForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Скрываем предыдущие ошибки
            hideEmailError();
            hideError(firstNameError);
            hideError(lastNameError);
            hideError(emailError);
            hideError(passwordError);
            hideError(confirmPasswordError);

            // Получаем данные формы
            const firstName = document.getElementById('firstName').value.trim();
            const lastName = document.getElementById('lastName').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();
            const confirmPassword = document.getElementById('confirmPassword').value.trim();
            const termsAccepted = document.getElementById('terms').checked;

            // Валидация
            let isValid = true;

            if (!firstName) {
                showError(firstNameError, 'Пожалуйста, введите имя');
                isValid = false;
            }

            if (!lastName) {
                showError(lastNameError, 'Пожалуйста, введите фамилию');
                isValid = false;
            }

            if (!email) {
                showError(emailError, 'Пожалуйста, введите email');
                isValid = false;
            } else if (!validateEmail(email)) {
                showError(emailError, 'Пожалуйста, введите корректный email');
                isValid = false;
            }

            if (!password) {
                showError(passwordError, 'Пожалуйста, введите пароль');
                isValid = false;
            } else if (password.length < 6) {
                showError(passwordError, 'Пароль должен содержать не менее 6 символов');
                isValid = false;
            }

            if (!confirmPassword) {
                showError(confirmPasswordError, 'Пожалуйста, подтвердите пароль');
                isValid = false;
            } else if (password !== confirmPassword) {
                showError(confirmPasswordError, 'Пароли не совпадают');
                isValid = false;
            }

            if (!termsAccepted) {
                alert('Пожалуйста, примите условия использования!');
                isValid = false;
            }

            if (!isValid) return;

            // Блокировка кнопки во время запроса
            disableSubmitButton(true);

            try {
                // Отправка запроса на сервер
                const response = await fetch('/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        firstName: firstName,
                        lastName: lastName,
                        email: email,
                        password: password,
                        confirmPassword: confirmPassword
                    })
                });

                if (response.status === 201) {
                    // Успешная регистрация
                    window.location.href = '/auth/login?registered';
                } else {
                    // Обработка ошибок сервера
                    const errorData = await response.text();

                    if (response.status === 400) {
                        showError(confirmPasswordError, errorData);
                    } else if (response.status === 409) {
                        showEmailError(errorData);
                    } else {
                        showEmailError('Неизвестная ошибка сервера');
                    }
                }
            } catch (error) {
                // Обработка сетевых ошибок
                showEmailError('Ошибка сети. Проверьте подключение к интернету');
            } finally {
                // Разблокировка кнопки
                disableSubmitButton(false);
            }
        });
    }
});