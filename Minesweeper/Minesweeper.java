package Minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

  final static char MINE = 'X';
  final static char CLEAR = ' ';
  final static char FILL = '#';
  final static int[][] AROUND = {
      {-1, -1}, {-1, 0}, {-1, 1},
      {0, -1}, {0, 1},
      {1, -1}, {1, 0}, {1, 1}
  };

  private List<Integer> mines;
  private char[][] fields;
  private int fieldLeft;

  private boolean end;
  private boolean reset;

  public Minesweeper() {
    fields = new char[9][9];
    mines = new ArrayList<>();

    resetField();
    generateMines();
  }

  public void printField() {
    System.out.print("  |");
    for (int i = 0; i < fields.length; i++) {
      System.out.print(i + 1);
      System.out.print('|');
    }
    System.out.println("");
    System.out.println("  ___________________");

    for (int i = 0; i < fields.length; i++) {
      System.out.printf("%c |", i + 'a');
      for (char field : fields[i]) {
        System.out.print(field);
        System.out.print('|');
      }
      System.out.println();
    }

    System.out.println();

  }

  public boolean isMine(Position position) {

    if (position.isValid()) {
      int intPosition = position.getInt();
      return mines.contains(intPosition);
    }

    return false;
  }

  public boolean isEnd() {
    return end;
  }
  
  public boolean isReset(){return reset;}

  public char getMines(Position pos) {
    int mineNum = 0;

    for (int[] ints : AROUND) {
      pos.setDiff(ints);

      if (isMine(pos)) {
        mineNum++;
      }
    }

    return (mineNum == 0) ? CLEAR : (char) (mineNum + '0');
  }

  public Position getPosition() {
    Scanner reader = new Scanner(System.in);
    Position position = new Position();
    boolean isNeedPosition = true;

    while (isNeedPosition) {
      System.out.println("Please give me the position what you want to see: ");
      String rawPos = reader.nextLine();

      isNeedPosition = processPosition(rawPos, position);

    }

    return position;
  }

  public void setEnd(){ end = true;}

  public void setReset(){
      reset = true;
  }

  public boolean flipPosition(Position position) {
    int xPos = position.getXPos();
    int yPos = position.getYPos();

    if (!this.isMine(position)) {
      char field = getMines(position);

      if (fields[xPos][yPos] == FILL) {
        fields[xPos][yPos] = field;
        fieldLeft--;

        if (field == CLEAR) {
          flipAround(position);
        }
      }

      return true;
    }
    fields[xPos][yPos] = MINE;

    return false;
  }

  private void flipAround(Position position) {
    for (int[] ints : AROUND) {
      Position tmpPosition = new Position(position, ints);

      int tmpX = tmpPosition.getXPos();
      int tmpY = tmpPosition.getYPos();

      if (tmpPosition.isValid() && fields[tmpX][tmpY] != CLEAR) {
        flipPosition(tmpPosition);
      }
    }
  }

  private void generateMines() {
    Random randomGen = new Random();

    while (mines.size() < 10) {
      int mine = randomGen.nextInt(81);
      if (!mines.contains(mine)) {
        mines.add(mine);
      }
    }
  }

  private boolean processPosition(String rawPos, Position position) {
    try {

      if(rawPos.charAt(0) == 'X' ){
        setEnd();
        return false;
      }
      
      if(rawPos.charAt(0) == 'R'){
        setReset();
        return false;
      }

      if (rawPos.length() < 2) {
        throw new WrongCordianteException();
      }

      char[] charPos = {
          rawPos.charAt(0),
          rawPos.charAt(1)
      };

      position.addPos(charPos);

      return false;

    } catch (WrongCordianteException e) {
      System.out.println(
          "You gave the position in wrong format, please give me a letter and a number base on the picture");
    }

    return true;
  }

  private void resetField() {
    for (char[] field : fields) {
      Arrays.fill(field, FILL);
    }

    fieldLeft = 81;
    end = false;
    reset = false;
  }

  public static void main(String[] args) {

    Minesweeper minesweeper = new Minesweeper();

    minesweeper.printField();

    while (!minesweeper.isEnd()) {

      Position pos = minesweeper.getPosition();

      if (!minesweeper.flipPosition(pos)) {
        minesweeper.printField();
        System.out.println("GAME OVER!");
        minesweeper.setEnd();
      }

      if (minesweeper.fieldLeft == 10) {
        System.out.println("You win this game congratulation");
        minesweeper.setEnd();
      }

      minesweeper.printField();
    }
  }
}

/*

TODO

userinterface optimalizálás
fomátum kiírása
hátralévő mezők kiírása vagy aknák száma
aknák megjelölése
 reset

* */