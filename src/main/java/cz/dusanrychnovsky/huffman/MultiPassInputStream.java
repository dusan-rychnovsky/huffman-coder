package cz.dusanrychnovsky.huffman;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface MultiPassInputStream extends Closeable {
  InputStream get() throws IOException;
}
