package org.tictac.mouseTrap.dModel;

import java.util.Vector;
import java.util.List;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;

public class ExecuteMT {
	private String fileName;
	
	public ExecuteMT (String file){
		fileName=file;
	}

	public void execute (){
		try{
			ReadMT file = new ReadMT(fileName);
			Document doc=file.readFile();
			doTree(doc.getRootElement());	
		}catch (Exception e) {
			System.out.println("ExecuteMT.execute Caught Exception: " + e.getMessage());
		}	
	}			
				
	public void doTree(Element root){
		String sclass;
		String smethod;
		Vector sparams = new Vector();
		Object prevobj=null;
		try{
			List instructions = root.getChildren("instruction");
			System.out.println("This log have "+ instructions.size() +" registered");
			Iterator i = instructions.iterator();
			while (i.hasNext()) {
				Element instruction = (Element) i.next();
				sclass = instruction.getChild("class").getText();
				smethod = instruction.getChild("method").getText(); 
				System.out.print("\t" + sclass + " for " + smethod);
				List initParams = instruction.getChildren("params");				
				if (initParams.size()>0){
					Iterator n=initParams.iterator();
					while (n.hasNext()){
						Element nodeparams =(Element)n.next();
						List listparams = nodeparams.getChildren();
						System.out.println(" (it has " + listparams.size() + " params)");
						sparams=readParams(listparams);
					}
				}
				
				Instruction instructionToDo = new Instruction(sclass, smethod, sparams);				
				prevobj=instructionToDo.doIt(prevobj);
	  		}
		}catch (Exception e) {
			System.out.println("ExecuteMT.doTree Caught Exception: " + e.getMessage());
		}
	}

	public Vector readParams(List initParams){
		Vector vectorParams=new Vector();
		try{
			Iterator i = initParams.iterator();
			while (i.hasNext()) {
				Element param = (Element) i.next();
				String stype = param.getName();
				if (stype.compareTo("simple")==0){
					vectorParams.add(simpleParam(param));					
				}else if(stype.compareTo("complex")==0){
					String sclass = param.getChild("class").getText();
					List subparams = param.getChild("params").getChildren();
					System.out.println("\t" + sclass+" has " + subparams.size() + " params");
					vectorParams.add(complexParam(sclass,readParams(subparams)));
				}
			}
		}catch (Exception e) {
			System.out.println("ExecuteMT.readParams Caught Exception: " + e.getMessage());
		}
		return vectorParams;
	}	
	
	public Object complexParam( String sclass, Vector subparams){
		Object newobj=null;
		try{
			System.out.println("Complex.Type:" + sclass + " Value:" + subparams.toString()); 
			//Constructor myc=(new MyConstructor()).getTheConstructor(type, value);
			newobj=(new MyObject()).createObject(sclass, subparams );
		}catch (Exception e) {
			System.out.println("ExecuteMT.simpleParam Caught Exception: " + e.getMessage());
		}
		return newobj;
	}
	
	
	public Object simpleParam(Element simple){
		Object newobj=null;
		try{
			String type=simple.getChild("type").getText();
			String value=simple.getChild("value").getText();
			System.out.println("Simple.Type:"+type+" Value:"+value); 
			//Constructor myc=(new MyConstructor()).getTheConstructor(type, value);
			newobj=(new MyObject()).createObject(type, value );
		}catch (Exception e) {
			System.out.println("ExecuteMT.simpleParam Caught Exception: " + e.getMessage());
		}
		return newobj;		
	}
}
