package cz.dusanrychnovsky.huffman;

import java.io.Serializable;

abstract class Node implements Serializable {

  // TODO: allow printing trees for debugging

  private byte label;

  public void setLabel(byte label) {
    this.label = label;
  }

  public byte getLabel() {
    return label;
  }

  public Node mergeWith(Node other) {
    return new InnerNode(this, other);
  }

  public abstract void dump(StringBuilder builder, int level);
}
