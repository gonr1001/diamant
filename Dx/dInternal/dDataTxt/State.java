package dInternal.dData;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.awt.Color;
import dInternal.dUtil.DXObject;

public class State extends DXObject{
  private Color _color;
  private int _value;

  /**
   * constructor
   */
  public State() {
    _color = Color.BLACK;
    _value = 0;
  }

  /**
   * constructor
   */
  public State(Color col, int val) {
    _color = col;
    _value= val;
  }

  /**
   *
   * @param col
   */
  public void setColor(Color col){
    _color = col;
  }

  /**
   *
   * @param val
   */
  public void setValue(int val){
    _value = val;
  }

  /**
   *
   * @return
   */
  public Color getColor(){
    return _color;
  }

  /**
   *
   * @return
   */
  public int getValue(){
    return _value;
  }
/*
  public String getModif(){
     return "Hello";
  }

  public void incrModif(){

  }*/

}