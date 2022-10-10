package br.uefs.larsid.dlt.iot.soft;

import br.uefs.larsid.dlt.iot.soft.models.LedgerReader;
import br.uefs.larsid.dlt.iot.soft.models.ZMQServer;

/**
 * @author Allan Capistrano
 * @version 0.0.1
 */
public final class Main {

  public static void main(String[] args) {
    System.out.println("Starting...");

    String[] topics = { "sn", "tx" };

    new LedgerReader(
      new ZMQServer(
        128,
        "tcp",
        "172.19.0.2",
        "5556",
        "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDX",
        topics
      )
    );
  }
}
