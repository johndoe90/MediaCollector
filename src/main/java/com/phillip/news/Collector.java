package com.phillip.news;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.phillip.news.media.MediaCollectionTask;
import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.collector.MediaCollectors;

@Component
public class Collector {

	@Inject private MediaCollectors mediaCollectors;

	private static ExecutorService startExecution(MediaCollectors mediaCollectors){
		ExecutorService executor = Executors.newFixedThreadPool(mediaCollectors.getMediaCollectors().size());
		for(MediaCollector mediaCollector : mediaCollectors.getMediaCollectors()){
			for(MediaCollectionTask task : mediaCollector.getMediaCollectionTasks()){
				executor.execute(task);
			}
		}
		
		executor.shutdown();
		
		return executor;
	}
	
	@Scheduled(fixedRate = 600000)
	public void startCycle() throws Exception{
		System.out.print("\n\nSTARTING NEW CYCLE\n\n");
		
		ExecutorService executor = startExecution(mediaCollectors); 
		while(!executor.isTerminated()){
			Thread.sleep(1000);
		}

		System.out.print("\n\nCYCLE FINISHED\n\n");
	}
}
