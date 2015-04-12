/**
 * 
 */
package com.hoteladvisor.elastic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Bulk.Builder;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class ElasticSearchJestClient {

	private static final String propertiesFile = "elasticSearch.properties";

	private static String elasticSearchServer;
	private static long batchSize;
	private static String indexName;
	private static String docType;

	private static JestClient jestClient;

	private static final long DEFAULT_BATCH_SIZE = 5000L;
	private static final int DEFAULT_CONN_TIMEOUT = 10000;
	private static final int DEFAULT_READ_TIMEOUT = 10000;

	static {
		try {
			InputStream inputStream = ElasticSearchJestClient.class
					.getClassLoader().getResourceAsStream(propertiesFile);
			Properties elasticSearchProperties = new Properties();
			elasticSearchProperties.load(inputStream);

			int connTimeout = 0;
			int readTimeOut = 0;

			/* Read properties */
			elasticSearchServer = elasticSearchProperties
					.getProperty("elasticsearch.server.url");
			indexName = elasticSearchProperties
					.getProperty("elasticsearch.index.name");
			docType = elasticSearchProperties
					.getProperty("elasticsearch.doc.type");
			try {
				batchSize = Long.parseLong(elasticSearchProperties
						.getProperty("elasticsearch.batch.size"));
			} catch (NumberFormatException e) {
				batchSize = DEFAULT_BATCH_SIZE;
			}
			try {
				connTimeout = Integer.parseInt(elasticSearchProperties
						.getProperty("elasticsearch.conn.timeout"));
			} catch (NumberFormatException e) {
				connTimeout = DEFAULT_CONN_TIMEOUT;
			}
			try {
				readTimeOut = Integer.parseInt(elasticSearchProperties
						.getProperty("elasticsearch.read.timeout"));
			} catch (NumberFormatException e) {
				readTimeOut = DEFAULT_READ_TIMEOUT;
			}

			HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(
					elasticSearchServer).multiThreaded(true)
					.connTimeout(connTimeout).readTimeout(readTimeOut).build();
			JestClientFactory jestClientFactory = new JestClientFactory();
			jestClientFactory.setHttpClientConfig(httpClientConfig);

			jestClient = jestClientFactory.getObject();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean indexDocument(String json, String id) {

		List<Index> indexList = new ArrayList<Index>();

		int documentCounter = 0;
		try {

			indexList.add(new Index.Builder(json).id((id)).build());

			if (++documentCounter % batchSize == 0) {
				Builder bulkIndexBuilder = new Bulk.Builder().defaultIndex(
						indexName).defaultType(docType);

				Bulk bulk = bulkIndexBuilder.addAction(indexList).build();
				JestResult jestResult = jestClient.execute(bulk);

				indexList = new ArrayList<Index>();

			}
			if (indexList.size() > 0) {
				Builder bulkIndexBuilder = new Bulk.Builder().defaultIndex(
						indexName).defaultType(docType);

				Bulk bulk = bulkIndexBuilder.addAction(indexList).build();
				JestResult jestResult = jestClient.execute(bulk);

			}
		} catch (Exception e) {

			return false;
		}

		return true;
	}
}
