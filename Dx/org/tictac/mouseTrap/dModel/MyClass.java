package org.tictac.mouseTrap.dModel;

public class MyClass {
	public Class getClass(String nameclass ){
		Class newclass=null;
		try{
			if (nameclass.compareTo("String") == 0)
				return String.class;
			else if ( nameclass.compareTo("boolean") == 0) 
				return Boolean.class;
			else if ( nameclass.compareTo("byte") == 0) 
				return Byte.class;
			else if ( nameclass.compareTo("short") == 0) 
				return Short.class;				
			else if ( nameclass.compareTo("int") == 0) 
				return Integer.class;
			else if ( nameclass.compareTo("long") == 0) 
				return Long.class;
			else if ( nameclass.compareTo("float") == 0) 
				return Float.class;
			else if ( nameclass.compareTo("double") == 0) 
				return Double.class;
			else newclass = Class.forName(nameclass);
		}catch (ClassNotFoundException e){
			System.out.println("MyClass.getClass  Class Not Found Exception: " + e.getMessage());
		}catch (Exception e) {
			System.out.println("MyClass.getClass Caught Exception: " + e.getMessage());
		}
		return newclass;
	}
	
}
