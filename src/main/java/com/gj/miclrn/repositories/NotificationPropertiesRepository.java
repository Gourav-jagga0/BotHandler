package com.gj.miclrn.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gj.miclrn.entities.Identity;
import com.gj.miclrn.entities.NotificationProperties;

@Repository
public interface NotificationPropertiesRepository extends BaseRepository<NotificationProperties, Long> {

	List<NotificationProperties> findByIdentityNotificationPreferences_NUID(String nuid);

	Optional<NotificationProperties> findByIdentityNotificationPreferences_NUIDAndContent(String nuid, String content);

	void deleteByIdentityNotificationPreferences_NUIDAndContent(String nuid, String content);

}
