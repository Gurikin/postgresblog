# Подготовка окружения
docker compose -f docker/docker-compose.yaml up -d

# Запуск приложения
maven clean install
java -jar target/postgresblog-0.0.1-SNAPSHOT.jar

# Пояснения к выбору решений
Теги решено хранить в отдельной таблице как справочник.
Связь тегов с постами сделана через таблицу многие-ко-многим по следующим причинам:
1. Экономия места по сравнению с хранением массива тегов в каждой записи в таблице posts
   1. Как в случае с данными
   2. Так и в случае с индексами
2. Более быстрый поиск
   1. как постов по тегам
   2. так и тегов к посту

# Необходимо доработать
1. Не успел реализовать все crud операции
2. Не успел реализовать функционал части 2 задания

# Curl запросы, для тестирования реализованных endpoints
## Для использования в idea
Лежат в папке curl в корне проекта в виде http запросов

## Для использования в консоли
### Fake login
curl -X POST --location "http://localhost:8080/user/login" \
-H "Content-Type: application/json" \
-d '{
"login": "harry",
"password": ""
}'

### SignUp (without password)
curl -X POST --location "http://localhost:8080/user/signup" \
-H "Content-Type: application/json" \
-d '{
"login": "harry",
"email": "harry@mail.ru",
"password": ""
}'

### Создание постов
curl -X POST --location "http://localhost:8080/posts" \
-H "Content-Type: application/json" \
-d '{
"title": "Программирование - это заголовок",
"content": "Программирование — это искусство создания решений через код. Это контент.",
"authorId": 1
}'

### Аудит постов
curl -X GET --location "http://localhost:8080/private/audit-info/2" \
-H "Content-Type: application/json"

### Полнотекстовый поиск по заголовкам и содержимому постов
curl -X GET --location "http://localhost:8080/posts?searchText=%D0%B8%D1%88%D0%BC%D0%B0%2C%D0%B7%D0%B0%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BE%D0%BA%2C%D0%BA%D0%BE%D0%B4%2C%D0%BA%D0%BE%D0%BD%D1%82%D0%B5%D0%BD%D1%82" \
-H "Content-Type: application/json"

### Получение всех постов по userId
curl -X GET --location "http://localhost:8080/posts/1" \
-H "Content-Type: application/json"