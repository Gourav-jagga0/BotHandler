package com.gj.miclrn.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gj.miclrn.entities.ConfigSettings;
@Repository
public interface ConfigSettingsRepository extends BaseRepository<ConfigSettings, Long> {
	
	 Optional<ConfigSettings> findByKey(String key);
}
