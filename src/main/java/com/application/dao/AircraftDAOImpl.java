package com.application.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.application.entity.Aircraft;

@Transactional
@Component
public class AircraftDAOImpl extends BaseDAOImpl<Aircraft> implements AircraftDAO {
    @Autowired
    public AircraftDAOImpl(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    @Override
    public void saveAircraft(Aircraft aircraft) {
        this.save(aircraft);
    }

    @Override
    public void deleteAircraft(Aircraft aircraft) {
        this.delete(aircraft);
    }

    @Override
    public List<Aircraft> getAllAircrafts() {
        return this.findAll();
    }
    
    
    

}
