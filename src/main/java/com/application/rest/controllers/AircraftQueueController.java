package com.application.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.application.rest.models.AircraftInfo;
import com.application.rest.service.ApiService;

@RestController
@RequestMapping("/queues")
public class AircraftQueueController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity<Object> listAircrafts() {
        return apiService.listAircrafts();
    }

    @RequestMapping(value="/", method = RequestMethod.PUT)
    public ResponseEntity<Object> addAircraftToQueue(@RequestBody AircraftInfo aircraftInfo) {
        return apiService.addAircraftToQueue(aircraftInfo);
    }

    @RequestMapping(value="/", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeAircraftFromQueue() {
        return apiService.removeAircraftFromQueue();
    }

}
