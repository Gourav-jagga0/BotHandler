package com.gj.miclrn.repositories;

import java.util.Optional;

import com.gj.miclrn.entities.ConfigSettings;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class ConfigSettingsRespositoryImpl extends BaseRepositoryImpl<ConfigSettings, Long> implements ConfigSettingsRepository{

	public ConfigSettingsRespositoryImpl( EntityManager entityManager) {
		super(ConfigSettings.class, entityManager);
	}

	@Override
	public Optional<ConfigSettings> findByKey(String key) {
	    try {
	        ConfigSettings config = (ConfigSettings) em.createNativeQuery(
	                "SELECT * FROM config_settings WHERE key = :key", ConfigSettings.class)
	            .setParameter("key", key)
	            .getSingleResult();
	        return Optional.of(config);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    }
	}

}
