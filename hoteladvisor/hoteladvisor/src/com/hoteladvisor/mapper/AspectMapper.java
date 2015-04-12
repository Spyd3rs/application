package com.hoteladvisor.mapper;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AspectMapper {

	static List<String> list;

	public boolean isTokenMeaningful(String token) {

		return list.contains(token.toLowerCase());

	}

	public AspectMapper() {
		BufferedReader br = null;

		// br = new BufferedReader(new FileReader(new File(
		// "hotel-related-terms-final.txt")));

		try {
			br = new BufferedReader(new InputStreamReader(AspectMapper.class
					.getClassLoader().getResourceAsStream(
							"hotel-related-terms-final.txt")));
			String line = br.readLine();

			if (list == null) {
				list = new ArrayList<String>();
			}

			while (line != null) {
				list.add(line);
				line = br.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				br.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
}
