package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.awt.Color;
import dInternal.dUtil.DXObject;

public class State extends DXObject{
  private Color color;
  private int value;

  /**
   * constructor
   */
  public State() {
    color = Color.BLACK;
  }

  /**
   * constructor
   */
  public State(Color col, int val) {
    color = col;
    value= val;
  }

  /**
   *
   * @param col
   */
  public void setColor(Color col){
    color = col;
  }

  /**
   *
   * @param val
   */
  public void setValue(int val){
    value = val;
  }

  /**
   *
   * @return
   */
  public Color getColor(){
    return color;
  }

  /**
   *
   * @return
   */
  public int getValue(){
    return value;
  }

  public String getModif(){
     return "Hello";
  }

  public void incrModif(){

  }

}