package com.application.queue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.entity.Aircraft;

/**
 * 
 * The main component that holds the aircrafts in a queue
 *
 */
@Component
public class AircraftQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftQueue.class);

    private static int initialQueueCapacity = 11;
    private PriorityBlockingQueue<Aircraft> priorityQueue;

    @Autowired
    private AircraftComparator queueComparator;

    /**
     * let the autowiring take place and then initialize the queue.
     * if added this in the constructor then the queueComparator is null and is not autowired
     * we can solve it by either initializing the queueComparator as well 
     * or putting the queue initialization in the post construct
     */
    @PostConstruct
    private void initializePriorityQueue() {
        priorityQueue = new PriorityBlockingQueue<>(initialQueueCapacity, queueComparator);
    } 

    /**
     * fill the priority queue with provided aircrafts
     * @param aircrafts
     */
    public void createAircraftQueue(List<Aircraft> aircrafts) {
        if(aircrafts == null) {
            LOGGER.debug("aircrafts list is null");
            return;
        }
        priorityQueue.addAll(aircrafts);
        LOGGER.debug("Added " + aircrafts.size() + " entries in the queue");
    }

    /**
     * add one aircraft to the priority queue
     * @param aircraft
     * @return
     */
    public boolean enqueue(Aircraft aircraft) {
        if(aircraft == null) {
            LOGGER.debug("aircraft is null");
            return false;
        }
        return priorityQueue.add(aircraft);
    }

    /**
     * remove an aircraft from the priority queue
     * @return
     */
    public Aircraft dequeue() {
        Aircraft aircraft = priorityQueue.poll();
        if(aircraft == null) {
            LOGGER.debug("queue is empty");
            return aircraft;
        }
        return aircraft;
    }

    /**
     * returns the priority queue iterator
     * @return
     */
    public Iterator<Aircraft> getQueueIterator() {
        return priorityQueue.iterator();
    }
    
    /**
     * returns the current size of the priority queue
     * @return
     */
    public int size() {
        return priorityQueue.size();
    }
    
    /**
     * returns true if the priority queue contains no elements.
     * @return
     */
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }
}
