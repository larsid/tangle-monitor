package br.uefs.larsid.dlt.iot.soft.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Allan Capistrano, Uellington Damasceno
 * @version 0.0.1
 */
public class CLI {

  public static Optional<String> getBufferSize(String... args) {
    return getArgInList("-bfs", args);
  }

  public static Optional<String> getSocketProtocol(String... args) {
    return getArgInList("-spr", args);
  }

  public static Optional<String> getSocketURL(String... args) {
    return getArgInList("-sur", args);
  }

  public static Optional<String> getSocketPort(String... args) {
    return getArgInList("-spt", args);
  }

  public static Optional<String> getAddress(String... args) {
    return getArgInList("-adr", args);
  }

  public static Optional<String> getDltProtocol(String... args) {
    return getArgInList("-dpr", args);
  }

  public static Optional<String> getDltURL(String... args) {
    return getArgInList("-dur", args);
  }

  public static Optional<String> getDltPort(String... args) {
    return getArgInList("-dpt", args);
  }

  public static Optional<String> getQueryType(String... args) {
    return getArgInList("-qrt", args);
  }

  public static Optional<String> getTag(String... args) {
    return getArgInList("-tag", args);
  }

  public static boolean hasParam(String arg, String... args) {
    return Arrays.asList(args).indexOf(arg) != -1;
  }

  private static Optional<String> getArgInList(String arg, String... args) {
    List<String> largs = Arrays.asList(args);
    int index = largs.indexOf(arg);
    return (index == -1) ? Optional.empty() : Optional.of(largs.get(index + 1));
  }
}
