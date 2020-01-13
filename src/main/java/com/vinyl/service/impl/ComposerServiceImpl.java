package com.vinyl.service.impl;

import com.vinyl.dao.ComposerDao;
import com.vinyl.model.Composer;
import com.vinyl.service.ComposerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class ComposerServiceImpl implements ComposerService {

	private static final String COMPOSER_NOT_FOUND_EXCEPTION = "Cannot find composer with that name ";

	@Resource
	private ComposerDao composerDao;

	@Override
	public void save(Composer composer) {
		composerDao.save(composer);
	}

	@Override
	public Composer getComposerByName(String name){
		return composerDao.getComposerByName(name);
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
	public void update(Composer composer, String composerName) {
		composerDao.update(composer, composerName);
	}

	@Override
	public List<Composer> getAll() {
		return composerDao.getAll();
	}

}
