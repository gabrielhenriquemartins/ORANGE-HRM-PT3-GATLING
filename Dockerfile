FROM openjdk:17-jdk-slim

WORKDIR /gatling

COPY pom.xml .
COPY src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN mvn clean install


CMD ["./src/test/java/orangehrm/utils/execute_gatling.sh"]

#mvn gatling:test -Dgatling.simulationClass=orangehrm.projects.regression.PerformanceRunner