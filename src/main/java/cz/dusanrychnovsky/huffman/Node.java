package cz.dusanrychnovsky.huffman;

class Node {

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
}
