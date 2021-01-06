package com.application.queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.application.dao.AircraftDAO;
import com.application.entity.Aircraft;

@Component
public class AircraftQueueManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftQueueManager.class);

    private static final String PERSIST_DATABASE_TRUE = "true";

    @Value("${persist.datastore}")
    private String persistDatabaseProperty;

    @Resource
    private AircraftDAO aircraftDAO;

    @Autowired
    private AircraftQueue aircraftQueue;

    @PostConstruct
    private void fillAircraftQueue() {
        if (StringUtils.equalsIgnoreCase(persistDatabaseProperty, PERSIST_DATABASE_TRUE)) {
            // get aircrafts from db
            List<Aircraft> aircraftsInDB = aircraftDAO.findAll();

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
            aircraftDAO.save(aircraft);
            LOGGER.debug("Added aircraft: " + aircraft.toString() + " to DB");
        }
        boolean result = aircraftQueue.enqueue(aircraft);
        LOGGER.debug("Added aircraft: " + aircraft.toString() + " to the queue");
        return result;
    }

    /**
     * remove an aircraft from the priority queue
     * also delete the aircraft info the DB if indicated so in the application properties
     * @return
     */
    public synchronized Aircraft dequeue() {
        if (aircraftQueue.isEmpty()) {
            LOGGER.debug("Aircraft queue is empty");
            return null;
        }
        LOGGER.debug("Removing aircraft from the queue");
        Aircraft aircraft = aircraftQueue.dequeue();
        if (StringUtils.equalsIgnoreCase(persistDatabaseProperty, PERSIST_DATABASE_TRUE)) {
            aircraftDAO.delete(aircraft);
            LOGGER.debug("Removed aircraft: " + aircraft.toString() + " from DB");
        }
        LOGGER.debug("Removed aircraft: " + aircraft.toString() + " from the queue");
        return aircraft;
    }

    /**
     * return a list of all the elements in the priority queue
     * @return
     */
    public synchronized LinkedList<Aircraft> listElements() {
        LinkedList<Aircraft> aircraftsInQueue = new LinkedList<>();
        Iterator<Aircraft> queueIterator = aircraftQueue.getQueueIterator();
        //LOGGER.debug("Following aircrafts are in the queue:");
        while(queueIterator.hasNext()) {
            Aircraft aircraft = queueIterator.next();
            //LOGGER.debug(aircraft.toString());
            aircraftsInQueue.add(aircraft);
        }
        return aircraftsInQueue;
    }

}
