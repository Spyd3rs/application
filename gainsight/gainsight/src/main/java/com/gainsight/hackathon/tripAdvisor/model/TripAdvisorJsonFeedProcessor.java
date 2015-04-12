/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.model;

import io.searchbox.client.JestResult;

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gainsight.hackathon.tripAdvisor.beans.HotelAddressCoordinate;
import com.gainsight.hackathon.tripAdvisor.beans.RatingsBean;
import com.gainsight.hackathon.tripAdvisor.beans.ReviewBean;
import com.gainsight.hackathon.tripAdvisor.beans.SentimentAnalysisAspectBean;
import com.gainsight.hackathon.tripAdvisor.services.ElasticSearchJestClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class TripAdvisorJsonFeedProcessor implements BaseFeedProcessor {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private static int DEFAULT_RATING = -1;
	
	/* (non-Javadoc)
	 * @see com.gainsight.hackathon.tripAdvisor.model.BaseFeedProcessor#process(java.io.InputStream)
	 */
	@Override
	public boolean process(Reader feedReader) {
		
		JsonParser parser = new JsonParser();
		
		try {
			Object obj = parser.parse(feedReader);
            JsonObject hotelJsonObj =  (JsonObject) obj;
            
            JsonObject hotelInfoJsonObj = (JsonObject) hotelJsonObj.get("HotelInfo");
            
            String hotelURL 	= hotelInfoJsonObj.get("HotelURL").getAsString();
            String hotelId		= hotelInfoJsonObj.get("HotelID").getAsString();
            String hotelPriceStr= hotelInfoJsonObj.get("Price").getAsString().trim();
            
            Float lowestHotelPrice = null;
            Float highestHotelPrice = null;
            
            String[] priceStrArray = hotelPriceStr.split("[\\-]");
            if(StringUtils.isNotEmpty(priceStrArray[0])) {
            	 try {
            		 lowestHotelPrice = Float.parseFloat(priceStrArray[0]);
                 } catch (NumberFormatException e) {
                	 lowestHotelPrice = null;
                 }
            }
            
            if(priceStrArray.length != 1 && StringUtils.isNotEmpty(priceStrArray[1])) {
	           	try {
	           		highestHotelPrice = Float.parseFloat(priceStrArray[1]);
                } catch (NumberFormatException e) {
                	highestHotelPrice = lowestHotelPrice;
                }
           } else {
        	   highestHotelPrice = lowestHotelPrice;
           }
            
            JsonArray reviewsArray = hotelJsonObj.get("Reviews").getAsJsonArray();
            
            int noOfReviews = reviewsArray.size();
            
            float aggregateOverallRating = 0F;
            for(int i=0; i<noOfReviews; i++) {
            	
            	//JsonObject jsonObject = reviewsArray.get(i).getAsJsonObject();
            	//logger.info("reviewsArray - " + jsonObject.to);
            	aggregateOverallRating += reviewsArray.get(i).getAsJsonObject().get("Ratings").getAsJsonObject().get("Overall").getAsFloat();
            }
            
            //logger.info("aggregateOverallRating - " + aggregateOverallRating + "   noOfReviews - " + noOfReviews + "   " + (float)aggregateOverallRating/noOfReviews);
            
            Float hotelOverallRating = aggregateOverallRating/noOfReviews;
            
            String uid = null;
        	Pattern uidPattern = Pattern.compile("g[\\d]+-d[\\d]+");
        	Matcher uidMatcher = uidPattern.matcher(hotelURL);
        	if(uidMatcher.find()) {
        		uid = uidMatcher.group(0);
        	}
        	HotelAddressCoordinate hCoordinate = null;
        	if(uid != null) {
        		
        		JestResult jResult = new ElasticSearchJestClient().getDocument("hackathon_hotel_location_2", "location", uid);
        		if(jResult.isSucceeded()) {
        			hCoordinate = new HotelAddressCoordinate();
        			JsonObject sourceJsonObject = jResult.getJsonObject().get("_source").getAsJsonObject();
        			hCoordinate.setLatitude(sourceJsonObject.get("lat").getAsFloat());
            		hCoordinate.setLongitude(sourceJsonObject.get("long").getAsFloat());
        		}
        		
        	}
            
            
            List<ReviewBean> reviews = new ArrayList<ReviewBean>();
            
            for(int i=0; i<noOfReviews; i++) {
            	JsonObject reviewJsonObj = reviewsArray.get(i).getAsJsonObject();
            	
            	String content 		= reviewJsonObj.get("Content").getAsString();
            	String reviewId		= reviewJsonObj.get("ReviewID").getAsString();
            	String author		= reviewJsonObj.get("Author").getAsString();
            	String dateString	= reviewJsonObj.get("Date").getAsString();
            	
            	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            	Date timestamp;
				try {
					timestamp = sdf.parse(dateString);
				} catch (java.text.ParseException e) {
					timestamp = null;
				}
            	
            	JsonObject ratingsJsonObj 	= reviewJsonObj.get("Ratings").getAsJsonObject();
            	
            	int serviceRating;
				try {
					serviceRating = ratingsJsonObj.get("Service").getAsInt();
				} catch (NullPointerException e) {
					serviceRating = DEFAULT_RATING;
				}
            	
            	int businessServiceRating;
				try {
					businessServiceRating = ratingsJsonObj.get("Business service").getAsInt();
				} catch (NullPointerException e) {
					businessServiceRating = DEFAULT_RATING;
				}
				
            	int cleanlinessRating;
				try {
					cleanlinessRating = ratingsJsonObj.get("Cleanliness").getAsInt();
				} catch (NullPointerException e) {
					cleanlinessRating = DEFAULT_RATING;
				}
				
            	int checkInRating;
				try {
					checkInRating = ratingsJsonObj.get("Check in / front desk").getAsInt();
				} catch (NullPointerException e) {
					checkInRating = DEFAULT_RATING;
				}
				
            	Float overallRating;
				try {
					overallRating = ratingsJsonObj.get("Overall").getAsFloat();
				} catch (NullPointerException e) {
					overallRating = (float) DEFAULT_RATING;
				}
				
            	int valueRating;
				try {
					valueRating = ratingsJsonObj.get("Value").getAsInt();
				} catch (NullPointerException e) {
					valueRating = DEFAULT_RATING;
				}
				
            	int roomsRating;
				try {
					roomsRating = ratingsJsonObj.get("Rooms").getAsInt();
				} catch (NullPointerException e) {
					roomsRating = DEFAULT_RATING;
				}
				
            	int locationRating;
				try {
					locationRating = ratingsJsonObj.get("Location").getAsInt();
				} catch (NullPointerException e) {
					locationRating = DEFAULT_RATING;
				}
            	
				
				
            	ReviewBean reviewBean = new ReviewBean();
            	reviewBean.setHotelId(hotelId);
            	reviewBean.setLowestHotelPrice(lowestHotelPrice);
            	reviewBean.setHighestHotelPrice(highestHotelPrice);
            	reviewBean.setHotelURL(hotelURL);
            	reviewBean.setHotelOverallRating(hotelOverallRating);
            	
            	reviewBean.setContent(content.split("\\. "));
            	reviewBean.setTimestamp(timestamp);
            	reviewBean.setReviewId(reviewId);
            	reviewBean.setAuthor(author);
            	
            	RatingsBean ratingsBean = new RatingsBean();
            	ratingsBean.setService(serviceRating);
            	ratingsBean.setBusinessService(businessServiceRating);
            	ratingsBean.setCleanliness(cleanlinessRating);
            	ratingsBean.setCheckIn(checkInRating);
            	ratingsBean.setOverall(overallRating);
            	ratingsBean.setValue(valueRating);
            	ratingsBean.setRooms(roomsRating);
            	ratingsBean.setLocation(locationRating);
            	
            	reviewBean.setRatings(ratingsBean);
            	/*HotelAddressCoordinate hCoordinate = new HotelAddressCoordinate();
				hCoordinate.setLatitude(12.9900F);
				hCoordinate.setLongitude(77.7000F);*/
            	
            	reviewBean.setHotelAddressCoordinate(hCoordinate);
            	
            	JestResult jResult = new ElasticSearchJestClient().getDocument("hackathon-sentimentanalysis_2", "sa", hotelId + "_" + reviewId);
            	JsonElement aspectsJsonElement = null;
            	if(jResult.isSucceeded()) {
            		aspectsJsonElement = jResult.getJsonObject().get("_source").getAsJsonObject().get("aspects");
            	}
            	 
            	
            	Gson gson = new Gson();
            	SentimentAnalysisAspectBean saAspectBean = gson.fromJson(aspectsJsonElement, SentimentAnalysisAspectBean.class);
            	
            	//Map<String, Double> saAspectBean = gson.fromJson(aspectsJsonElement, HashMap.class);
            	
            	//logger.info("----------" + saAspectBean);
            	
            	reviewBean.setSentimentAnalysisAspects(saAspectBean);
            	
            	/*ObjectMapper objectMapper = new ObjectMapper();
				String docAsJsonString = objectMapper.writeValueAsString(reviewBean);
            	
            	logger.info(docAsJsonString);*/
            	
            	reviews.add(reviewBean);
            }

            ElasticSearchJestClient esJestClient = new ElasticSearchJestClient();
            esJestClient.indexDocument(reviews);
		} catch (Exception e) {
			logger.error("Exception occurred - " + e.getMessage(), e);
		} 
		return false;
	}

}
