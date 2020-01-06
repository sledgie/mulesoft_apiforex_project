package org.mule.extension.scopelogger.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.route.Chain;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class ScopeLoggerOperations {

	private static final Logger logger = LoggerFactory.getLogger(ScopeLoggerOperations.class);
  
	
	public void logScope(@DisplayName("Correlation ID") @Optional(defaultValue="n/a") String correlationID,
			@DisplayName("Project Name") String projectName,
			Chain operations, CompletionCallback<Object, Object> callback) {			
		StringBuilder log = new StringBuilder();
		DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
		Date startDate = new Date();
		log.append("\nStart Scope component log at : " + dtFormat.format(startDate));
		log.append("\nProject Name: " + projectName);		
		log.append("\nCorrelation ID: " + correlationID);
		logger.info(log.toString());
		log.delete(0, log.length());
		operations.process(
				result -> {
					Date endDate = new Date();
					log.append("\nEnd Scope Component log at: " + dtFormat.format(endDate));
					
					
					long timeDiff = (endDate.getTime() - startDate.getTime())/1000;
					//log.append("\nEnd Time: " + endDate.getTime());
					//log.append("\nStart Time: " + startDate.getTime());
					log.append("\nElapsed Time: " + timeDiff + " seconds.");
					logger.info(log.toString());
					callback.success(result);
				},
				(error, previous) -> {
					Date errorDate = new Date();
					log.append("\nEnd Scope Component With Error log at: " + dtFormat.format(errorDate));
					long timeDiff = (errorDate.getTime() - startDate.getTime())/1000;
					log.append("\nElapsed Time: " + timeDiff + " seconds.");
					logger.info(log.toString());
					callback.error(error);
				}				
				);
	}
}
