package dInternal;

/**
*
* Title: DState $Revision $  $ Date:  $
* Description: DState is a class used to
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $ $
* @author  $Author: garr2701 $
* @since JDK1.3
*/

import java.awt.Color;

import dInternal.DObject;

public class DState extends DObject{
  private Color _color;
  private int _value;

  /**
   * constructor
   */
  public DState() {
    _color = Color.BLACK;
    _value = 0;
  }

  /**
   * constructor
   */
  public DState(Color col, int val) {
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

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}

}