package com.gj.miclrn.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gj.miclrn.entities.IdentityNotificationPreferences;
/**
 *  Implemented By  IdentityNotificationPreferencesRepositoryImpl
 */
@Repository
public interface IdentityNotificationPreferencesRepository extends BaseRepository<IdentityNotificationPreferences, Long> {
	boolean existsByNUID(String object);
	Optional<IdentityNotificationPreferences> getByNUID(String object);

}
