package cz.dusanrychnovsky.huffman;

import java.io.*;

public class MultiPassFileInputStream implements MultiPassInputStream {

  private final File file;
  private InputStream in;

  public MultiPassFileInputStream(File file) {
    this.file = file;
  }

  public MultiPassFileInputStream(String path) {
    this(new File(path));
  }

  @Override
  public InputStream get() throws IOException {
    close();
    in = new BufferedInputStream(new FileInputStream(file));
    return in;
  }

  @Override
  public void close() throws IOException {
    if (in != null) {
      in.close();
    }
    in = null;
  }
}
