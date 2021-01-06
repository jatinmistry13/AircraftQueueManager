package com.application.dao;

import org.springframework.stereotype.Repository;

import com.application.entity.Aircraft;

@Repository
public interface AircraftDAO extends IBaseDAO<Aircraft, Integer> {

}
