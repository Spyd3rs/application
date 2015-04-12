/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.beans;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class SentimentAnalysisAspectBean {

	private Integer hotel;
	private Integer stay;
	private Integer staff;
	private Integer dining;
	private Integer food;
	private Integer pool;
	private Integer price;
	private Integer location;
	private Integer room;
	private Integer bathroom;
	
	public Integer getHotel() {
		return hotel;
	}
	public void setHotel(Integer hotel) {
		this.hotel = hotel;
	}
	public Integer getStay() {
		return stay;
	}
	public void setStay(Integer stay) {
		this.stay = stay;
	}
	public Integer getStaff() {
		return staff;
	}
	public void setStaff(Integer staff) {
		this.staff = staff;
	}
	public Integer getDining() {
		return dining;
	}
	public void setDining(Integer dining) {
		this.dining = dining;
	}
	public Integer getFood() {
		return food;
	}
	public void setFood(Integer food) {
		this.food = food;
	}
	public Integer getPool() {
		return pool;
	}
	public void setPool(Integer pool) {
		this.pool = pool;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	public Integer getBathroom() {
		return bathroom;
	}
	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}
	@Override
	public String toString() {
		return "SentimentAnalysisAspectBean [hotel=" + hotel + ", stay=" + stay
				+ ", staff=" + staff + ", dining=" + dining + ", food=" + food
				+ ", pool=" + pool + ", price=" + price + ", location="
				+ location + ", room=" + room + ", bathroom=" + bathroom + "]";
	}
	
	
}
