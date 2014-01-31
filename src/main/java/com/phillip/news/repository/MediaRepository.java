package com.phillip.news.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.phillip.news.domain.Media;

public interface MediaRepository extends JpaRepository<Media, Long>{
	Media findByUrl(String url);
	
	/*@Query("SELECT media FROM Media as media ORDER BY media.date DESC")
	List<Media> findLast(Pageable p);
	
	@Query("SELECT media FROM Media as media WHERE (media.date = :date AND media.id < :first) OR media.date < :date ORDER BY media.date DESC, media.id DESC")
	List<Media> findBeforeThis(@Param("date") Long date, @Param("first") Long first, Pageable p);
	
	@Query("SELECT media FROM Media as media WHERE (media.date = :date AND media.id > :last) OR media.date > :date ORDER BY media.date ASC, media.id ASC")
	List<Media> findAfterThis(@Param("date") Long date, @Param("last") Long last, Pageable p);*/
}
