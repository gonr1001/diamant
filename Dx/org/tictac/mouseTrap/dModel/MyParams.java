package org.tictac.mouseTrap.dModel;

import java.util.Vector;

public class MyParams {
	
	public Object[] getTheParams(Vector params){
		Object newval[] = new Object[params.size()];
		try{
			for (int n=0 ;n < params.size(); n++){
				newval[n] = params.get(n);
			}
		}catch (Exception e) {
			System.err.println("MyParams.getTheParams Caught Exception: " + e.getMessage());
		}
		return newval;
	}
}
