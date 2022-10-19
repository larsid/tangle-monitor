package br.uefs.larsid.dlt.iot.soft.models;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.FindTransactionResponse;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.utils.TrytesConverter;

/**
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReadApi {

  private IotaAPI api;

  /**
   * Método construtor.
   *
   * @param protocol - Protocolo DLT
   * @param url - URL DLT
   * @param port - Porta DLT
   */
  public ReadApi(String protocol, String url, int port) {
    this.api =
      new IotaAPI.Builder().protocol(protocol).host(url).port(port).build();
  }

  /**
   * Busca e exibe transações por uma TAG específica.
   *
   * @param tag - TAG que se deseja buscar.
   */
  public void findTransactionsByTag(String tag) {
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

    System.out.println(transactionJSON);
  }
}
