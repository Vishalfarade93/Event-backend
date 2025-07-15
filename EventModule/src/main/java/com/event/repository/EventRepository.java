package com.event.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.event.entity.Event;

public interface EventRepository extends CrudRepository<Event, String> {
	List<Event> findByTypeIgnoreCase(String type);

	List<Event> findByLocationIgnoreCase(String location);

	List<Event> findByTypeIgnoreCaseAndLocationIgnoreCase(String type, String location);

	List<Event> findByTagsContainingIgnoreCase(String tag);
}
