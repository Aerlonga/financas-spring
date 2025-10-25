# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-focal

# Instala o gosu para troca de usuário de forma segura
ENV GOSU_VERSION 1.17
RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends ca-certificates wget; \
    if ! command -v gpg; then \
    apt-get install -y --no-install-recommends gnupg dirmngr; \
    fi; \
    rm -rf /var/lib/apt/lists/*; \
    dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')"; \
    wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch"; \
    wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc"; \
    export GNUPGHOME="$(mktemp -d)"; \
    gpg --batch --keyserver hkps://keys.openpgp.org --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4; \
    gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu; \
    gpgconf --kill all; \
    rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc; \
    chmod +x /usr/local/bin/gosu; \
    gosu --version; \
    gosu nobody true

# Arguments for user and group IDs to avoid permission issues
ARG UID=1000
ARG GID=1000

# Create a non-root user
RUN groupadd -g $GID -o appgroup && \
    useradd -m -u $UID -g $GID -o -s /bin/bash appuser

# Set working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/FinancasSpring-*.jar app.jar

# Copia o script de entrada e o torna executável
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

EXPOSE 8080

# Define o script como ponto de entrada. Ele será executado como root.
ENTRYPOINT ["/app/entrypoint.sh"]
# O comando padrão que o entrypoint irá executar como 'appuser'
CMD ["java", "-jar", "app.jar"]

