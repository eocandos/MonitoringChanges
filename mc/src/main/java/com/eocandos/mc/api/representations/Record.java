package com.eocandos.mc.api.representations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record {

	/////////////////////////
	/// ATRIBUTES
	/////////////////////////

	private int id;

	private String name;
	
	private String description;
	
	private List<String> affected;

	private String dateCreated;

	/////////////////////////
	/// CONSTRUCTOR
	/////////////////////////

	@JsonCreator
	public Record(@JsonProperty("id") int id, @JsonProperty("name")  String name, @JsonProperty("description")  String description, @JsonProperty("affected") List<String> affected, @JsonProperty("dateCreated")  String dateCreated) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.affected=affected;
		this.dateCreated=dateCreated;
	}

	/////////////////////////
	/// PUBLIC METHODS
	/////////////////////////

	@JsonIgnore
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getAffected() {
		return affected;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	/////////////////////////
	/// PRIVATE METHODS
	/////////////////////////
}
