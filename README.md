# Logging_T1

Цель: Реализация логирования с помощью Spring AOP

Для демонстрации логгирования созданы сущности(User, Order). Для каждой из них добавлены сервисы и репозитории
UserRestController добавлен для ручного тестирования

endpoints:

POST /users: Создать нового юзера.

GET /users: Получить список всех юзеров.

GET /users/{id}: Получить информацию о юзере по его идентификатору.

DELETE /users/{id}: Удалить юзера по его идентификатору.

В пакете aop добавлены три аспекта:

* LoggingAspect - общий аспект, логирующий ошибки контроллера и сервисного слоя и параметры методов контроллера
* OrderServiceAspect, UserServiceAspect - аспекты со срезом только на сервисный слой
  
Пример логов из LoggingAspect, POST запрос

`2024-03-31T18:34:14.701+0300 INFO log before saveUser() - [User(id=null, name=Petr, email=petr@mail.ru, orders=null)]`

`2024-03-31T18:34:14.702+0300 INFO Method args names: user`

Запись логов реализована с помощью Log4j2 с выводом в консоль и в файл logs/application.log.

Конфигурация логгирования находится в файле resources/log4j2.xml

Для старта проекта требуется запустить контейнер postgres в файле docker-compose либо выполнить команду в терминале
`docker-compose up`. Затем запустить само приложение LoggingOperationsApplication.java

