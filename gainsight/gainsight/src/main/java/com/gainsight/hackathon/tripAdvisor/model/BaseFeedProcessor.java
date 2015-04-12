/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.model;

import java.io.Reader;

/**
 * @author Kumaran Gunamurthy
 *
 */
public interface BaseFeedProcessor {

	public boolean process(Reader feedReader);
	
}
