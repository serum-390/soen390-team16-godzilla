FROM openjdk:slim

WORKDIR /home/godzilla
COPY target/godzilla-*.jar ./

RUN useradd godzilla

USER godzilla
CMD ["sh", "-c", "java -jar /home/godzilla/godzilla-*.jar"]
