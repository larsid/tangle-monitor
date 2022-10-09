package br.uefs.larsid.dlt.iot.soft;

import br.uefs.larsid.dlt.iot.soft.model.LedgerReader;
import br.uefs.larsid.dlt.iot.soft.model.ZMQServer;

/**
 * @author Allan Capistrano
 * @version 0.0.1
 */
public final class Main {

  public static void main(String[] args) {
    System.out.println("Starting...");
    LedgerReader ledgerReader = new LedgerReader(
      new ZMQServer(
        128,
        "tcp",
        "172.19.0.2",
        "5556",
        "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDX"
      )
    );
  }
}
