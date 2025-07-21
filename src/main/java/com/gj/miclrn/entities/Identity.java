package com.gj.miclrn.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Identity extends BaseEntity {
	@Column(name = "FIRST_NAME")
	String firstName;

	@Column(name = "LAST_NAME")
	String lastName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "IDENTITY_ID")
	private Set<IdentityNotificationPreferences> notificationPreferences;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = IdentityType.class)
	@JoinColumn(insertable = false, updatable = false)
	IdentityType  type;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public IdentityType  getType() {
		return type;
	}

	public void setType(IdentityType  type) {
		this.type = type;
	}

	public Set<IdentityNotificationPreferences> getNotificationPreferences() {
		return notificationPreferences;
	}

	public void setNotificationPreferences(Set<IdentityNotificationPreferences> notificationPreferences) {
		this.notificationPreferences = notificationPreferences;
	}

}
