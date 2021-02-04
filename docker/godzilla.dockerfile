FROM openjdk:slim

ENV SPRING_PROFILE prod

WORKDIR /home/godzilla
COPY target/godzilla-*.jar ./

RUN useradd godzilla

USER godzilla
CMD ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILE} -jar /home/godzilla/godzilla-*.jar"]
