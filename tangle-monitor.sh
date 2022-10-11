#!/bin/bash

java -jar bin/tangle-monitor-1.0.0-jar-with-dependencies.jar -bfs $BUFFER_SIZE -spr $ZMQ_SOCKET_PROTOCOL -sur $ZMQ_SOCKET_URL -spt $ZMQ_SOCKET_PORT -adr $ADDRESS