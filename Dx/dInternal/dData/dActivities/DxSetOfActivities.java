package dInternal.dData.dActivities;

import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

public class DxSetOfActivities extends DxSetOfResources {

	protected DxResource findEquivalent(DxResource dxrSearch) {
		return this.getActivity(dxrSearch.getName());
	}

	protected void merge(DxResource dxrModify, DxResource dxrNew) {
		// TODO Verify behavior of merge for activities

	}

	public DxActivity getActivity(String sActivitySiteName) {
		return (DxActivity)this.getResource(sActivitySiteName);
	}

	public DxActivity getActivity(Long lActivitySiteKey) {
		return (DxActivity)this.getResource(lActivitySiteKey);
	}

	public void addActivity(DxActivity dxaActivity) {
		this.addResource(dxaActivity);
	}

	public void addSetOfActivities(DxSetOfActivities dxsoaSetOfActivities) {
		this.addSetOfResources(dxsoaSetOfActivities);
	}

}
