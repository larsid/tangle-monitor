FROM openjdk:8
LABEL maintainder="AllanCapistrano <asantos@ecomp.uefs.br>"

ADD target/tangle-monitor-0.0.1-jar-with-dependencies.jar bin/tangle-monitor-0.0.1-jar-with-dependencies.jar
ADD tangle-monitor.sh tangle-monitor.sh

RUN chmod +x tangle-monitor.sh