package br.uefs.larsid.dlt.iot.soft.models;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.FindTransactionResponse;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.utils.TrytesConverter;

public class ReadApi {

  private IotaAPI api;

  public ReadApi(String protocol, String url, int port) {
    this.api =
      new IotaAPI.Builder().protocol(protocol).host(url).port(port).build();
  }

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
