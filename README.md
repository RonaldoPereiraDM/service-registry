# Semana 04 - Cross Cutting - Registry Discovery Pattern

- ## Criação Service Registry com Spring Cloud Discovery Netflix Eureka
    - links úteis
        - [Spring Cloud Projects](https://spring.io/projects/spring-cloud)
        - [Spring Cloud Netflix](https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/)
        - [Spring Cloud Netflix Features](https://docs.spring.io/spring-cloud-netflix/reference/spring-cloud-netflix.html#spring-cloud-eureka-server-standalone-mode)
        - [Acessar Service Registry normalmente](http://localhost:8761)

    - ### Etapa 1: Configuração nos Microservicos de negócio para poderem se registrar no Service Registry.
      - Nos microservices: **AuthUser, Course, Notification e Payment** foi adicionado uma configuração para definir:
        - Nome a aplicação: 
          - ```yaml
            spring:
                application:
                  name: ead-course-service #exemplo de como ficou no course-service
            ```
        - Configurações de propriedade do eureka client para cada ms poder se registrar no Registry Service:
          - ```yaml
              ## configurações de propriedade do eureka client
              eureka:
                client:
                  service-url:
                    defaultZone: 'http://localhost:8761/eureka'
              instance:
                hostname: localhost
            ```
          
          - No pom.xml de cada aplicação foi adicionado:
            - Spring Version: 2023.0.3
            - Dependência Spring Cloud Starter Netflix Eureka Client
            - Gerenciador de dependências do Spring Cloud
           