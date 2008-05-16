package dInternal.dData.dActivities;

import java.util.Iterator;

import dInternal.dData.DxResource;

public class DxSection extends DxResource {
	private static long _lUniqueKey = 1;
	
	private DxSetOfUnities _dxsouUnities;
	
	private int _capacityLimit;

	public DxSection(String sSectionName) {
		super(_lUniqueKey++,sSectionName);
		_dxsouUnities = new DxSetOfUnities();
	}

	public void addUnity(DxUnity dxuUnity) {
		_dxsouUnities.addUnity(dxuUnity);
	}

	public Iterator getUnitiesIterator() {
		return _dxsouUnities.iterator();
	}

	public int getUnityCount() {
		return _dxsouUnities.size();
	}

	public DxUnity getUnity(String sUnityName) {
		return _dxsouUnities.getUnity(sUnityName);
	}
	
	public DxUnity getUnity(long lUnityKey) {
		return _dxsouUnities.getUnity(lUnityKey);
	}

	/**
	 * 
	 * @param c
	 *            the capacity limit
	 */
	public void setCapacityLimit(int c) {
		_capacityLimit = c;
	}
	
	/**
	 * 
	 * 
	 */
	public int getCapacityLimit() {
		return _capacityLimit;
	}
}
