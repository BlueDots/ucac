package com.ucac.tourist.service;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;

public interface TouristService {
	public String controlTouristState(int currentState) throws DBException, ErrorException;
}
