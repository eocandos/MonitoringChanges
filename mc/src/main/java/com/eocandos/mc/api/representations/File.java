package com.eocandos.mc.api.representations;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class File {

	/////////////////////////
	/// ATRIBUTES
	/////////////////////////

	private int id;

	private String name;

	private String type;

	private String path;

	private Date dateCreate;

	/////////////////////////
	/// CONSTRUCTOR
	/////////////////////////

	@JsonCreator
	public File(@JsonProperty("id") int id, @JsonProperty("name")  String name, @JsonProperty("type")  String type, @JsonProperty("path")  String path, @JsonProperty("dateCreate")  Date dateCreate) {
		this.id=id;
		this.name=name;
		this.type=type;
		this.path=path;
		this.dateCreate=dateCreate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	/////////////////////////
	/// PRIVATE METHODS
	/////////////////////////

}
