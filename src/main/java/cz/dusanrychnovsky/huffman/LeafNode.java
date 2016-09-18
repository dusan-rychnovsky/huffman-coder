package cz.dusanrychnovsky.huffman;

import static org.apache.commons.lang3.StringUtils.repeat;

class LeafNode extends Node {

  private final Character character;

  public LeafNode(Character character) {
    this.character = character;
  }

  public Character getCharacter() {
    return character;
  }

  @Override
  public void dump(StringBuilder builder, int level) {
    builder.append(repeat("--", level));
    builder.append("[" + getLabel() + "]");
    builder.append(" ");
    builder.append(getCharacter());
    builder.append("\n");
  }
}
