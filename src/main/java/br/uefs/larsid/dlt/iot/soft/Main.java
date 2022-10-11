package br.uefs.larsid.dlt.iot.soft;

import br.uefs.larsid.dlt.iot.soft.models.LedgerReader;
import br.uefs.larsid.dlt.iot.soft.models.ZMQServer;
import br.uefs.larsid.dlt.iot.soft.utils.CLI;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Allan Capistrano
 * @version 0.0.1
 */
public final class Main {

  /*---------------------------------Properties-------------------------------*/
  private static String bufferSize;
  private static String socketProtocol;
  private static String socketURL;
  private static String socketPort;
  private static String address;

  /*--------------------------------------------------------------------------*/

  public static void main(String[] args) {
    System.out.println("Starting...");

    String[] topics = { "sn", "tx" };

    readProperties(args);

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
        printlnDebug("Sorry, unable to find controller.properties.");
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
    } catch (IOException ex) {
      printlnDebug(
        "Sorry, unable to find sensors.json or not create pesistence file."
      );
    }
  }

  private static void printlnDebug(String str) {
    System.out.println(str);
  }
}
