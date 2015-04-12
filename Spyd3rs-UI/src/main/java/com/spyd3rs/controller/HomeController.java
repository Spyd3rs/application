package com.spyd3rs.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
	
	final String ES_HOST = "http://localhost:9200/hackathon-gainsight-tripadvisor/reviews/_search";
	
	@RequestMapping("/home")
	public String showHomePage(Model model) {
		return "home";
	}

	@RequestMapping("/analysis")
	public String showAnalysisPage(Model model) {
		return "analysis";
	}

	@RequestMapping("/charts")
	public String showChartsPage(Model model) {
		return "charts";
	}

	@RequestMapping("/positive_aspects")
	public String showPositiveAspects(Model model) {
		return "positiveaspects";
	}
	

	@RequestMapping("/negative_aspects")
	public String showNegativeAspects(Model model) {
		return "negaspects";
	}
	

	@RequestMapping("/neutral_aspects")
	public String showNeutralAspects(Model model) {
		return "neutaspects";
	}
	
	@RequestMapping("/analysis_data/{hotel_id}")
	@ResponseBody
	public String showChartsById(@PathVariable("hotel_id") String hotelId,
			HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// RestTemplate restTemplate = new RestTemplate();
		// String result =
		// restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}",
		// String.class, "42", "21");
		String result = "{\"aspects\":[{\"aspect\":\"presentation\",\"polarity\":1},{\"aspect\":\"hotel\",\"polarity\":-1},{\"aspect\":\"staff\",\"polarity\":-1},{\"aspect\":\"pool\",\"polarity\":0}]}";
		return result;
	}

	@RequestMapping("/analysis_joins")
	@ResponseBody
	public String showChartsByAspects(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		RestTemplate restTemplate = new RestTemplate();
		// String result =
		// restTemplate.postForObject("http://example.com/hotels/{hotel}/bookings/{booking}",
		// String.class, "42", "21");
		// String result =
		// "{\"aspects\":[{\"aspect\":\"presentation\",\"polarity\":1},{\"aspect\":\"hotel\",\"polarity\":-1},{\"aspect\":\"staff\",\"polarity\":-1},{\"aspect\":\"pool\",\"polarity\":0}]}";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ES_HOST);
		System.out.println("Requesting : " + httppost.getURI());
		Map<String, Long> aspects = new HashMap<String, Long>();
		try {

			aspects.put("hotel", new Long(0));
			aspects.put("staff", new Long(0));
			aspects.put("stay", new Long(0));
			aspects.put("dining", new Long(0));
			aspects.put("food", new Long(0));
			aspects.put("pool", new Long(0));
			aspects.put("price", new Long(0));
			aspects.put("location", new Long(0));
			aspects.put("room", new Long(0));
			aspects.put("bathroom", new Long(0));

			Set<String> aspectKeys = aspects.keySet();
			for (String key : aspectKeys) {
				StringEntity entity = new StringEntity(
						"{\"size\":0,\"query\":{\"filtered\":{\"filter\":{\"range\":{\"hotelOverallRating\":{\"from\":\"4\",\"to\":\"5\"}}},\"filter\":{\"range\":{\"sentimentAnalysisAspects."
								+ key
								+ "\":{\"from\":\"0\",\"to\":\"10\"}}}}}}",
						"application/json", "ISO-8859-1");

				httppost.addHeader("Accept", "application/json");
				httppost.setEntity(entity);

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(httppost,
						responseHandler);

				System.out.println("responseBody : " + responseBody);
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(responseBody);
				Long hits = (Long) ((JSONObject) object.get("hits"))
						.get("total");
				System.out.println(hits);
				aspects.put(key, hits);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Set<String> keys = aspects.keySet();
		for (String key : keys) {
			if (sb.length() > 2) {
				sb.append(",");
			}
			sb.append("{\"name\":\"" + key + "\",\"y\":" + aspects.get(key)+"}");
		}
		sb.append("]");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	@RequestMapping("/neut_aspects")
	@ResponseBody
	public String showChartsByNeutralAspects(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		RestTemplate restTemplate = new RestTemplate();
		// String result =
		// restTemplate.postForObject("http://example.com/hotels/{hotel}/bookings/{booking}",
		// String.class, "42", "21");
		// String result =
		// "{\"aspects\":[{\"aspect\":\"presentation\",\"polarity\":1},{\"aspect\":\"hotel\",\"polarity\":-1},{\"aspect\":\"staff\",\"polarity\":-1},{\"aspect\":\"pool\",\"polarity\":0}]}";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ES_HOST);
		System.out.println("Requesting : " + httppost.getURI());
		Map<String, Long> aspects = new HashMap<String, Long>();
		try {

			aspects.put("hotel", new Long(0));
			aspects.put("staff", new Long(0));
			aspects.put("stay", new Long(0));
			aspects.put("dining", new Long(0));
			aspects.put("food", new Long(0));
			aspects.put("pool", new Long(0));
			aspects.put("price", new Long(0));
			aspects.put("location", new Long(0));
			aspects.put("room", new Long(0));
			aspects.put("bathroom", new Long(0));

			Set<String> aspectKeys = aspects.keySet();
			for (String key : aspectKeys) {
				StringEntity entity = new StringEntity(
						"{\"size\":0,\"query\":{\"filtered\":{\"filter\":{\"range\":{\"sentimentAnalysisAspects."
								+ key
								+ "\":{\"from\":\"-10\",\"to\":\"10\"}}}}}}",
						"application/json", "ISO-8859-1");

				httppost.addHeader("Accept", "application/json");
				httppost.setEntity(entity);

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(httppost,
						responseHandler);

				System.out.println("responseBody : " + responseBody);
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(responseBody);
				Long hits = (Long) ((JSONObject) object.get("hits"))
						.get("total");
				System.out.println(hits);
				aspects.put(key, hits);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Set<String> keys = aspects.keySet();
		for (String key : keys) {
			if (sb.length() > 2) {
				sb.append(",");
			}
			sb.append("{\"name\":\"" + key + "\",\"y\":" + aspects.get(key)+"}");
		}
		sb.append("]");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	@RequestMapping("/neg_aspects")
	@ResponseBody
	public String showChartsByNegAspects(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		RestTemplate restTemplate = new RestTemplate();
		// String result =
		// restTemplate.postForObject("http://example.com/hotels/{hotel}/bookings/{booking}",
		// String.class, "42", "21");
		// String result =
		// "{\"aspects\":[{\"aspect\":\"presentation\",\"polarity\":1},{\"aspect\":\"hotel\",\"polarity\":-1},{\"aspect\":\"staff\",\"polarity\":-1},{\"aspect\":\"pool\",\"polarity\":0}]}";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ES_HOST);
		System.out.println("Requesting : " + httppost.getURI());
		Map<String, Long> aspects = new HashMap<String, Long>();
		try {

			aspects.put("hotel", new Long(0));
			aspects.put("staff", new Long(0));
			aspects.put("stay", new Long(0));
			aspects.put("dining", new Long(0));
			aspects.put("food", new Long(0));
			aspects.put("pool", new Long(0));
			aspects.put("price", new Long(0));
			aspects.put("location", new Long(0));
			aspects.put("room", new Long(0));
			aspects.put("bathroom", new Long(0));

			Set<String> aspectKeys = aspects.keySet();
			for (String key : aspectKeys) {
				StringEntity entity = new StringEntity(
						"{\"size\":0,\"query\":{\"filtered\":{\"filter\":{\"range\":{\"hotelOverallRating\":{\"from\":\"3\",\"to\":\"4\"}}},\"filter\":{\"range\":{\"sentimentAnalysisAspects."
								+ key
								+ "\":{\"from\":\"-10\",\"to\":\"0\"}}}}}}",
						"application/json", "ISO-8859-1");

				httppost.addHeader("Accept", "application/json");
				httppost.setEntity(entity);

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(httppost,
						responseHandler);

				System.out.println("responseBody : " + responseBody);
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(responseBody);
				Long hits = (Long) ((JSONObject) object.get("hits"))
						.get("total");
				System.out.println(hits);
				aspects.put(key, hits);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Set<String> keys = aspects.keySet();
		for (String key : keys) {
			if (sb.length() > 2) {
				sb.append(",");
			}
			sb.append("{\"name\":\"" + key + "\",\"y\":" + aspects.get(key)+"}");
		}
		sb.append("]");
		System.out.println(sb.toString());
		return sb.toString();
	}
}
