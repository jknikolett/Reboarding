REBOARDING 2.0
===

To run application do the following:
- run ```build.bat```
- after build complete run ```start-services.bat```
- after all services are up run ```start-apps.bat```

To stop application:
- run ```stop-apps.bat```
- run ```stop-services.bat```

The following services are used:
- zookeeper
- kafka
- kafdrop (kafka ui)
- postgresql
- config-service (for centralized configuration): ```localhost:9090```
- eureka-server (service registry): ```localhost:9091```

The application:
- boarding-service (for register / status check): ```localhost:8081/boarding```
- terminal-service (for card reader terminals entry and exit): ```localhost:8082/terminal```

Additional information:
- for postman testing the files located under ```postman``` directory
- postgresql 
    - user: ```postgres``` 
    - password: ```docker```
    - localhost:5432
- capacity handling change the required row ```active``` column to ```true``` and the others to ```false```
