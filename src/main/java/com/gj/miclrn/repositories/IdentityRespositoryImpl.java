package com.gj.miclrn.repositories;

import org.springframework.stereotype.Repository;

import com.gj.miclrn.entities.Identity;

import jakarta.persistence.EntityManager;
//@Repository
public class IdentityRespositoryImpl extends BaseRepositoryImpl<Identity, Long> implements IdentityRepository{

	public IdentityRespositoryImpl( EntityManager entityManager) {
		super(Identity.class, entityManager);
	}

}
