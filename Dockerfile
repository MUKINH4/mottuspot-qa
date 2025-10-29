FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copiar todos os arquivos do Gradle wrapper primeiro
COPY gradle/ ./gradle/
COPY gradlew ./
COPY build.gradle settings.gradle ./

# Dar permissão de execução ao gradlew
RUN chmod +x ./gradlew

# Baixar dependências
RUN ./gradlew dependencies --no-daemon

# Copiar código fonte
COPY src ./src

# Construir aplicação
RUN ./gradlew build -x test --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar JAR construído
COPY --from=build /app/build/libs/*.jar mottu-spot.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mottu-spot.jar"]
