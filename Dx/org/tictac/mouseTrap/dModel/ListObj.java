/*
 * Created on 2004-09-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.tictac.mouseTrap.dModel;
import java.util.Vector;
import java.util.Iterator;
/**
 * @author garr2701
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListObj {
	Vector list = new Vector();
	
	public Object found(int id){
		Object obj=null;
		Iterator it=list.listIterator();
		while(obj==null && it.hasNext()){
			ObjectReg tmp=(ObjectReg)it.next();
			if (tmp.getId()==id){
				obj=tmp.getObj();
			}
		}
		return obj;
	}
	
	public void add(ObjectReg obj){
		list.add(obj);
	}
	
	public void add(int id, Object obj){
		ObjectReg tmp=new ObjectReg(id, obj);
		list.add(tmp);
	}
	
}
