/*
 * Created on 11 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal;

/**
 * @author gonr1001
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DataExchange {
	String _header;
	String _contents;

	/**
	 * 
	 */
	public DataExchange(String header, String contents) {
		_header = header;
		_contents = contents;		
	}
	
	public String getHeader(){
		return _header;
	}
	
	public String getContents(){
		return _contents;
	}
	
	public void setContents(String contents){
		_contents = contents;
	}
}
