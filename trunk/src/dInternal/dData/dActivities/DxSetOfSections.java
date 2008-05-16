package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfSections extends DxSetOfResources {

	@Override
	protected DxResource findEquivalent(DxResource dxrSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		// TODO Auto-generated method stub

	}

	public void addSection(DxSection dxsSection) {
		this.addResource(dxsSection);
		
	}

	public DxSection getSection(String sSectionName) {
		return (DxSection)this.getResource(sSectionName);
	}

	public DxSection getSection(long lSectionKey) {
		return (DxSection)this.getResource(lSectionKey);
	}

}
