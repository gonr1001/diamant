package org.tictac.mouseTrap.dModel;

import java.util.Vector;
public class MyReturn {

	public Object getTheReturn(Class typeRet,String value){
		Vector v= new Vector();
		try{		
			v.add(value);
		}catch (Exception e) {
			System.err.println("MyReturn.getTheReturn Caught Exception: " + e.getMessage());
		}
		return (new MyObject()).createObject( typeRet.getName(), v);		
	}
}
