FROM eclipse-temurin:21-jdk

# Install basic tools
RUN apt-get update && apt-get install -y \
    curl wget git unzip zip \
    && rm -rf /var/lib/apt/lists/*

# Install Gradle (optional if using wrapper)
RUN wget https://services.gradle.org/distributions/gradle-8.4-bin.zip -P /tmp \
    && unzip /tmp/gradle-8.4-bin.zip -d /opt \
    && ln -s /opt/gradle-8.4/bin/gradle /usr/bin/gradle

ENV GRADLE_HOME=/opt/gradle-8.4
ENV PATH="${GRADLE_HOME}/bin:${PATH}"

WORKDIR /workspace
