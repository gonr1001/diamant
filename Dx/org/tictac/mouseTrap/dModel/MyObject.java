package org.tictac.mouseTrap.dModel;

import java.lang.reflect.Constructor;
import java.util.Vector;

public class MyObject {

	public Object createObject (String type, String value){
		Object newobj = new Object();
		
		if (type == null  || value == null){
					throw new NullPointerException("getTheConstructor.Illegal class: " + 
					type+ " Param number: "+ value);
		}	
		//		boolean, byte, char?, short, int, long, float, and double
		//Byte, Double, Float, Integer, Long, and Short
	
		if (type=="" && value==""){
			return null;
		}
		
		try {		
			Class myclass = (new MyClass()).getClass(type);		
			Constructor myconstructor=(new MyConstructor()).getTheConstructor(type, value.getClass().getName());
			Object listparam[]={value}; 
			newobj = myconstructor.newInstance(listparam);
		}catch (Exception e){
		System.err.println("MyObject.createObject (String, String) Caught Exception: " + e.getMessage());	
		}
		return newobj;
	}

	public Object createObject (String type, Vector vvalue){
		Object newobj = new Object();
		
		if (type == null  || vvalue == null){
					throw new NullPointerException("getTheConstructor.Illegal class: " + 
					type+ " Param number: "+ vvalue.toString());
		}	
		//		boolean, byte, char?, short, int, long, float, and double
		//Byte, Double, Float, Integer, Long, and Short
	
		
		try {		
			Class myclass = (new MyClass()).getClass(type);		
			Constructor myconstructor=(new MyConstructor()).getTheConstructor(type, vvalue);
			Object listparam[]= new Object[vvalue.size()];
			for (int i=0;i<vvalue.size();i++){
				listparam[i]=vvalue.get(i);
			}	
			newobj = myconstructor.newInstance(listparam);
		}catch (Exception e){
		System.out.println("MyObject.createObject (String, String) Caught Exception: " + e.getMessage());	
		}
		return newobj;
	}
}
