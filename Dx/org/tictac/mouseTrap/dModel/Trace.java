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
import java.util.Vector;

/**
 * @author garr2701
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Trace {
	
	public String write(Object obj, Object info){
		String method=methodData();
		String str="<i>\n";
		str+=formatIdClassMethod(obj.hashCode(), obj.getClass().getName(),method);
		str+="<p>";
		str+="<o>";
		str+="<t>"+info.getClass().getName()+"</t>";
		str+="<v>"+info.toString()+"</v>";
		str+="</o>";
		str+="</p></i>\n";
		return str;
	}
	
	public String write(Object obj, Vector info){
		String method=methodData();
		info.trimToSize();
		String str="<i>\n";
		str+=formatIdClassMethod(obj.hashCode(), obj.getClass().getName(),method);
		str+="<p>";
		for (int i=0; i<info.size();i++){
				str+="<s>";
				str+="<t>"+info.get(i).getClass().getName()+"</t>";
				str+="<v>"+info.get(i).toString()+"</v>";
				str+="</s>";
		}
		str+="</p></i>\n";
		return str;
	}
	
	public String write(Object obj, boolean info){
		String str="";
		str=writePrimitiveData(obj,Boolean.class.getName(), Boolean.toString(info));
		return str;
	}
	public String write(Object obj, Boolean info){
		String str="";
		str=writePrimitiveData(obj,Boolean.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, byte info){
		String str="";
		str=writePrimitiveData(obj,Byte.class.getName(), Byte.toString(info));
		return str;
	}
	public String write(Object obj, Byte info){
		String str="";
		str=writePrimitiveData(obj,Byte.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, short info){
		String str="";
		str=writePrimitiveData(obj,Short.class.getName(), Short.toString(info));
		return str;
	}
	public String write(Object obj, Short info){
		String str="";
		str=writePrimitiveData(obj,Short.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, int info){
		String str="";
		str=writePrimitiveData(obj,Integer.class.getName(), Integer.toString(info));
		return str;
	}
	public String write(Object obj, Integer info){
		String str="";
		str=writePrimitiveData(obj,Integer.class.getName(), info.toString());
		return str;
	}
	
	public String write(Object obj, long info){
		String str="";
		str=writePrimitiveData(obj,Long.class.getName(), Long.toString(info));
		return str;
	}
	
	public String write(Object obj, Long info){
		String str="";
		str=writePrimitiveData(obj,Long.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, float info){
		String str="";
		str=writePrimitiveData(obj,Float.class.getName(), Float.toString(info));
		return str;
	}
	public String write(Object obj, Float info){
		String str="";
		str=writePrimitiveData(obj,Float.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, double info){
		String str="";
		str=writePrimitiveData(obj,Double.class.getName(), Double.toString(info));
		return str;
	}
	public String write(Object obj, Double info){
		String str="";
		str=writePrimitiveData(obj,Double.class.getName(), info.toString());
		return str;
	}
	public String write(Object obj, char info){
		String str="";
		str=writePrimitiveData(obj,Character.class.getName(), Character.toString(info));
		return str;
	}
	public String writePrimitiveData(Object obj,String type, String info){
		String method=methodData();
		String str="<i>\n";
		str+=formatIdClassMethod(obj.hashCode(), obj.getClass().getName(),method);
		str+="<p>"+"<s><t>"+type+"</t>";
		str+="<v>"+info+"</v></s>";
		str+="</p></i>\n";
		return str;
	}
	public String write(Object obj, String info){
		String method=methodData();
		String str="<i>\n";
		str+=formatIdClassMethod(obj.hashCode(), obj.getClass().getName(),method);
		str+="<p>"+"<s><t>String</t><v>"+info+"</v></s>";
		str+="</p></i>\n";
		return str;
	}
	
	public String write(Object obj){
		String method=methodData();
		String str="<i>\n";
		str+=formatIdClassMethod(obj.hashCode(), obj.getClass().getName(),method);
		str+="<p></p></i>";
		return str;
	}
	
	public String formatIdClassMethod(int id, String sclass, String smethod ){
		String str="";
		str+="<I>"+id+"</I>";
		str+="<c>"+sclass+"</c>";
		str+="<m>"+smethod+"</m>"; 
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
			if (methodName.compareTo("<init>")==0){
				methodName=getMethodName(className);
			}
			//System.out.println("Trace.methodData ---> Class that called :"+
			//							  className+" in the Method :"+methodName);								  
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
