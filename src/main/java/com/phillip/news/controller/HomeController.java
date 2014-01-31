package com.phillip.news.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.phillip.news.domain.Media;
import com.phillip.news.repository.MediaRepository;
import com.phillip.news.utils.ImageUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@Inject
	private MediaRepository mediaRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		/*Map<String, String> links = ImageUtils.buildImageTree("http://images02.kurier.at/schumacher-000_APA_VALDRIN+XHEMAJ.jpg/314.454");
		System.out.println(links);*/
		
		List<Media> media = mediaRepo.findAll();
		model.addAttribute("media", media);
		/*for(Media medium : media){
			System.out.println("id: " + medium.getId() + " / " + medium.getTitle() + " / " + medium.getImageMedium());
		}*/
		
		return "home";
	}
	
}
