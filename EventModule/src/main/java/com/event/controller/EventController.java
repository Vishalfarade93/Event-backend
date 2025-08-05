package com.event.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.event.entity.Event;
import com.event.service.EventService;

@RestController
@RequestMapping("/events")

public class EventController {
	@Autowired
	private EventService enventService;

	@PostMapping("/create")
	public ResponseEntity<Event> addEvent(@RequestPart("event") Event event,
			@RequestPart(value = "image", required = false) MultipartFile file) {

		System.out.println(file.getOriginalFilename());

		// image from frontend upload to upload folder

		try {
			if (!file.isEmpty()) {
				Path absolutePath = Paths.get("upload/").toAbsolutePath();

				if (Files.exists(absolutePath)) {
					Files.createDirectories(absolutePath);
				}

				String originalFilename = file.getOriginalFilename();
				Path path = absolutePath.resolve(originalFilename);

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				event.setImage("/upload/" + file.getOriginalFilename());
				Event event2 = this.enventService.addEvent(event);
				return ResponseEntity.status(HttpStatus.CREATED).body(event2);
			}
		} catch (Exception e) {
			System.out.println("Error Occured During File Upload");
		}
		return null;

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
