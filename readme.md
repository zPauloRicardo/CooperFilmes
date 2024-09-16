# CooperFilmes

API Backend Cooperfilmes

## Utiliza:  
> Java 17  
> Spring Boot 3  
> Gradle   
> Docker  
> Postgres  
> Conceitos de Clean Arch e DDD  


## Como utilizar


Primeiro deve ser feito o build da aplicação utilizando gradle
```bash
gradlew assemble
```

Após conclusão do build, deve ser feito o build da imagem docker
```bash
docker build -t cooperfilmes .
```

Após finalizar build da imagem docker pode ser inicializado a aplicação utilizando docker compose.
```bash
cd sandbox
docker compose up -d
```

### Usuarios
Os usuarios listados são:
> user: revisor@paulojr.me  
> senha: cooperfilmes

> user: analista@paulojr.me  
> senha: cooperfilmes

> user: aprovador@paulojr.me  
> senha: cooperfilmes

> user: aprovador2@paulojr.me  
> senha: cooperfilmes

> user: aprovador3@paulojr.me  
> senha: cooperfilmes
