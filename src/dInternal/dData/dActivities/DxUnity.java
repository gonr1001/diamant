package dInternal.dData.dActivities;

import java.util.Iterator;
import java.util.Vector;

import dInternal.dData.DxResource;

public class DxUnity extends DxResource {
	private static long _lUniqueKey = 1;

	DxSetOfAssignements _dxsoassAssign;

	Vector<String> _vsPreferFunction;

	int _nDuration;

	boolean _bPermanent, _bAssigned;

	public DxUnity(String sUnityName) {
		super(_lUniqueKey++, sUnityName);
		_dxsoassAssign = new DxSetOfAssignements();
		_vsPreferFunction = new Vector<String>();

		_nDuration = 60;
		_bPermanent = false;
		_bAssigned = false;
	}

	public void setDuration(int nDuration) {
		_nDuration = nDuration;

	}

	public void addAssignment(DxAssignement dxassAssign) {
		_dxsoassAssign.addAssignement(dxassAssign);

	}

	public Iterator getAssignementsIterator() {
		return _dxsoassAssign.iterator();
	}

	public void addPreferFunctionRoom(String sRoomType) {
		_vsPreferFunction.add(sRoomType);
	}

	public void setPermanent(boolean bPerm) {
		_bPermanent = bPerm;

	}

	public void setAssign(boolean bAssign) {
		_bAssigned = bAssign;
	}

	public int getAssignementCount() {
		return _dxsoassAssign.size();
	}

	public DxAssignement getAssignement(String sAssignementName) {
		return _dxsoassAssign.getAssignement(sAssignementName);
	}

	public DxAssignement getAssignement(long lAssignementKey) {
		return _dxsoassAssign.getAssignement(lAssignementKey);
	}
}
