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
	
	private boolean lastCycleIsFinished = true;	
	private static final Integer threadPoolSize = 2;

	private static ExecutorService startExecution(MediaCollectors mediaCollectors){
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
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
		System.out.println("Enter startCycle");
		if(lastCycleIsFinished){
			System.out.println("startCycle");
			lastCycleIsFinished = false;
			ExecutorService executor = startExecution(mediaCollectors); 
			while(!executor.isTerminated()){
				Thread.sleep(1000);
			}
			
			lastCycleIsFinished = true;
			System.out.println("Cycle finished");
		}
	}
}
