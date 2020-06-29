cd config
call mvnw clean package -P docker
cd ..

cd eureka
call mvnw clean package -P docker
cd ..

cd zuul
call mvnw clean package -P docker
cd ..

cd boarding
call mvnw clean package -P docker
cd ..

cd card-terminal
call mvnw clean package -P docker
cd ..

cd visualization
call mvnw clean package -P docker
cd ..

docker-compose -f build.yml build