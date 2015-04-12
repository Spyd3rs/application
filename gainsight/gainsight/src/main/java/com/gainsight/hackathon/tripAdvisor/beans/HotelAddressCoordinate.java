/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class HotelAddressCoordinate {

	private Float latitude;
	private Float longitude;
	
	@JsonProperty("lat")
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	@JsonProperty("long")
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "HotelAddressCoordinate [latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
	
	
}
