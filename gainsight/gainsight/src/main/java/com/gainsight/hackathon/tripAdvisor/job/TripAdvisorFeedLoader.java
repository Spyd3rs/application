/**
 * 
 */
package com.gainsight.hackathon.tripAdvisor.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

import com.gainsight.hackathon.tripAdvisor.model.TripAdvisorJsonFeedProcessor;

/**
 * @author Kumaran Gunamurthy
 *
 */
public class TripAdvisorFeedLoader {
	
	private static Logger logger = Logger.getLogger(TripAdvisorFeedLoader.class);

	public static void main(String args[]) {
		
		File landingZoneDir = new File("C:\\Users\\KG\\Desktop\\Hackathon"); 
		File[] fileList = null;
		if(landingZoneDir.exists()) {
			fileList = landingZoneDir.listFiles(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
					  boolean isAMatch = new File(current, name).isFile(); //Check if its a File
					  if(isAMatch) {
						  isAMatch = name.matches(".*.json");
					  }
					  return isAMatch;
				  }
				});
		} else {
			logger.error("Landing Zone - " + landingZoneDir.getAbsolutePath() + " does not exist.");
		}
		
		for(int i=0; i<fileList.length; i++) {
			
			File file = fileList[i];
			logger.info("Processing file - " + file.getName());
			try {
				FileReader inputFeedRead = new FileReader(file);
				
				TripAdvisorJsonFeedProcessor taFeedProcessor = new TripAdvisorJsonFeedProcessor();
				
				taFeedProcessor.process(inputFeedRead);
				
				File bkpDir = new File("C:\\Users\\KG\\Desktop\\Hackathon\\finalBkp");
				file.renameTo(new File(bkpDir, file.getName()));
				
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException occured - " + e.getMessage(), e);
			}
		}
		
	}
}
