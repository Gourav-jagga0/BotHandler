package com.gj.miclrn.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.gj.miclrn.identifies.sequenceGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public class BaseEntity {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // Changed to standard ID generation
	@Column(name = "id", unique = true, updatable = false)
	@GenericGenerator(name = "sequence_generator", strategy = "com.gj.miclrn.identifies.IdeGenerator")
	@GeneratedValue(generator = "sequence_generator")
	Long id;

	@Column(name = "int_status")
	int int_status;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false, nullable = false)
	private Date createdOn;
	@UpdateTimestamp
	@Column(name = "changed_on", nullable = false)
	private Date changedOn;

	public int getInt_status() {
		return int_status;
	}

	public void setInt_status(int int_status) {
		this.int_status = int_status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	private void prePersist() {
		if (this.getId() == null) {
			this.setId(sequenceGenerator.getInstance().nextId());
		}
	}

}
