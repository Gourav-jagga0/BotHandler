package com.gj.miclrn.repositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.gj.miclrn.entities.BaseEntity;

import jakarta.persistence.EntityManager;

public class BaseRepositoryImpl<T extends BaseEntity,ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	 protected final EntityManager em;

	    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
	        super(domainClass, em);
	        this.em = em;
	    }

}
