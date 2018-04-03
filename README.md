# Coursarium

Coursarium, a simple students and courses thing

## Prepare
* have maven, JDK 1.8, ansible

(Optional) Locally install&run postgres docker using ansible
```
cd ansible
ansible-playbook ./postgres.yml
```
OR do it manually, reading postgres.yml will yield parameters easily.

## Test
`mvn test`

## Run
Using in memory DB:

`mvn spring-boot:run`

If you want to use the postgres DB (warning: database will be empty, you need to add 1 user manually after tables are created):

`mvn spring-boot:run -D`

## Visit Swagger and play
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Authentication is simple http auth, username = user and password = token