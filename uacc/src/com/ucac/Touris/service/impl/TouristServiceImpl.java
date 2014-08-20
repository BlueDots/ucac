package com.ucac.Touris.service.impl;

import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;
import com.ucac.tourist.service.TouristService;

public class TouristServiceImpl implements TouristService{
	private TouristServiceImpl(){}
    private EntityDao  entity  = EntityDaoImpl.getInstance();
	private static class touristServiceHelper{
		static final TouristService INSTANCE = new TouristServiceImpl(); 
	}
	
	public static TouristService getInstance(){
		return touristServiceHelper.INSTANCE;
	}
	
	@Override
	public String controlTouristState(int currentState) throws DBException, ErrorException {
		// TODO Auto-generated method stub
		String success="设置成功!";
	    Settings  old  =entity.findEntity(Settings.class, null, null);
		Settings settings = new Settings();
		settings.setId(1);
		settings.setPicture(old.getPicture());
		settings.setDocument(old.getDocument());
		settings.setVideo(old.getVideo());
	
		if (currentState == 0) {
			settings.setTouristState(1);
			entity.update(settings);
		}else{
			settings.setTouristState(0);
			entity.update(settings);
		}
		return success;
	}

}
