package com.application.dao;

import java.util.List;

import com.application.entity.Aircraft;

public interface AircraftDAO extends IBaseDAO<Aircraft> {
    
    void saveAircraft(Aircraft aircraft);
    void deleteAircraft(Aircraft aircraft);
    List<Aircraft> getAllAircrafts();

}
