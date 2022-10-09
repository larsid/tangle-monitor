package br.uefs.larsid.dlt.iot.soft.services;

/**
 *
 * @author Uellington Damasceno
 * @version 0.0.1
 */
public interface ILedgerReader {
  public void subscribe(String topic, ILedgerSubscriber subscriber);

  public void unsubscribe(String topic, ILedgerSubscriber subscriber);
}
