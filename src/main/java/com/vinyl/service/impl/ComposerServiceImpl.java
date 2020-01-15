package com.vinyl.service.impl;

import com.vinyl.dao.ComposerDao;
import com.vinyl.model.Composer;
import com.vinyl.service.ComposerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ComposerServiceImpl implements ComposerService {

	@Resource
	private ComposerDao composerDao;

	@Override
	public void save(Composer composer) {
		composerDao.save(composer);
	}

	@Override
	public Composer getComposerByName(String name) {
		return composerDao.getComposerByName(name);
	}

	@Override
	public List<Composer> getAll() {
		return composerDao.getAll();
	}

	@Override
	public void update(Composer composer, String composerName) {
		composerDao.update(composer, composerName);
	}

	@Override
	public List<Composer> findComposersByCountry(String country) {
		return composerDao.findComposersByCountry(country);
	}

	@Override
	public List<Composer> findComposersByActivityPeriod(Date activityStart, Date activityEnd) {
		return composerDao.findComposersByActivityPeriod(activityStart, activityEnd);
	}

	@Override
	public List<Composer> findComposerByCriteria(String countryCode, Date activityStart, Date activityEnd) {
		return composerDao.findComposersByMultiplyCriteria(countryCode, activityStart, activityEnd);
	}

	@Override
	public List<String> getTracksByName(String composerName) {
		Composer foundComposer = getComposerByName(composerName);
		return isNull(foundComposer)? null : foundComposer.getTrackIds();
	}

}
