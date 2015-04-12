package com.hoteladvisor.sentiment.analyzer;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.List;

public class SentimentAnalyzerPythonConnector {

	private static final String path = "/home/grijesh/";

	public String callScript(String text) {
		ProcessBuilder scriptBuilder = null;
		BufferedInputStream bin = null;
		Process process = null;
		String response = "";
		String scriptName = "senti.py";
		try {

			byte[] by = new byte[1024];

			List<String> commands = new LinkedList<String>();
			commands.add("python");
			commands.add(path + scriptName);
			commands.add(text);

			String[] pythonCommand = commands.toArray(new String[0]);
			System.out.println(commands.toString());

			scriptBuilder = new ProcessBuilder(pythonCommand);
			process = scriptBuilder.start();
			bin = (BufferedInputStream) process.getInputStream();
			int i = 0;
			while ((i = bin.read(by)) != -1) {
				String tempResult = new String(by, 0, i);
				response = response + tempResult;
			}
			System.out.println("response="+response);
			process.destroy();
			bin.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}