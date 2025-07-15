package com.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.entity.Event;
import com.event.service.EventService;

@RestController
@RequestMapping("/events")
@CrossOrigin("http://localhost:5173")
public class EventController {
	@Autowired
	private EventService enventService;

	@PostMapping("/create")
	public ResponseEntity<Event> addEvent(@RequestBody Event event) {
		Event event2 = this.enventService.addEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event2);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable String id) {
		Event event = this.enventService.getEventByID(id);
		return ResponseEntity.ok(event);
	}

	@GetMapping
	public ResponseEntity<List<Event>> getAllEvents() {
		return ResponseEntity.ok(this.enventService.getAllEvents());
	}

	@GetMapping("/filter")
	public ResponseEntity<List<Event>> getFilteredEvents(@RequestParam(required = false) String type,
			@RequestParam(required = false) String location) {
		return ResponseEntity.ok(this.enventService.filterEvent(type, location));
	}

	@GetMapping("/filter/{tag}")
	public ResponseEntity<List<Event>> getFilteredEventsTag(@PathVariable String tag) {
		return ResponseEntity.ok(this.enventService.filterByTags(tag));
	}

}
