package br.uefs.larsid.dlt.iot.soft.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

/**
 * @author Allan Capistrano
 * @version 0.0.1
 */
public class ZMQServer implements Runnable {

  private Thread serverThread;
  private final BlockingQueue<String> DLTInboundBuffer;
  private ZMQ.Socket serverListener;
  private String socketURL;
  private String address;

  public ZMQServer(
    int bufferSize,
    String socketProtocol,
    String socketURL,
    String socketPort,
    String address
  ) {
    this.DLTInboundBuffer = new ArrayBlockingQueue(bufferSize);
    this.serverListener = ZMQ.context(1).socket(SocketType.SUB);
    this.socketURL =
      String.format("%s://%s:%s", socketProtocol, socketURL, socketPort);
    this.address = address;

    System.out.println("Socket URL: " + this.socketURL);

    this.serverListener.connect(this.socketURL);
    this.serverThread = new Thread(this);
    this.serverThread.setName("CLIENT_TANGLE/ZMQ_SERVER");
    this.serverThread.start();
  }

  public void subscribe(String topic) {
    System.out.println("Subscribe: " + topic);
    this.serverListener.subscribe(topic);
  }

  public void unsubscribe(String topic) {
    this.serverListener.unsubscribe(topic);
  }

  public String take() throws InterruptedException {
    return this.DLTInboundBuffer.take();
  }

  @Override
  public void run() {
    System.out.println("Address: " + address);
    while (!this.serverThread.isInterrupted()) {
      byte[] reply = serverListener.recv(0);
      String[] data = (new String(reply).split(" "));

      if (data[0].equals("tx") && data[2].equals(address)) {
        this.putReceivedMessageBuffer("tx/" + data[1]);
      } else if (data[0].equals("sn") && (data[3].equals(address))) {
        this.putReceivedMessageBuffer("sn/" + data[2]);
      }
    }
  }

  private void putReceivedMessageBuffer(String receivedMessage) {
    try {
      this.DLTInboundBuffer.put(receivedMessage);
    } catch (InterruptedException ex) {
      Logger.getLogger(ZMQServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
