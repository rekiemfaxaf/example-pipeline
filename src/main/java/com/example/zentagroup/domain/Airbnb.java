package com.example.zentagroup.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Airbnb implements Serializable {

	private static final long serialVersionUID = -4721845038463397451L;

	private Integer id;
	private String last_scraped;
	private String host_name;
	private String host_since;
	private String city;
	private String property_type;
	private String room_type;
	private Integer review_scores_rating;

}
