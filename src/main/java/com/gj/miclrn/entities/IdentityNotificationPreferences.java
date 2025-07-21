package com.gj.miclrn.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "IDENTITY_NOTIFICATION_PREFERENCES")
public class IdentityNotificationPreferences extends BaseEntity {

	@Column(name = "NOTIFICATION_UID", unique = true, length = 30)
	private String NUID;
	@Column(name ="MODE")
	private String Mode;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="NOTIFICATION_PREFERENCE_ID")
    private List<NotificationProperties> notificationProperties;

	public String getNUID() {
		return NUID;
	}

	public void setNUID(String nUID) {
		NUID = nUID;
	}

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	public List<NotificationProperties> getNotificationProperties() {
		return notificationProperties;
	}

	public void setNotificationProperties(List<NotificationProperties> notificationProperties) {
		this.notificationProperties = notificationProperties;
	}

}