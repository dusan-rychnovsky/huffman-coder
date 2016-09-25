package cz.dusanrychnovsky.huffman;

import java.io.*;

public class Main {

  private static final String USAGE = "USAGE [encode/decode] [source-file] [target-file]";

  private static enum Mode {
    ENCODE, DECODE
  }

  public static void main(String[] args) {

    if (args.length != 3) {
      System.out.println(USAGE);
      System.out.println(
        "Wrong number of arguments. Expected [3], got [" + args.length + "]."
      );
      return;
    }

    Mode mode;
    try {
      mode = Mode.valueOf(args[0].toUpperCase());
    }
    catch (IllegalArgumentException ex) {
      System.out.println(USAGE);
      System.out.println(
        "Wrong mode. Expected [complete/daily], got [" + args[0] + "]."
      );
      return;
    }

    try (MultiPassFileInputStream in = new MultiPassFileInputStream(args[1]);
         OutputStream out = new BufferedOutputStream(new FileOutputStream(args[2]))) {

      if (mode == Mode.ENCODE) {
        new Encoder().encode(in, out);
      }
      else {
        new Decoder().decode(in, out);
      }
    }
    catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
      return;
    }
  }
}
