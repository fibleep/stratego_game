package models;

public enum Rank {
  FLAG(1),
  SPY(1),
  SCOUT(8),
  MINER(5),
  SERGEANT(4),
  LIEUTENANT(4),
  CAPTAIN(4),
  MAJOR(3),
  COLONEL(2),
  GENERAL(1),
  MARSHAL(1),
  BOMB(6);

  Rank(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  private final int count;

}
