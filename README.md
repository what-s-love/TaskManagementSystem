# Task Management System

## 📖 Описание проекта
Task Management System — это веб-приложение для управления задачами, разработанное на Java с использованием Spring Boot. Оно включает REST API для выполнения операций, таких как авторизация, управление пользователями и задачами, с поддержкой базы данных PostgreSQL.

---

## 📋 Функциональность
- Регистрация и авторизация пользователей (с использованием JWT).
- Управление задачами: создание, обновление, удаление и просмотр.
- Роли пользователей и разграничение доступа.
- Использование Liquibase для миграции базы данных.
- Контейнеризация с помощью Docker Compose.

---

## 🚀 Запуск проекта

### Системные требования
- Docker и Docker-Compose
- Docker-образ базы данных postgres
- Java 17+ для локальной разработки

### Установка
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/what-s-love/TaskManagementSystem.git
   cd TaskManagementSystem
   ```
2. Измените файл [.env](.env) на собственные настройки (если необходимо):
- Внешний и внутренний порт приложения
  ```env
  APP_PORTS=8080:8080
  ```
- Имя Docker-образа базы данных
  ```env
  DB_IMAGE=postgres:16
  ```
- Внешний и внутренний порт базы данных
   ```env
   DB_PORTS=5050:5432
   ```
- Укажите такой же внутренний порт в адресе подключения
   ```env
   DB_URL=jdbc:postgresql://db:5432/tms_db
   ```
- Логин пользователя базы данных
   ```env
   DB_USERNAME=postgres
   ```
- Пароль пользователя базы данных
   ```env
   DB_PASSWORD=admin
   ```
- Время жизни токена авторизации (в минутах)
   ```env
   JWT_EXPIRATION_TIME=60
   ```
- Ключ генерации токена авторизации (в кодировке BASE64 размером не менее 256 бит)
   ```env
   JWT_SECRET_KEY=53A73E5F1C4E0A2D3B5F2D784E6A...
   ```
❗Убедитесь, что вы указали корректные переменные для подключения к базе данных.

3. Соберите и запустите проект с помощью Docker Compose:
   ```bash
   docker-compose up -d
   ```
4. Приложение будет доступно по адресу
   ```url
   http://localhost:{внешний_порт_приложения}
   ```

## 🗄️ Структура проекта
   ```plaintext
   TaskManagementSystem/
   ├── src/                       # Исходный код приложения
   │   ├── main/
   │   │   ├── java/              # Backend-код на Java
   │   │   └── resources/         # Файлы ресурсов (application.yml, миграции Liquibase)
   ├── Dockerfile                 # Инструкция для сборки Docker-образа
   ├── compose.yaml               # Конфигурация для запуска контейнеров
   ├── .env                       # Переменные окружения
   ├── README.md                  # Документация проекта
   └── pom.xml                    # Файл зависимостей Maven
   ```

## 🛠️ Используемые технологии
- Backend: Spring Boot 3.x (Spring Data JPA, Spring Security)
- База данных: PostgreSQL
- Миграции: Liquibase
- Аутентификация: JWT
- Контейнеризация: Docker и Docker Compose

## ❓Работа с проектом
### Тестовые данные
При запуске проекта система Liquibase наполняет базу данных тестовыми задачами и комментариями к ним.
Помимо этого создаётся 3 пользователя:
1. Администратор
   - email: admin@test.com
   - password: Test1234
2. Пользователь
   - email: user1@test.com
   - password: Test1234
3. Пользователь
   - email: user2@test.com
   - password: Test1234

### Получение токена доступа
Для неавторизованных пользователей в приложении доступен только один эндпоинт
   ```url
   /auth/sign-in
   ```
Ожидает POST запрос с телом в виде объекта в формате JSON с полями _email_ и _password_
   ```json
   {
      "email": "admin@test.com",
      "password": "Test1234"
   }
   ```
В случае успешной авторизации возвращает тело ответа в формате JSON с единственным полем _token_
   ```json
   {
      "token": "eyJhbGciOiJIUzUxMiJ9..."
   }
   ```
Для обращения к другим эндпоинтам полученное значение должно передаваться в заголовке запроса _Authorization_ с префиксом "Bearer "

## 📄 API Документация
Детальная документация доступна на  _/swagger-ui/index.html_

## 🐛 Решение проблем
1. Ошибка подключения к базе данных:
   Убедитесь, что база данных запущена и переменные окружения в .env корректны.
2. Миграции Liquibase не применяются:
   Проверьте путь к файлу master.yaml и наличие таблицы DATABASECHANGELOG в базе данных.

## 🧑‍💻 Автор
Владислав Корчевой

- GitHub: [what-s-love](https://github.com/what-s-love)
- Telegram: [@ValdisKo](https://t.me/ValdisKo)
