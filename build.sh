cd config
./mvnw clean package
cd ..

cd eureka
./mvnw clean package
cd ..

cd boarding
./mvnw clean package
cd ..

cd card-terminal
./mvnw clean package
cd ..

docker-compose -f build.yml build