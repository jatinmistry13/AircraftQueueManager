package com.application.queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.application.dao.AircraftDAO;
import com.application.entity.Aircraft;

@Component
public class AircraftQueueManager {

    private static final Logger LOGGER = Logger.getLogger(AircraftQueueManager.class);

    private static final String PERSIST_DATABASE_TRUE = "true";

    @Value("${persist.datastore}")
    private String persistDatabaseProperty;

    @Autowired
    private AircraftDAO aircraftDAO;

    @Autowired
    private AircraftQueue aircraftQueue;

    @PostConstruct
    private void fillAircraftQueue() {
        if (StringUtils.equalsIgnoreCase(persistDatabaseProperty, PERSIST_DATABASE_TRUE)) {
            // get aircrafts from db
            List<Aircraft> aircraftsInDB = aircraftDAO.getAllAircrafts();

            if(aircraftsInDB == null) {
                return;
            }

            // initialize queue with data from db
            LOGGER.debug("Initializing the aircraft queue with " + aircraftsInDB.size() + " entries");
            aircraftQueue.createAircraftQueue(aircraftsInDB);
        }
    }

    /**
     * add an aircraft to the priority queue.
     * also save the aircraft info the DB if indicated so in the application properties
     * @param aircraft
     * @return
     */
    public synchronized boolean enqueue(Aircraft aircraft) {
        LOGGER.debug("Adding aircraft: " + aircraft.toString() + " to the queue");
        if (StringUtils.equalsIgnoreCase(persistDatabaseProperty, PERSIST_DATABASE_TRUE)) {
            aircraftDAO.saveAircraft(aircraft);
        }
        return aircraftQueue.enqueue(aircraft);
    }

    /**
     * remove an aircraft from the priority queue
     * also delete the aircraft info the DB if indicated so in the application properties
     * @return
     */
    public synchronized Aircraft dequeue() {
        Aircraft aircraft = aircraftQueue.dequeue();
        if (StringUtils.equalsIgnoreCase(persistDatabaseProperty, PERSIST_DATABASE_TRUE)) {
            aircraftDAO.deleteAircraft(aircraft);
        }
        LOGGER.debug("Removed aircraft: " + aircraft.toString() + " from the queue");
        return aircraft;
    }

    /**
     * return a list of all the elements in the priority queue in order of their priority
     * @return
     */
    public synchronized LinkedList<Aircraft> listElements() {
        LinkedList<Aircraft> aircraftsInQueue = new LinkedList<>();
        Iterator<Aircraft> queueIterator = aircraftQueue.getQueueIterator();
        LOGGER.debug("Following aircrafts are in the queue:");
        while(queueIterator.hasNext()) {
            Aircraft aircraft = queueIterator.next();
            LOGGER.debug(aircraft.toString());
            aircraftsInQueue.add(aircraft);
        }
        return aircraftsInQueue;
    }

}
