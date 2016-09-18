package cz.dusanrychnovsky.huffman;

import static org.apache.commons.lang3.StringUtils.repeat;

class DummyNode extends Node {

  @Override
  public void dump(StringBuilder builder, int level) {
    builder.append(repeat("--", level));
    builder.append("[" + getLabel() + "]");
    builder.append("\n");
  }
}
