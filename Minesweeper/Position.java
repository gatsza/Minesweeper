package Minesweeper;

public class Position {

  private int xPos;
  private int yPos;

  private int diffX;
  private int diffY;

  public Position() {
    xPos = 0;
    yPos = 0;
    diffX = 0;
    diffY = 0;
  }

  public Position(Position position, int[] difference){
    this.xPos = position.getXPos() +  difference[0];
    this.yPos = position.getYPos() +  difference[1];
  }

  public int getInt() {
    return (xPos + diffX) * 9 + yPos + diffY;
  }

  public int getXPos() {
    return xPos;
  }

  public int getYPos() {
    return yPos;
  }

  public void setDiff(int[] difference){
    this.diffX = difference[0];
    this.diffY = difference[1];
  }

  public boolean isValid() {
    return (xPos >= 0 && xPos < 9 && yPos >= 0 && yPos < 9);
  }

  public void addPos(char[] position) throws WrongCordianteException {

    if (position[0] >= 'A' && position[0] <= 'Z') {
      position[0] += 'a' - 'A';
    }

    if(position[0] < 'a' || position[0] > 'z' || position[1] < '1' || position[1] > '9')
    {
      throw new WrongCordianteException();
    }

    this.xPos = position[0] - 'a';
    this.yPos = position[1] - '1';
  }

}
