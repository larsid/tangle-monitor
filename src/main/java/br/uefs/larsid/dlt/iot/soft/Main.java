package br.uefs.larsid.dlt.iot.soft;

import br.uefs.larsid.dlt.iot.soft.models.LedgerReader;
import br.uefs.larsid.dlt.iot.soft.models.ReadApi;
import br.uefs.larsid.dlt.iot.soft.models.ZMQServer;
import br.uefs.larsid.dlt.iot.soft.utils.CLI;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Allan Capistrano
 * @version 1.0.1
 */
public final class Main {

  /*---------------------------------Properties-------------------------------*/
  private static String bufferSize;
  private static String socketProtocol;
  private static String socketURL;
  private static String socketPort;
  private static String address;
  private static String dltProtocol;
  private static String dltURL;
  private static String dltPort;
  private static String queryType;
  private static String tag;

  /*--------------------------------------------------------------------------*/

  public static void main(String[] args) {
    System.out.println("Starting...");

    String[] topics = { "sn", "tx" };

    readProperties(args);

    if (queryType.equals("api")) {
      new ReadApi(dltProtocol, dltURL, Integer.parseInt(dltPort), tag);
    } else if (queryType.equals("zmq")) {
      new LedgerReader(
        new ZMQServer(
          Integer.parseInt(bufferSize),
          socketProtocol,
          socketURL,
          socketPort,
          address,
          topics
        )
      );
    } else {
      System.err.println(
        "Error! unknown query type, you should try 'api' or 'zmq'."
      );
    }
  }

  /**
   * Realiza leitura das propriedades passadas por parâmetro ou resgata
   * valores presentes no arquivo de propriedade.
   *
   * @param args String[] - Dados passados na execução do projeto.
   */
  public static void readProperties(String[] args) {
    try (
      InputStream input = Main.class.getResourceAsStream(
          "tangle-monitor.properties"
        )
    ) {
      if (input == null) {
        printlnDebug("Sorry, unable to find tangle-monitor.properties.");
        return;
      }

      Properties props = new Properties();
      props.load(input);

      bufferSize =
        CLI.getBufferSize(args).orElse(props.getProperty("bufferSize"));

      socketProtocol =
        CLI.getSocketProtocol(args).orElse(props.getProperty("socketProtocol"));

      socketURL = CLI.getSocketURL(args).orElse(props.getProperty("socketURL"));

      socketPort =
        CLI.getSocketPort(args).orElse(props.getProperty("socketPort"));

      address = CLI.getAddress(args).orElse(props.getProperty("address"));

      dltProtocol =
        CLI.getDltProtocol(args).orElse(props.getProperty("dltProtocol"));

      dltURL = CLI.getDltURL(args).orElse(props.getProperty("dltURL"));

      dltPort = CLI.getDltPort(args).orElse(props.getProperty("dltPort"));

      queryType = CLI.getQueryType(args).orElse(props.getProperty("queryType"));

      tag = CLI.getTag(args).orElse(props.getProperty("tag"));
    } catch (IOException ex) {
      printlnDebug("Sorry, unable to find tangle-monitor.properties.");
    }
  }

  private static void printlnDebug(String str) {
    System.out.println(str);
  }
}
