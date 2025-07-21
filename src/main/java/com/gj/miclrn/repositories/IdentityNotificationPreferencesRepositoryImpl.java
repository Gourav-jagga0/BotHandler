package com.gj.miclrn.repositories;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import com.gj.miclrn.entities.IdentityNotificationPreferences;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

//@Repository
@NoRepositoryBean
public class IdentityNotificationPreferencesRepositoryImpl extends
		BaseRepositoryImpl<IdentityNotificationPreferences, Long> implements IdentityNotificationPreferencesRepository {

	public IdentityNotificationPreferencesRepositoryImpl(EntityManager entityManager) {
		super(IdentityNotificationPreferences.class, entityManager);
	}

	@Override
	public boolean existsByNUID(String NUID) {
		try {
			Long identityNotificationPreferences = (Long) em.createNativeQuery(
					"SELECT id FROM IDENTITY_NOTIFICATION_PREFERENCES WHERE NOTIFICATION_UID = :key AND INT_STATUS!=3",
					Long.class).setParameter("key", NUID).getSingleResult();
			return identityNotificationPreferences != null;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public Optional<IdentityNotificationPreferences> getByNUID(String NUID) {
		try {
			IdentityNotificationPreferences identityNotificationPreferences = (IdentityNotificationPreferences) em
					.createNativeQuery(
							"SELECT * FROM IDENTITY_NOTIFICATION_PREFERENCES WHERE NOTIFICATION_UID = :key AND INT_STATUS!=3",
							IdentityNotificationPreferences.class)
					.setParameter("key", NUID).getSingleResult();
			return Optional.of(identityNotificationPreferences);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

}
