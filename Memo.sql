$> docker pull mariadb
$> docker run —detach —name mariadb -p 3306:3306 —env MARIADB_USER=kspot —env MARIADB_PASSWORD=${PASSWORD} —env MARIADB_ROOT_PASSWORD=oo1414036! mariadb:latest

$> docker pull redis
$> sudo docker run -p 6379:6379 redis

show databases;

create database ${NAME};

create user '${NAME}'@'%' identified by '${PASSWORD}';

use mysql;

select * from user;

grant all privileges on ${NAME}.* to '${NAME}'@'%';

flush privileges;
