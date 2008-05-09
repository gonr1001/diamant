package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfTypes extends DxSetOfResources {

	@Override
	protected DxResource findEquivalent(DxResource dxrSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		// TODO Auto-generated method stub

	}

	public void addType(DxType dxtType) {
		this.addResource(dxtType);
		
	}

	public DxType getType(String sTypeName) {
		return (DxType)this.getResource(sTypeName);
	}

	public DxType getType(long lTypeKey) {
		return (DxType)this.getResource(lTypeKey);
	}

}
