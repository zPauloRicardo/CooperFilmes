# Use uma imagem base Debian com OpenJDK 17
FROM openjdk:17-slim-buster


# Define o diretório de trabalho
WORKDIR /api/

# Copia o arquivo JAR
COPY ./build/libs/COOPERFILME-API.jar ./COOPERFILME.jar

# Configuração do ponto de entrada
ENTRYPOINT ["java", "-jar", "COOPERFILME.jar"]

