package br.uefs.larsid.dlt.iot.soft.models;

import br.uefs.larsid.dlt.iot.soft.utils.CsvWriter;
import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.FindTransactionResponse;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.utils.TrytesConverter;

/**
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReadApi implements Runnable {

  /*-------------------------Constantes---------------------------------------*/
  private static final String FILE_NAME = "tangle-monitor.csv";
  private static final long SLEEP = 5000;
  /*--------------------------------------------------------------------------*/

  private Thread readApi;
  private final IotaAPI api;
  private final CsvWriter csvWriter;
  private String tag;
  private int index = 1;

  /**
   * Método construtor.
   *
   * @param protocol - Protocolo DLT
   * @param url - URL DLT
   * @param port - Porta DLT
   */
  public ReadApi(String protocol, String url, int port, String tag) {
    this.api =
      new IotaAPI.Builder().protocol(protocol).host(url).port(port).build();
    this.csvWriter = new CsvWriter(FILE_NAME);
    this.tag = tag;

    if (this.readApi == null) {
      this.readApi = new Thread(this);
      this.readApi.setName("TANGLE_MONITOR/READ_API");
      this.readApi.start();
    }
  }

  /**
   * Busca e retorna a primeira transação de uma TAG específica.
   *
   * @param tag - TAG que se deseja buscar.
   * @return String - JSON da primeira transação.
   */
  public String findFirstTransactionByTag(String tag) throws ArgumentException {
    String tagTrytes = TrytesConverter.asciiToTrytes(tag);

    FindTransactionResponse transactions =
      this.api.findTransactionsByTags(tagTrytes);

    String[] hashesTransaction = transactions.getHashes();

    GetBundleResponse response = api.getBundle(hashesTransaction[0]);

    String transactionTrytes = response
      .getTransactions()
      .get(0)
      .getSignatureFragments()
      .substring(0, 2186);

    String transactionJSON = TrytesConverter.trytesToAscii(transactionTrytes);

    return transactionJSON;
  }

  /**
   * Thread para monitoramento das consultas através da API da IOTA.
   */
  @Override
  public void run() {
    while (!this.readApi.isInterrupted()) {
      try {
        long start = System.currentTimeMillis();

        this.findFirstTransactionByTag(this.tag);

        long end = System.currentTimeMillis();
        System.out.println("API Response time (ms): " + (end - start));

        String[] data = { String.valueOf(index), String.valueOf(end - start) };

        this.csvWriter.writeData(data);

        this.index++;

        Thread.sleep(SLEEP);
      } catch (ArgumentException ae) {
        ae.printStackTrace();
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
    }
  }
}
