package com.gj.miclrn.entities;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "IDENTITY_TYPE")
@SQLDelete(sql = "UPDATE IDENTITY_TYPE SET INT_STATUS=3 WHERE ")
public class IdentityType extends BaseEntity {
	@Column(name = "TEXT", length = 25 , unique = true )
	private String text;

	@Column(name = "DESCRIPTION", length = 25, nullable = false)
	private String description;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
