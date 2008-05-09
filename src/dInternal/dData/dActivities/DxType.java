package dInternal.dData.dActivities;

import dInternal.dData.DxResource;

public class DxType extends DxResource {
	private static long _lUniqueKey=1;
	private DxSetOfSections _dxsosecSections;

	public DxType(String sTypeName) {
		super(_lUniqueKey++,sTypeName);
		_dxsosecSections = new DxSetOfSections();
	}

	public void addSection(DxSection dxsSection) {
		_dxsosecSections.addSection(dxsSection);
	}

	public int getSectionCount() {
		return _dxsosecSections.size();
	}

	public DxSection getSection(String sSectionName) {
		return _dxsosecSections.getSection(sSectionName);
	}
	
	public DxSection getSection(long lSectionKey) {
		return _dxsosecSections.getSection(lSectionKey);
	}

}
