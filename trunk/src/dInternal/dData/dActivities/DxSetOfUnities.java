package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfUnities extends DxSetOfResources {

	@Override
	protected DxResource findEquivalent(DxResource dxrSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		// TODO Auto-generated method stub

	}

	public void addUnity(DxUnity dxuUnity) {
		this.addResource(dxuUnity);
		
	}

	public DxUnity getUnity(String sUnityName) {
		return (DxUnity)this.getResource(sUnityName);
	}

	public DxUnity getUnity(long lUnityKey) {
		return (DxUnity)this.getResource(lUnityKey);
	}

}
