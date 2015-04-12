package com.hoteladvisor.tokenizer;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.hoteladvisor.mapper.AspectMapper;
import com.hoteladvisor.pojo.AspectWithPolarity;
import com.hoteladvisor.pojo.Aspects;
import com.hoteladvisor.sentiment.analyzer.SentimentAnalyzerPythonConnector;

public class AspectExtractor {

	AspectMapper mapper = new AspectMapper();
	SentimentAnalyzerPythonConnector connector = new SentimentAnalyzerPythonConnector();

	public String extractSentimentWithAspects(String text) {

		StringTokenizer stt = new StringTokenizer(text, ".");

		Gson gson = new Gson();

		Set<AspectWithPolarity> aspects = new HashSet<AspectWithPolarity>();

		while (stt.hasMoreTokens()) {

			String splittedText = stt.nextToken();
			Integer sentiment = null;
			AspectWithPolarity aspect = null;

			StringTokenizer st = new StringTokenizer(splittedText, " ");

			while (st.hasMoreTokens()) {

				String token = st.nextToken();

				if (mapper.isTokenMeaningful(token)) {

					aspect = new AspectWithPolarity();
					aspect.setAspect(token.toLowerCase());
					if (sentiment == null) {
						sentiment = getSentiment(splittedText);
					} else {
						aspect.setPolarity(sentiment);
						aspects.add(aspect);

					}
				}

			}
		}

		if (aspects.size() > 0) {

			// return gson.toJson(new Aspects(aspects));
			return toJson(aspects);

		}

		return "";

	}

	public String toJson(Set<AspectWithPolarity> aspects) {
		StringBuffer jsonBuilder = new StringBuffer();
		jsonBuilder.append("{\"aspects\":{");
		for (AspectWithPolarity aspectWithPolarity : aspects) {
			if (aspectWithPolarity.getAspect() != null) {

				if (jsonBuilder.length() > 14) {
					jsonBuilder.append(",");
				}

				jsonBuilder.append("\"" + aspectWithPolarity.getAspect()
						+ "\":" + aspectWithPolarity.getPolarity());
			}
		}

		jsonBuilder.append("}}");

		if (jsonBuilder.length() > 16) {
			return jsonBuilder.toString();
		}

		return null;
	}

	public Integer getSentiment(String text) {
		System.out.println("timestamp start for getSentiment="
				+ System.currentTimeMillis());
		String polarityString = connector.callScript(text.replaceAll(
				"[^a-zA-Z0-9-_*., ]", " "));
		System.out.println("timestamp end for getSentiment="
				+ System.currentTimeMillis());
		Double polarity = Double.parseDouble(polarityString.substring(0,
				polarityString.indexOf('\n')));
		if (polarity > 0.0) {

			return 1;

		} else if (polarity == 0.0) {

			return 0;
		} else {
			return -1;
		}

	}

}
