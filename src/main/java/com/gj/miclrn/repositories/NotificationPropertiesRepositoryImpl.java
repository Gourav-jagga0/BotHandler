package com.gj.miclrn.repositories;

import java.util.List;
import java.util.Optional;

import com.gj.miclrn.entities.Identity;
import com.gj.miclrn.entities.NotificationProperties;

import jakarta.persistence.EntityManager;

//@Repository
public class NotificationPropertiesRepositoryImpl extends BaseRepositoryImpl<NotificationProperties, Long>
		implements NotificationPropertiesRepository {

	public NotificationPropertiesRepositoryImpl(EntityManager entityManager) {
		super(NotificationProperties.class, entityManager);
	}

	@Override
	public List<NotificationProperties> findByIdentityNotificationPreferences_NUID(String nuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<NotificationProperties> findByIdentityNotificationPreferences_NUIDAndContent(String nuid,
			String content) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteByIdentityNotificationPreferences_NUIDAndContent(String nuid, String content) {
		// TODO Auto-generated method stub

	}

}
