package org.tictac.mouseTrap.dModel;

import java.lang.reflect.Constructor;
import java.util.Vector;

public class MyConstructor {

	public Constructor getTheConstructor(String nameclass, String param){
		Constructor theConstructor = null;
		if (nameclass == null  || param == null){
			throw new NullPointerException("getTheConstructor.Illegal class: " + 
							nameclass+ " Param number: "+param);
		}
		try{
			Class cls = (new MyClass()).getClass(nameclass);
			Constructor constList[] = cls.getConstructors();
			for (int i = 0; i < constList.length; i++) {
				int numParams = constList[i].getParameterTypes().length;
				if (numParams == 1 ){
					//boolean equal = true; 
					String nameParam=constList[i].getParameterTypes()[0].getName();
					nameParam=cut(nameParam);
					nameclass=cut(param);
					if (nameParam.compareTo(nameclass) == 0){
						theConstructor = constList[i];
						return theConstructor;
					}//else{
						//equal = false;
					//}													
				}
			}
			System.out.println("Constructor not found. "+ cls.getName() + " "+nameclass);
			assert theConstructor!=null:"Constructor name not found.";
		}catch (Exception e) {
			System.out.println("MyConstructor.getTheConstructor(String, String) Caught Exception: " + e.getMessage());	
		}
		return theConstructor;	
	}

	public Constructor getTheConstructor(String classname, Vector params){
		Constructor theConstructor = null;
		String type="";
	
		//Precondition 
		//such as IllegalArgumentException, IndexOutOfBoundsException, or NullPointerException
		if (classname == null  || params == null){
			throw new NullPointerException("getTheConstructor.Illegal class: " + classname + " Params number: "+params.size());
		}
		try{
			Class classobj = (new MyClass()).getClass(classname);
			Constructor constList[] = classobj.getConstructors();
			for (int i = 0; i < constList.length; i++) {
				int numParams = constList[i].getParameterTypes().length;
				if (numParams == params.size() ){
					boolean equal = true; 
				
					for (int j = 0; j<params.size(); j++){ 
						String nameParam=constList[i].getParameterTypes()[j].getName();
						nameParam = translate(nameParam);
						nameParam = cut(nameParam);
					
						type=params.get(j).getClass().getName();
						type=cut(type);
					
						if (nameParam.compareTo(type) != 0)
							equal = false;
					}					
					if (equal){
						theConstructor = constList[i];
						return theConstructor;
					}										
				}
			}
			System.out.println("Constructor not found. "+ classobj.getName() + " "+type);
			assert theConstructor!=null:"Constructor name not found.";
		}catch (AssertionError e) {
			System.out.println("MyConstructor.getTheConstructor(String, Vector). "  + e.getMessage());		
		}catch (Exception e) {
			System.out.println("MyConstructor.getTheConstructor(String, Vector) Caught Exception: " + e.getMessage());		
		}	
		return theConstructor;
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
}
