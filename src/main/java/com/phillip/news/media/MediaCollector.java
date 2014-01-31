package com.phillip.news.media;

import java.util.List;

public interface MediaCollector {
	void addMediaCollectionTask(MediaCollectionTask mediaCollectionTask);
	List<MediaCollectionTask> getMediaCollectionTasks();
}
