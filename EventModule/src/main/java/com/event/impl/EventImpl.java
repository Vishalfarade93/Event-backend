package com.event.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.Exception.ResourceNotFoundException;
import com.event.entity.Event;
import com.event.repository.EventRepository;
import com.event.service.EventService;

@Service
public class EventImpl implements EventService {

	@Autowired
	private EventRepository Eventrepository;

	@Override
	public Event addEvent(Event event) {
		String id = UUID.randomUUID().toString();
		event.setId(id);
		Event savedEvent = this.Eventrepository.save(event);
		return savedEvent;
	}

	@Override
	public List<Event> getAllEvents() {
		Iterable<Event> allEvents = this.Eventrepository.findAll();
		return (List<Event>) allEvents;
	}

	@Override
	public Event getEventByID(String id) {
		Event singleEvent = this.Eventrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User With Given ID" + id + " Not Found"));
		return singleEvent;
	}

	@Override
	public List<Event> filterEvent(String type, String location) {
		if (type != null && location != null) {
			List<Event> bytypeandLocation = this.Eventrepository.findByTypeIgnoreCaseAndLocationIgnoreCase(type,
					location);
			return bytypeandLocation;
		} else if (type != null) {
			List<Event> byType = this.Eventrepository.findByTypeIgnoreCase(type);
			return byType;
		} else if (location != null) {
			List<Event> byLocation = this.Eventrepository.findByLocationIgnoreCase(location);
			return byLocation;
		}
		return (List<Event>) this.Eventrepository.findAll();
	}

	@Override
	public List<Event> filterByTags(String tag) {
		return this.Eventrepository.findByTagsContainingIgnoreCase(tag);
	}

}
