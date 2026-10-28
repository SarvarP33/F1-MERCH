# Pit Lane Store — магазин мерча Формулы 1

Учебный интернет-магазин на **Java 17 + Spring Boot 3 + Thymeleaf**.

## Возможности
- Главная страница с хитами продаж и выбором команды
- Каталог с поиском, фильтрами по команде и категории, сортировкой
- Карточка товара с выбором размера и количества
- Корзина (хранится в сессии): изменение количества, удаление, расчёт доставки
- Оформление заказа с валидацией формы
- Страница «Спасибо за заказ» и собственная страница ошибок 404

## Как запустить в IntelliJ IDEA
1. Распакуйте архив.
2. `File → Open…` → выберите папку `f1-merch` (или сам `pom.xml`) → **Open as Project**.
3. Дождитесь, пока Maven скачает зависимости (значок слона справа → Reload).
4. Убедитесь, что выбран JDK 17+: `File → Project Structure → SDK`.
5. Откройте `src/main/java/com/f1merch/F1MerchApplication.java` и нажмите зелёный ▶ рядом с `main`.
6. Откройте в браузере: http://localhost:8080

Из терминала: `mvn spring-boot:run`

## Структура
```
src/main/java/com/f1merch/
├── F1MerchApplication.java      — точка входа
├── model/                       — Product, Team, Category, CartItem, OrderForm
├── service/                     — ProductService (каталог), CartService (корзина)
└── controller/                  — ShopController, CartController, GlobalModelAdvice
src/main/resources/
├── templates/                   — HTML-страницы (Thymeleaf)
├── static/css/style.css         — стили
└── application.properties
```

## Как добавить товар
В `ProductService` в конструкторе добавьте строку `add(...)` — название, описание, цена, категория, команда, эмодзи-иконка, размеры, флаг «хит».

## Идеи для развития
- Подключить базу данных (Spring Data JPA + H2/PostgreSQL)
- Добавить реальные фото товаров в `static/images`
- Регистрация и вход (Spring Security)
- Админ-панель для управления товарами
