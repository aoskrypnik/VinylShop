package com.vinyl.service;

import com.vinyl.exception.ComposerNotFoundException;
import com.vinyl.model.Composer;

import java.sql.Date;
import java.util.List;

public interface ComposerService {

	void save(Composer composer);

	void update(Composer composer, String composerName);

	Composer getComposerByName(String name);

	List<Composer> getAll();

	List<Composer> findComposersByCountry(String country);

	List<Composer> findComposersByActivityPeriod(Date activityStart, Date activityEnd);

}
