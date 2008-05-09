/**
 * Created on Mar 25, 2006
 * 
 * 
 * Title: DxState.java 
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
 * 
 * 
 */
package dInternal;

import java.awt.Color;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxState is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxState {
	
	private String _str;
	
	private Color _color;

	private int _value;

	/**
	 * constructor
	 * @param i 
	 * @param color 
	 * @param string 
	 */
	public DxState(String string, Color color, int i) {
		_str = string;
		_color = color;
		_value = i;
		
	}

	/**
	 * 
	 * @param col
	 */
	public void setColor(Color col) {
		_color = col;
	}

	/**
	 * 
	 * @param val
	 */
	public void setValue(int val) {
		_value = val;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getHeader() {
		return _str;
	}
	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		return _color;
	}

	/**
	 * 
	 * @return
	 */
	public int getValue() {
		return _value;
	}
} // end DxState
