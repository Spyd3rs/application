/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.beans;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class RatingsBean {

	private Integer service;
	private Integer businessService;
	private Integer cleanliness;
	private Integer checkIn;
	private Float overall;
	private Integer value;
	private Integer rooms;
	private Integer location;
	public Integer getService() {
		return service;
	}
	public void setService(Integer service) {
		this.service = service;
	}
	public Integer getBusinessService() {
		return businessService;
	}
	public void setBusinessService(Integer businessService) {
		this.businessService = businessService;
	}
	public Integer getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(Integer cleanliness) {
		this.cleanliness = cleanliness;
	}
	public Integer getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Integer checkIn) {
		this.checkIn = checkIn;
	}
	public Float getOverall() {
		return overall;
	}
	public void setOverall(Float overall) {
		this.overall = overall;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "RatingsBean [service=" + service + ", businessService="
				+ businessService + ", cleanliness=" + cleanliness
				+ ", checkIn=" + checkIn + ", overall=" + overall + ", value="
				+ value + ", rooms=" + rooms + ", location=" + location + "]";
	}
	
	
	
}
