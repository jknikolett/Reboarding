cd config
call mvnw clean package
cd ..

cd eureka
call mvnw clean package
cd ..

cd boarding
call mvnw clean package
cd ..

cd card-terminal
call mvnw clean package
cd ..

docker-compose -f build.yml build