package com.phillip.news.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Categories {
	public static final Category OTHER = new Category("other", null, null);
	
	public static final Category POLITICS = new Category("politics", null, null);
	public static final Category POLITICS_DOMESTIC = new Category("politics_domestic", null, null);
	public static final Category POLITICS_FOREIGN = new Category("politics_foreign", null, null);
	public static final Category POLITICS_EU = new Category("politics_eu", null, null);
	public static final Category POLITICS_WORLD = new Category("politics_world", null, null);
	
	public static final Category SPORTS = new Category("sports", null, null);
	public static final Category SPORTS_FOOTBALL = new Category("sports_football", null, null);
	public static final Category SPORTS_WINTERSPORTS = new Category("sports_wintersports", null, null);
	public static final Category SPORTS_MOTORSPORTS = new Category("sports_motorsports", null, null);
}
