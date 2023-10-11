# tangle-monitor
Faz o monitoramento do tempo de resposta, em milissegundos (ms), de consultas na *blockchain* Tangle, através da *IOTA API* ou do *ZMQ*, e escreve em um arquivo `.csv` .

## Como utilizar
Recomendamos a utilização do Docker, mas também é possível executar o projeto através do arquivo `.jar`.

### Via Docker

Você pode utilizar a nossa imagem que está disponível no [Docker Hub](https://hub.docker.com/r/larsid/tangle-monitor), ou fazer o *build* da imagem manualmente.

#### Build da imagem Docker:

1. Clone este projeto;
2. Acesse o diretório do projeto;
3. Digite o comando para realizar o *build* da imagem:
   ```powershell
   docker build -t larsid/tangle-monitor:<tag_name> .
   ```
4. Execute o container<sup>1</sup>:
   ```powershell
   docker run -it larsid/tangle-monitor:<tag_name>
   ```
   
###### Obs<sup>1</sup>: Dessa maneira irá executar com as configurações padrões. ############
   
### Via `.jar`

1. Clone este projeto;
2. Acesse o diretório do projeto;
3. Compile o projeto:
   ```powershell
   mvn clean compile assembly:single
   ```
4. Execute o projeto<sup>2</sup>:
   ```powershell
   java -jar bin/tangle-monitor-1.1.0-jar-with-dependencies.jar
   ```
   
###### Obs<sup>2</sup>: Dessa maneira irá executar com as configurações padrões. ############

## Sobrescrevendo as configurações padrões

### Via Docker

|Parâmetro|Descrição|Valor padrão|
|---------|---------|------------|
|ZMQ_SOCKET_PROTOCOL|Define qual é o protocolo do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|tcp|
|ZMQ_SOCKET_URL|Define qual é a URL do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|zmq.devnet.iota.org|
|ZMQ_SOCKET_PORT|Define qual é a porta do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|5556|
|ADDRESS|Define o endereço da transação.|[Verificar aqui](https://github.com/larsid/tangle-monitor/blob/main/src/main/resources/br/uefs/larsid/dlt/iot/soft/tangle-monitor.properties)|
|BUFFER_SIZE|Define o tamanho máximo do `buffer` que armazena as transações que serão enviadas para a rede.|128|
|DLT_PROTOCOL|Define qual é o tipo de protocolo utilizado pelo cliente da API.|https|
|DLT_URL|Define qual é a URL do nó da rede que o cliente deve se conectar.|nodes.devnet.iot.org|
|DLT_PORT|Define a porta.|443|
|QUERY_TYPE<sup>3</sup>|Tipo de consulta para o monitoramento.|api|
|TAG|TAG que será utilizada para as consultas utilizando a *IOTA API*.|clientTag|

###### Obs<sup>3</sup>: Os tipos aceitos são `api` ou `zmq`. ######

Após realizar o *build* da imagem ou utilizando a disponibilizado no [Docker Hub](https://hub.docker.com/repository/docker/larsid/tangle-monitor), basta utilizar os parâmetros acima.

#### Exemplo:

```powershell
docker run -it -e ZMQ_SOCKET_URL=0.0.0.0 -e DLT_PROTOCOL=http -e DLT_URL=0.0.0.0 -e DLT_PORT=14265 -e TAG=my_transaction_tag -e QUERY_TYPE=api larsid/tangle-monitor:<tag_name>
```

### Via `.jar`

|Parâmetro|Descrição|Valor padrão|
|---------|---------|------------|
|-spr|Define qual é o protocolo do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|tcp|
|-sur|Define qual é a URL do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|zmq.devnet.iota.org|
|-spt|Define qual é a porta do sistema de mensageria que utilizado pela rede para notificar o estado das transações.|5556|
|-adr|Define o endereço da transação.|[Verificar aqui](https://github.com/AllanCapistrano/tangle-monitor/blob/main/src/main/resources/br/uefs/larsid/dlt/iot/soft/tangle-monitor.properties)|
|-bfs|Define o tamanho máximo do `buffer` que armazena as transações que serão enviadas para a rede.|128|
|-dpr|Define qual é o tipo de protocolo utilizado pelo cliente da API.|https|
|-dur|Define qual é a URL do nó da rede que o cliente deve se conectar.|nodes.devnet.iot.org|
|-dpt|Define a porta.|443|
|-qrt<sup>4</sup>|Tipo de consulta para o monitoramento.|api|
|-tag|TAG que será utilizada para as consultas utilizando a *IOTA API*.|cloud/c1|

###### Obs<sup>4</sup>: Os tipos aceitos são `api` ou `zmq`. ######
###### Obs: Também é possível alterar essas configurações através do arquivo [tangle-monitor.properties](https://github.com/larsid/tangle-monitor/blob/main/src/main/resources/br/uefs/larsid/dlt/iot/soft/tangle-monitor.properties) ######

Após compilar o projeto, basta utilizar os parâmetros acima.

#### Exemplo:

```powershell
java -jar tangle-monitor-1.1.0-jar-with-dependencies.jar -sur 0.0.0.0 -spt -dpr http -dur 0.0.0.0 -dpt 14265 -qrt api -tag api
```
