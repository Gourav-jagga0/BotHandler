package com.gj.miclrn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ConfigSettings extends BaseEntity {
	@Column(name = "KEY_STR")
	String key;
	@Column(name = "VALUE_STR")
	String value;
	@Column(name = "GROUP_STR")
	String Group;
	@Column(name = "DESCRIPTION")
	String description;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		Group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
