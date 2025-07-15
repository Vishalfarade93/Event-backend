package com.event.service;

import java.util.List;

import com.event.entity.Event;

public interface EventService {
	Event addEvent(Event event);

	List<Event> getAllEvents();

	Event getEventByID(String id);

	List<Event> filterEvent(String type, String location);

	List<Event> filterByTags(String tag);

}
