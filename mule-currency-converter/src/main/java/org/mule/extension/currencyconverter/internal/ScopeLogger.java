package org.mule.extension.currencyconverter.internal;

import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.route.Chain;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScopeLogger {

	public void logScope(Chain operations, CompletionCallback<Object, Object> callback) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		
		System.out.println("Components Entered at " + dateFormat.format(new Date()) );		
		operations.process(				
				result -> {
					System.out.println("Scope ended at " + dateFormat.format(new Date()));
					System.out.println("RESULT: " + result.getOutput());
					callback.success(result);
				},
				(error, previous) -> {
					System.out.println("Scope ended with error at " + dateFormat.format(new Date()));
					System.out.println(error.getMessage());
					callback.error(error);
				}
				);
	}
	
}
