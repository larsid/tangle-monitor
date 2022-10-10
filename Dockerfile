FROM openjdk:8
LABEL maintainder="AllanCapistrano <asantos@ecomp.uefs.br>"

ADD target/tangle-monitor-0.0.1-jar-with-dependencies.jar tangle-monitor-0.0.1-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar","tangle-monitor-0.0.1-jar-with-dependencies.jar"]