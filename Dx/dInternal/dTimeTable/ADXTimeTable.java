package dInternal.dTimeTable;
import java.util.Vector;

public class ADXTimeTable {

	public Vector<ACycle> _DXTimeTable;
	
	public ADXTimeTable() {
		_DXTimeTable = new Vector<ACycle>();
	}

	public String ToString(){
		StringBuffer sb = new StringBuffer("Mon DXTimeTable est le suivant: ");
		for (int i = 0; i < _DXTimeTable.size(); i++){
		sb.append(_DXTimeTable.getClass());}
		return sb.toString();
	}
}
