package com.eocandos.mc.persistence.db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Script {

	/////////////////////////
	/// ATRIBUTES
	/////////////////////////

	private int id;

	private String name;

	private String path;

	/////////////////////////
	/// CONSTRUCTOR
	/////////////////////////

	@JsonCreator
	public Script(@JsonProperty("id") int id, @JsonProperty("name")  String name, @JsonProperty("path")  String path) {
		this.id=id;
		this.name=name;
		this.path=path;
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

	public String getPath() {
		return path;
	}

	/////////////////////////
	/// PRIVATE METHODS
	/////////////////////////
	
}
