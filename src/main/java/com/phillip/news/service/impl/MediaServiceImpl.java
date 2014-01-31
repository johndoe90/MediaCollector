package com.phillip.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.repository.CategoryRepository;
import com.phillip.news.repository.MediaProviderRepository;
import com.phillip.news.repository.MediaRepository;
import com.phillip.news.service.MediaService;


@Service
public class MediaServiceImpl implements MediaService{
	@PersistenceContext
	private EntityManager em;
	
	private final MediaRepository mediaRepository;
	private final CategoryRepository categoryRepository;
	private final MediaProviderRepository mediaProviderRespository;
	
	@Inject
	public MediaServiceImpl(MediaRepository mediaRepository, MediaProviderRepository mediaProviderRespository, CategoryRepository categoryRepository){
		this.mediaRepository = mediaRepository;
		this.mediaProviderRespository = mediaProviderRespository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Media persist(Media media) {
		Assert.notNull(media);
		
		MediaProvider mediaProvider = mediaProviderRespository.findByMediaProviderId(media.getMediaProvider().getMediaProviderId());		
		media.setMediaProvider(mediaProvider);
		
		Category category = categoryRepository.findByQualifiedName(media.getCategory().getQualifiedName());
		media.setCategory(category);
		
		return mediaRepository.save(media);
	}
	
	@Override
	public Media findById(Long id) {
		Assert.notNull(id);
		
		return mediaRepository.findOne(id);
	}

	@Override
	public Boolean exists(String URL) {
		Assert.hasText(URL);
		
		return findByUrl(URL) != null ? true : false;
	}

	@Override
	public Media findByUrl(String URL) {
		Assert.hasText(URL);
		
		return mediaRepository.findByUrl(URL);
	}
	
	@Override
	public List<Media> findAll() {
		List<Media> all = new ArrayList<Media>();
		for(Media media : mediaRepository.findAll()){
			all.add(media);
		}
		
		return all;
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findByCategory(Category category) {
		Assert.notNull(category);
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(10)
								.add(Restrictions.eq("category", category))
								.list();
		
		return media;
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findByMediaProvider(MediaProvider mediaProvider) {
		Assert.notNull(mediaProvider);
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.add(Restrictions.eq("mediaProvider", mediaProvider))
								.list();
		
		return media;
	}

	//works
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findRecent(List<Category> categories, List<MediaProvider> mediaProviders, Integer quantity) {
		Assert.notEmpty(categories);
		Assert.notEmpty(mediaProviders);
		Assert.notNull(quantity);
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("category", categories))
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.addOrder(Order.desc("date"))
								.addOrder(Order.desc("id"))
								.list();
		
		return media;
	}

	//works
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findAfterThis(List<Category> categories, List<MediaProvider> mediaProviders, Media last, Integer quantity) {
		Assert.notEmpty(categories);
		Assert.notEmpty(mediaProviders);
		Assert.noNullElements(new Object[]{last, quantity});
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.add(Restrictions.in("category", categories))
								.add(Restrictions.or(
										Restrictions.and(
												Restrictions.eq("date", last.getDate()),
												Restrictions.gt("id", last.getId())
										),
										Restrictions.gt("date", last.getDate())
								))
								.addOrder(Order.asc("date"))
								.addOrder(Order.asc("id"))
								.list();
		
		return media;
	}

	//works
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findBeforeThis(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Integer quantity) {
		Assert.notEmpty(categories);
		Assert.notEmpty(mediaProviders);
		Assert.noNullElements(new Object[]{first, quantity});
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.add(Restrictions.in("category", categories))
								.add(Restrictions.or(
										Restrictions.and(
												Restrictions.eq("date", first.getDate()),
												Restrictions.lt("id", first.getId())	//wa gt
										),
										Restrictions.lt("date", first.getDate())
								))
								.addOrder(Order.desc("date"))
								.addOrder(Order.desc("id"))
								.list();
		
		return media;
	}

	
	//remove first and last ?
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findBetweenThem(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Media last) {
		Assert.notEmpty(categories);
		Assert.notEmpty(mediaProviders);
		Assert.noNullElements(new Object[]{first, last});
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.add(Restrictions.in("category", categories))
								.add(Restrictions.and(
										Restrictions.ge("date", first.getDate()),
										Restrictions.le("date", last.getDate())
								))
								.addOrder(Order.desc("date"))
								.addOrder(Order.desc("id"))
								.list();
		
		return media;
	}

	@Override
	@Transactional
	public void consume(Media media) {
		media.setConsumed(media.getConsumed() + 1);
		mediaRepository.save(media);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Media> query(List<Long> categoryIds, List<Long> mediaProviderIds, String q, Integer quantity) {		
		Assert.notEmpty(categoryIds);
		Assert.notEmpty(mediaProviderIds);
		Assert.hasText(q);
		Assert.notNull(quantity);
		
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery("queryMedia");		
		query.setParameterList("categories", categoryIds);
		query.setParameterList("mediaProviders", mediaProviderIds);
		query.setParameter("q", q);
		query.setParameter("quantity", quantity);
		
		return query.list();
	}
}
