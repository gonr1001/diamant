/*
 * Created on 2004-09-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.tictac.mouseTrap.dModel;

/**
 * @author garr2701
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjectReg {
	private int _id=0;
	private Object _obj=null;
	
	public ObjectReg(int id, Object obj){
		_id=id;
		_obj=obj;
	} 
	
	/**
	 * @return Returns the _id.
	 */
	public int getId() {
		return _id;
	}
	/**
	 * @param _id The _id to set.
	 */
	public void setId(int _id) {
		this._id = _id;
	}
	/**
	 * @return Returns the _obj.
	 */
	public Object getObj() {
		return _obj;
	}
	/**
	 * @param _obj The _obj to set.
	 */
	public void setObj(Object _obj) {
		this._obj = _obj;
	}
}
