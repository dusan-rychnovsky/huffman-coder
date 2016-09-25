package cz.dusanrychnovsky.huffman;

import java.io.IOException;
import java.io.InputStream;

public abstract class MultiPassInputStream extends InputStream {
  public abstract void reset() throws IOException;
}
