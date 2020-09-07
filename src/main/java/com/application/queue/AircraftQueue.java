package com.application.queue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.entity.Aircraft;

@Component
public class AircraftQueue {
    
    private static final Logger LOGGER = Logger.getLogger(AircraftQueue.class);

    private static int initialQueueCapacity = 11;
    private PriorityBlockingQueue<Aircraft> priorityQueue;
    
    @Autowired
    private AircraftComparator queueComparator;
    
    @PostConstruct
    private void initializePriorityQueue() {
        priorityQueue = new PriorityBlockingQueue<>(initialQueueCapacity, queueComparator);
    } 

    public void createAircraftQueue(List<Aircraft> aircrafts) {
        priorityQueue.addAll(aircrafts);
        LOGGER.debug("Added " + aircrafts.size() + " entries in the queue");
    }

    public boolean enqueue(Aircraft aircraft) {
        LOGGER.debug("Adding aircraft: " + aircraft.toString() + " to the queue");
        return priorityQueue.add(aircraft);
    }

    public Aircraft dequeue() {
        Aircraft aircraft = priorityQueue.poll();
        LOGGER.debug("Removed aircraft: " + aircraft.toString() + " from the queue");
        return aircraft;
    }
    
    public Iterator<Aircraft> getQueueIterator() {
        return priorityQueue.iterator();
    }
}
