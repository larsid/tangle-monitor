package br.uefs.larsid.dlt.iot.soft.models;

import br.uefs.larsid.dlt.iot.soft.services.ILedgerReader;
import br.uefs.larsid.dlt.iot.soft.services.ILedgerSubscriber;
import br.uefs.larsid.dlt.iot.soft.utils.CsvWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Allan Capistrano, Antonio Crispim, Uellington Damasceno
 * @version 0.0.1
 */
public class LedgerReader implements ILedgerReader, Runnable {

  /*-------------------------Constantes---------------------------------------*/
  private static final String FILE_NAME = "tangle-monitor.csv";
  /*--------------------------------------------------------------------------*/

  private Thread DLTInboundMonitor;
  private final Map<String, Set<ILedgerSubscriber>> topics;
  private final ZMQServer server;
  private final CsvWriter csvWriter;
  private int temp = 0;

  public LedgerReader(ZMQServer server) {
    this.topics = new HashMap();
    this.server = server;
    this.csvWriter = new CsvWriter(FILE_NAME);

    if (this.DLTInboundMonitor == null) {
      this.DLTInboundMonitor = new Thread(this);
      this.DLTInboundMonitor.setName("CLIENT_TANGLE/DLT_INBOUND_MONITOR");
      this.DLTInboundMonitor.start();
    }
  }

  @Override
  public void subscribe(String topic, ILedgerSubscriber subscriber) {
    if (topic != null) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null) {
        subscribers.add(subscriber);
      } else {
        subscribers = new HashSet();
        subscribers.add(subscriber);
        this.topics.put(topic, subscribers);
        this.server.subscribe(topic);
      }
    }
  }

  @Override
  public void unsubscribe(String topic, ILedgerSubscriber subscriber) {
    if (topic != null) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null && !subscribers.isEmpty()) {
        subscribers.remove(subscriber);
        if (subscribers.isEmpty()) {
          this.server.unsubscribe(topic);
          this.topics.remove(topic);
        }
      }
    }
  }

  @Override
  public void run() {
    while (!this.DLTInboundMonitor.isInterrupted()) {
      try {
        long start = System.currentTimeMillis();
        String receivedMessage = this.server.take();

        if (receivedMessage != null && receivedMessage.contains("/")) {
          String[] data = receivedMessage.split("/");
          String topic = data[0];
          String message = data[1];
          notifyAll(topic, message);
        }

        long end = System.currentTimeMillis();
        System.out.println("Response time (ms): " + (end - start));

        String[] data = { String.valueOf(1), String.valueOf(end - start) };

        this.csvWriter.writeData(data);

        /**
         * Ver qual o lugar certo para fechar o arquivo e salvar as informações.
         */
        if (temp == 3) {
          this.csvWriter.closeFile();
        }

        temp++;
      } catch (InterruptedException ex) {
        System.out.println(ex);
        this.DLTInboundMonitor.interrupt();
      }
    }
  }

  private void notifyAll(String topic, Object object) {
    if (topic != null && !topic.isEmpty()) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null && !subscribers.isEmpty()) {
        subscribers.forEach(sub -> sub.update(object));
      }
    }
  }
}
