// Функция для добавления товара в корзину
function addToCart(productId) {
    fetch(`/cart/add/${productId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // Обновляем счетчик корзины
                updateCartCount();

                // Визуальная обратная связь
                const button = document.querySelector(`.add-to-cart[onclick="addToCart(${productId})"]`);
                const originalHTML = button.innerHTML;
                button.innerHTML = '<i class="fas fa-check"></i> Добавлено';
                button.style.background = '#28a745';

                setTimeout(() => {
                    button.innerHTML = originalHTML;
                    button.style.background = '';
                }, 2000);
            } else if (response.status === 401) {
                alert('Для добавления товара в корзину необходимо авторизоваться');
                window.location.href = '/auth/login';
            } else {
                throw new Error('Ошибка добавления в корзину');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Не удалось добавить товар в корзину');
        });
}

// Функция для обновления счетчика корзины
function updateCartCount() {
    fetch('/cart/count')
        .then(response => response.json())
        .then(data => {
            const cartCountElement = document.querySelector('.cart-count');
            if (cartCountElement) {
                if (data.count > 0) {
                    cartCountElement.textContent = data.count;
                    cartCountElement.style.display = 'inline-block';
                } else {
                    cartCountElement.style.display = 'none';
                }
            }
        })
        .catch(error => console.error('Ошибка получения количества товаров в корзине:', error));
}

// Обработчик для поиска при нажатии Enter
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.querySelector('.search-bar input');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                document.getElementById('searchForm').submit();
            }
        });
    }

    // Валидация поискового запроса
    const searchForm = document.getElementById('searchForm');
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const searchInput = document.getElementById('searchInput');
            const query = searchInput.value.trim();

            if (query.length < 2) {
                e.preventDefault();
                alert('Поисковый запрос должен содержать минимум 2 символа');
                searchInput.focus();
            }
        });
    }

    // Обновляем счетчик корзины при загрузке страницы
    updateCartCount();
});