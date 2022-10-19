FROM openjdk:8
LABEL maintainder="AllanCapistrano <asantos@ecomp.uefs.br>"

ENV ZMQ_SOCKET_PROTOCOL=tcp \
    ZMQ_SOCKET_URL=zmq.devnet.iota.org \
    ZMQ_SOCKET_PORT=5556 \
    ADDRESS=ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDX \
    BUFFER_SIZE=128 \
    DLT_PROTOCOL=https \
    DLT_URL=nodes.devnet.iot.org \
    DLT_PORT=443

ADD target/tangle-monitor-1.1.0-jar-with-dependencies.jar bin/tangle-monitor-1.1.0-jar-with-dependencies.jar
ADD tangle-monitor.sh tangle-monitor.sh

RUN chmod +x tangle-monitor.sh