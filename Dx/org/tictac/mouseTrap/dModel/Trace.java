/*
 * Created on 2004-09-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.tictac.mouseTrap.dModel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author garr2701
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Trace {
	
	public String write(Object obj, Object info){
		String method=methodData();
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n"+"<simple>\n<type>"+info.getClass().getName()+"</type>\n";
		str+="<value>"+info.toString()+"</value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
//	public String write(Object obj, String method, Object info){
//		String str="";
//		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
//		str+="<class>"+obj.getClass().getName()+"</class>\n";
//		str+="<method>"+method+"</method>\n"; 
//		str+="<params>\n"+"<simple>\n<type>String</type>\n<value>"+info+"</value>\n</simple>\n";
//		str+="</params>\n</instruction>\n";
//		return str;
//	}
	public String write(Object obj, int info){
		String method=methodData();
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n"+"<simple>\n<type>String</type>\n<value>"+info+"</value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	public String write(Object obj, boolean info){
		String method=methodData();
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n"+"<simple>\n";
		str+="<type>boolean</type>\n<value>"+info+"</value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	/*public String write(Object obj, String method){
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n<simple>\n<type></type>\n<value></value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}*/
	
	public String write(Object obj){
		String method=methodData();
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n<simple>\n<type></type>\n<value></value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	
	
	public String methodData(){
		String 	methodName="";
		String 	className="";
		
    	StringWriter sw=new StringWriter();
    	new Throwable().printStackTrace(new PrintWriter(sw));
	    String callStack=sw.toString();
	     
	    try{
			String classMethodName=getClassAndMethod(callStack);
			methodName=getMethodName(classMethodName);
			className=getClassName(classMethodName);
			System.out.println("Trace.methodData ---> Class that called :"+
										  className+" in the Method :"+methodName);								  
	    }catch (Exception e) {
	      System.err.println("Trace recordData Caught Exception: " + e.getMessage());
	    }
	    return methodName;
  	}

	public String getClassAndMethod(String callStack){
		String name="";
			//Format in callStack
			//java.lang.Throwable\r\n\tat package.InfoTrace.data(InfoTrace.java:20)\r\n\tat package.A.add(A.java:43)\r\n\tat package.A.main(A.java:25)
			//  Division of calls "\r\n\tat "
			//  Division of package, class, method "."
		try{
		
			int atPos=callStack.indexOf("\r\n\tat ");     //  Initial Position of Premier call at package.InfoTrace.data(InfoTrace.java:20)
			atPos=callStack.indexOf("\r\n\tat ",atPos+1); //  Initial Position of Second call at package.A.add(A.java:43)
			atPos=callStack.indexOf("\r\n\tat ",atPos+1); //  Initial Position of Second call at package.A.add(A.java:43)
			int parPos=callStack.indexOf("(",atPos);      //  Final position of Method

			name=callStack.substring(atPos+6,parPos);  
		}catch (Exception e) {
					System.err.println("getClassMethod Caught Exception: " + e.getMessage());
		} 	
		return name;
	}

	public String getClassName(String classMethodName){
		String name="";
		try{
			int dotPos=classMethodName.indexOf(".");     //  Final position of Package
			int dotPos2=classMethodName.indexOf(".",dotPos+1); //  Final position of Class
			if (dotPos2!=-1)
				dotPos=dotPos2;
				
			name=classMethodName.substring(0,dotPos); 
			
		}catch (Exception e) {
							System.err.println("getClassName Caught Exception: " + e.getMessage());
		} 
		return name;
	}
	public String getMethodName(String classMethodName){
		String name="";
		try{
			int dotPos=classMethodName.indexOf(".");     //  Final position of Package
			int dotPos2=classMethodName.indexOf(".",dotPos+1);//  Final position of Class
			if (dotPos2!=-1)
				dotPos=dotPos2;
			name=classMethodName.substring(dotPos+1,classMethodName.length());  //method
		}catch (Exception e) {
			System.err.println("getMethodName Caught Exception: " + e.getMessage());
		}
		return name;
	}
			
		
}
