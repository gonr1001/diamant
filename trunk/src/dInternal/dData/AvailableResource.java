/**
 * Created on 7 juillet 2006
 * 
 * 
 * Title: AvailableResource.java 
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 */

package dInternal.dData;

/**
 * Nicolas Calderon
 * 
 * Description: AvailableResource is an interface used to:
 * <p>
 * Regroup all available resource and provide them with stadard acces method
 * such as set and get availability.
 * <p>
 * 
 */
public interface AvailableResource {
    public DxAvailability getAvailability();
    public void setAvailability(DxAvailability dxaNew);
}
