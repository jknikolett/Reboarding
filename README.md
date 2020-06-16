REBOARDING 2.0
===

To run application do the following:
- run ```build.bat```
- after build complete run ```start-services.bat```
- after all services are up run ```start-apps.bat```

The following services are used:
- zookeeper
- kafka
- kafdrop (kafka ui)
- postgresql
- config-service (for centralized configuration)
- eureka-server (service registry)

The application:
- boarding-service (for register / status check)
- terminal-service (for card reader terminals entry and exit)

Additional information:
- for postman testing the files located under ```postman``` directory
- postgresql 
    - user: ```postgres``` 
    - password: ```docker```
    - localhost:5432
- capacity handling change the required row ```active``` column to ```true``` and the others to ```false```