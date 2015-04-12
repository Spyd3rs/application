/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.beans;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.gainsight.hackathon.tripAdvisor.utils.DateAsUTCJsonSerializer;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class ReviewBean {

	private String hotelURL;
	private String hotelId;
	private Float lowestHotelPrice;
	private Float highestHotelPrice;
	private Float hotelOverallRating;
	private HotelAddressCoordinate hotelAddressCoordinate;
	
	private String[] content;
	@JsonSerialize(using=DateAsUTCJsonSerializer.class)
	private Date timestamp;
	private String reviewId;
	private String author;
	
	private RatingsBean ratings;
	
	public RatingsBean getRatings() {
		return ratings;
	}
	public void setRatings(RatingsBean ratings) {
		this.ratings = ratings;
	}
	private SentimentAnalysisAspectBean sentimentAnalysisAspects;
	
	public String getHotelURL() {
		return hotelURL;
	}
	public void setHotelURL(String hotelURL) {
		this.hotelURL = hotelURL;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public Float getLowestHotelPrice() {
		return lowestHotelPrice;
	}
	public void setLowestHotelPrice(Float lowestHotelPrice) {
		this.lowestHotelPrice = lowestHotelPrice;
	}
	public Float getHighestHotelPrice() {
		return highestHotelPrice;
	}
	public void setHighestHotelPrice(Float highestHotelPrice) {
		this.highestHotelPrice = highestHotelPrice;
	}
	public Float getHotelOverallRating() {
		return hotelOverallRating;
	}
	public void setHotelOverallRating(Float hotelOverallRating) {
		this.hotelOverallRating = hotelOverallRating;
	}
	public String[] getContent() {
		return content;
	}
	public void setContent(String[] content) {
		this.content = content;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public HotelAddressCoordinate getHotelAddressCoordinate() {
		return hotelAddressCoordinate;
	}
	public void setHotelAddressCoordinate(HotelAddressCoordinate hotelAddressCoordinate) {
		this.hotelAddressCoordinate = hotelAddressCoordinate;
	}
	public SentimentAnalysisAspectBean getSentimentAnalysisAspects() {
		return sentimentAnalysisAspects;
	}
	public void setSentimentAnalysisAspects(
			SentimentAnalysisAspectBean sentimentAnalysisAspects) {
		this.sentimentAnalysisAspects = sentimentAnalysisAspects;
	}
	@Override
	public String toString() {
		return "ReviewBean [hotelURL=" + hotelURL + ", hotelId=" + hotelId
				+ ", lowestHotelPrice=" + lowestHotelPrice
				+ ", highestHotelPrice=" + highestHotelPrice
				+ ", hotelOverallRating=" + hotelOverallRating
				+ ", hotelAddressCoordinate=" + hotelAddressCoordinate
				+ ", content=" + content + ", timestamp=" + timestamp
				+ ", reviewId=" + reviewId + ", author=" + author
				+ ", ratings=" + ratings + ", sentimentAnalysisAspects="
				+ sentimentAnalysisAspects + "]";
	}
	
	
}
