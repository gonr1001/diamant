package dInternal.dData.dActivities;

import dInternal.dData.DxResource;

public class DxActivity extends DxResource {
	private static long _lUniqueKey = 1;

	private DxSetOfTypes _dxsotTypes;

	private boolean _bVisibility;

//	private String _sIdemLine;

	private int _nCapacity;

	public DxActivity(String sActivityName) {
		super(_lUniqueKey++, sActivityName);
		_dxsotTypes = new DxSetOfTypes();
//		_sIdemLine = "";
		_bVisibility = false;
		_nCapacity = 0;
	}

	public void addType(DxType dxtType) {
		_dxsotTypes.addType(dxtType);
	}

	public void setVisibility(boolean bVisibility) {
		_bVisibility = bVisibility;
	}

	public boolean getVisibility() {
		return _bVisibility;
	}

//	public void setIdemLine(String sIdemLine) {
//		_sIdemLine = sIdemLine;
//
//	}

	public DxType getType(String sTypeName) {
		return _dxsotTypes.getType(sTypeName);
	}

	public DxType getType(long lTypeKey) {
		return _dxsotTypes.getType(lTypeKey);
	}

	public int getTypeCount() {
		return _dxsotTypes.size();
	}

	public void setCapacity(int nCapacity) {
		_nCapacity = nCapacity;
	}

	public int getCapacity() {
		return _nCapacity;
	}
	
	public String toString(){
		return this.getName();
	}

}
