package cz.dusanrychnovsky.huffman;

import java.io.*;

public class MultiPassFileInputStream extends MultiPassInputStream {

  private final File file;
  private FileInputStream in;

  public MultiPassFileInputStream(File file) throws FileNotFoundException {
    this.file = file;
    in = new FileInputStream(file);
  }

  public MultiPassFileInputStream(String path) throws IOException {
    this(new File(path));
  }

  @Override
  public int read() throws IOException {
    return in.read();
  }

  @Override
  public void close() throws IOException {
    if (in != null) {
      in.close();
      in = null;
    }
  }

  @Override
  public void reset() throws IOException {
    close();
    in = new FileInputStream(file);
  }
}
