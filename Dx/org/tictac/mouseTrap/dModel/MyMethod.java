package org.tictac.mouseTrap.dModel;

import java.lang.reflect.Method;
import java.util.Vector;

public class MyMethod {

	public Method getTheMethod(Class cls, String methodName, Vector params){
		// rgd: I have to check when the params[0]==null
		Method oneMethod = null;
		String type="";
		//Precondition in public method
		//such as IllegalArgumentException, IndexOutOfBoundsException, or NullPointerException
		if (cls == null  || methodName == null){
			throw new NullPointerException("getTheMethod.Illegal class: " + cls.getName() + "methodName: "+methodName);
		}
		try{
			Method methList[] = cls.getDeclaredMethods();
			for (int i = 0; i < methList.length; i++) {
				//String c1 = methList[i].getName();
				//int c2 = methList[i].getParameterTypes().length;
				//int c3 = params.size();
				if (methList[i].getName().compareTo(methodName) == 0){
					boolean equal = true;
					if (params.size()==1 && methList[i].getParameterTypes().length==0){
						equal=true;
					}else{
						if (methList[i].getParameterTypes().length == params.size()) {
							for (int j = 0; j<params.size(); j++){
								String nameParam=methList[i].getParameterTypes()[j].getName();
								nameParam = translate(nameParam);
								nameParam = cut(nameParam);
								type=params.get(j).getClass().getName();
								type = cut(type);
								if (nameParam.compareTo(type) != 0){
									equal = false;
								}
							}
						}
					}
					if (equal){
						oneMethod = methList[i];
						break;
					}//else{
						//System.out.println("Method not found. "+ methodName + " "+type);
					//}										
				}
			}
			assert oneMethod!=null:"Method name not found.";
		}catch (AssertionError e) {
			System.out.println("MyMethod.getTheMethod. "  + e.getMessage());		
		}catch (Exception e) {
		System.out.println("MyMethod.getTheMethod Caught Exception: " + e.getMessage());		
		}
		//Postcondition		
		if (oneMethod==null)
			System.out.println("Method not found. "+ methodName + " "+params.toString());
		return oneMethod;
	}
	
	public String translate(String className){
			String str="";
			if (className.compareTo("String") == 0)
				str = String.class.getName();	
			else if ( className.compareTo("boolean") == 0) 
				str = Boolean.class.getName();
			else if ( className.compareTo("byte") == 0) 
				str = Byte.class.getName();
			else if ( className.compareTo("short") == 0) 
				str = Short.class.getName();				
			else if ( className.compareTo("int") == 0) 
				str = Integer.class.getName();
			else if ( className.compareTo("long") == 0) 
				str = Long.class.getName();
			else if ( className.compareTo("float") == 0) 
				str = Float.class.getName();
			else if ( className.compareTo("double") == 0) 
				str = Double.class.getName();
			else
				str = className;
			return str;
		}	
		
	public String cut(String str){
		try{	
			String[] list;
			if (str.indexOf(".")> -1){
				list = str.split("\\.");
				str=list[list.length-1];
			}
		}catch (Exception e) {
			System.out.println("MyConstructor.cut Caught Exception: " + e.getMessage());		
		}
		return str;
	}	
}
