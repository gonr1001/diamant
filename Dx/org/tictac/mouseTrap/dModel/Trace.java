/*
 * Created on 2004-09-23
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
public class Trace {
	public String write(Object obj, String method, Object info){
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n"+"<simple>\n<type>String</type>\n<value>"+info+"</value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	public String write(Object obj, String method, int info){
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n"+"<simple>\n<type>String</type>\n<value>"+info+"</value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	public String write(Object obj, String method){
		String str="";
		str+="<instruction>\n<Id>"+obj.hashCode()+"</Id>\n";
		str+="<class>"+obj.getClass().getName()+"</class>\n";
		str+="<method>"+method+"</method>\n"; 
		str+="<params>\n<simple>\n<type></type>\n<value></value>\n</simple>\n";
		str+="</params>\n</instruction>\n";
		return str;
	}
	
}
