package com.hoteladvisor.sentiment.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hoteladvisor.elastic.ElasticSearchJestClient;
import com.hoteladvisor.tokenizer.AspectExtractor;

public class HotelJSONReader {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		String line = null;
		AspectExtractor extractor = new AspectExtractor();
		ElasticSearchJestClient elasticClient = new ElasticSearchJestClient();
		// System.out.println(args[0]);
		BufferedReader br = null;

		File folder = new File(args[0]);
		// File folder = new File("C:/Users/UC178586/Desktop/gainsight/1");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {

			try {

				br = new BufferedReader(new FileReader(file));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {

				line = br.readLine();

			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					br.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			System.out.println(line);

			try {

				Object obj = parser.parse(line);
				JSONObject tripReviewDocument = (JSONObject) obj;

				String hotelId = (String) ((JSONObject) tripReviewDocument
						.get("HotelInfo")).get("HotelID");

				JSONArray reviews = (JSONArray) tripReviewDocument
						.get("Reviews");

				for (Integer i = 0; i < reviews.size(); i++) {

					JSONObject soloReview = (JSONObject) reviews.get(i);
					String reviewId = (String) (soloReview).get("ReviewID");
					String content = (String) (soloReview).get("Content");
					String timestamp = (String) (soloReview).get("Date");
					String sentimentWithAspects = extractor
							.extractSentimentWithAspects(content);
					String finalJSON = formBasicJSON(reviewId, hotelId,
							timestamp, sentimentWithAspects);
					System.out.println(finalJSON);
					System.out.println("timestamp start for indexDocument="
							+ System.currentTimeMillis());
					elasticClient.indexDocument(finalJSON, hotelId + "_"
							+ reviewId);

					System.out.println("timestamp end for indexDocument="
							+ System.currentTimeMillis());
				}

			} catch (ParseException pe) {
				pe.printStackTrace();

			}
			file.delete();
		}
	}

	public static String formBasicJSON(String reviewId, String hotelId,
			String timestamp, String sentimentWithAspectsString) {

		String sentimentForAddition = null;
		try {
			sentimentForAddition = sentimentWithAspectsString.substring(1,
					sentimentWithAspectsString.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {

		}
		if (sentimentForAddition != null) {
			return "{\"timestamp\":\"" + timestamp + "\" , \"HotelID\":\""
					+ hotelId + "\" , \"ReviewID\":\"" + reviewId + "\","
					+ sentimentForAddition + "}";
		} else {
			return "{\"timestamp\":\"" + timestamp + "\" ,\"HotelID\":\""
					+ hotelId + "\" , \"ReviewID\":\"" + reviewId + "}";

		}
	}
}
