FROM mcr.microsoft.com/devcontainers/java:21

ENV GRADLE_USER_HOME=/workspace/.gradle

# Install basic tools
RUN apt-get update && apt-get install -y \
    curl wget git unzip zip \
    && rm -rf /var/lib/apt/lists/*

ENV GRADLE_HOME=/opt/gradle-8.4
ENV PATH="${GRADLE_HOME}/bin:${PATH}"

WORKDIR /workspace
