package org.tictac.mouseTrap.dModel;

import java.lang.reflect.Method;
import java.util.Vector;
import java.lang.reflect.InvocationTargetException;

public class Instruction {
	private Class   _class;
	private Method 	_method;
	private Class  	_typeParams[];	
	private Object  _valueParams[];	
	private Class  	_typeRet ;
	private Object  _valueRet;

	public Instruction(String sclass, String smethod, Vector sparams){
		try{
			_class = (new MyClass()).getClass(sclass); //class
			_method = (new MyMethod()).getTheMethod(_class, smethod, sparams);//method and type
			_typeParams = _method.getParameterTypes();
			_typeRet = _method.getReturnType();
			if (sparams.size()>0){
			   _valueParams = (new MyParams()).getTheParams(sparams);
			}
		}catch (Exception e) {
			System.out.println("Instruction constructor Caught Exception: " + e.getMessage());	
		}
	}

	public Object doIt (Object prevobj){
		Object methobj=null;
		
		//		Precondition in public method
		//such as IllegalArgumentException, IndexOutOfBoundsException, or NullPointerException
		if (_class == null  || _method == null ){
			throw new NullPointerException("doIt.Illegal _class: " + 
						_class.getName() + "_method: "+_method.getName()+
						"_valueParams: "+_valueParams.toString());
		}
		try{
			System.out.println("---Réexécution: "+_method.toString());
			if (_valueParams!=null){
			    for (int i = 0; i <_valueParams.length; i++ ){
				     System.out.print(_valueParams[i].toString() + " ");
			    }
			}
			Vector v=new Vector();
			v.add(new Boolean("true"));
			if (    prevobj==null || 
				   (prevobj.getClass().getName()).compareTo(_class.getName()) !=0){
				methobj = (new MyObject()).createObject(_class.getName(),v);
			}else{
				methobj=prevobj;
			}
			
			Object retobj = _method.invoke(methobj, _valueParams);  //That's the execution
			
			if (retobj!=null){
				//Only if we have a return value
				Object retval = (new MyReturn()).getTheReturn(_typeRet,retobj.toString());
				System.out.println("Return:" + retval.toString());
			}
		}catch (IllegalAccessException e){
			System.out.println("Instruction.doIt Illegal Access Exception: " + e.getMessage());
		}catch (InvocationTargetException e) {
			System.out.println("Instruction.doIt Invocation Target Exception: " + e.getMessage());		
		}catch (Exception e) {
			System.out.println("Instruction.doIt Caught Exception: " + e.getMessage());		
		}
		return methobj;
	}
}
