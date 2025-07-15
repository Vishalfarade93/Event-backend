package com.event.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Event {

	@Id
	private String id;
	private String title;
	private String type;
	private String mode;
	private String location;
	private LocalDate startDate;
	private LocalDate endDate;
	private String tags;
	private String RegistrationLink;
	private String description;

}
