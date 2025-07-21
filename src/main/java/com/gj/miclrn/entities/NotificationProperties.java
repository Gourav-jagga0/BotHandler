package com.gj.miclrn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "NORIFICATION_PROPERTIES")
public class NotificationProperties extends BaseEntity {

	@Column(name = "CONTENT")
	String content;
	@Column(name = "SCHEDULE")
	String schedule;

	@ManyToOne
	@JoinColumn(name = "preference_id")
	private IdentityNotificationPreferences identityNotificationPreferences;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public IdentityNotificationPreferences getIdentityNotificationPreferences() {
		return identityNotificationPreferences;
	}

	public void setIdentityNotificationPreferences(IdentityNotificationPreferences identityNotificationPreferences) {
		this.identityNotificationPreferences = identityNotificationPreferences;
	}

}
