REBOARDING 2.0
===

To run application do the following:
- DockerDesktopVM needs at least 3GB RAM
- run ```build.bat```
- after build complete run ```start-services.bat```
- after all services are up run ```start-apps.bat```
- Eureka clients default settings 30s for fetching the registry therefore need to wait before use it

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
- zuul-server (reverse proxy / filter): ```localhost:8080```

The application:
- boarding-service (for register / status check):
    - ```localhost:8080/register```
    - ```localhost:8080/status```
- terminal-service (for card reader terminals entry and exit):
    - ```localhost:8080/entry```
    - ```localhost:8080/exit```
- visualization-service (for visualize office layout and registration):
    - ```localhost:8080/layout```
    - ```localhost:8080/reservation```

Additional information:
- for postman testing the files located under ```postman``` directory
- postgresql 
    - user: ```postgres``` 
    - password: ```docker```
    - ```localhost:5432```
