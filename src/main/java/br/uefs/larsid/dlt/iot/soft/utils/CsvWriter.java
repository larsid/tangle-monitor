package br.uefs.larsid.dlt.iot.soft.utils;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Allan Capistrano, AmanSingh2210
 * @version 1.0.0
 */
public class CsvWriter {

  /*-------------------------Constantes---------------------------------------*/
  private static final String FILE_PATH = "etc/";
  /*--------------------------------------------------------------------------*/

  private CSVWriter writer;

  /**
   * Método construtor.
   *
   * @param fileName String - Nome do arquivo .csv.
   */
  public CsvWriter(String fileName) {
    File file = new File(FILE_PATH + fileName);

    try {
      FileWriter outputFile = new FileWriter(file);
      this.writer = new CSVWriter(outputFile);

      String[] header = { "Tempo", "Tempo de resposta (s)" };
      writer.writeNext(header);
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }

  /**
   * Escreve e salva os dados no arquivo .csv.
   *
   * @param data String[] - Dados que serão gravados no arquivo.
   */
  public void writeData(String[] data) {
    try {
      this.writer.writeNext(data);
      this.writer.flush();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }

  /**
   * Fecha o arquivo .csv.
   */
  public void closeFile() {
    try {
      this.writer.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }
}
