package com.application.rest.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.constants.EnumAircraftSize;
import com.application.constants.EnumAircraftType;
import com.application.constants.EnumErrorType;
import com.application.entity.Aircraft;
import com.application.rest.models.AircraftInfo;
import com.application.rest.models.ErrorObject;
import com.application.rest.models.ResponseWrapper;
import com.application.queue.AircraftQueueManager;

@Service
public class ApiService {

    @Autowired
    private AircraftQueueManager aircraftQueueManager;

    public ResponseEntity<List<Aircraft>> listAircrafts() {
        List<Aircraft> aircraftsInQueue = aircraftQueueManager.listElements();
        return new ResponseEntity<>(aircraftsInQueue, HttpStatus.OK);
    }

    public ResponseEntity<Object> addAircraftToQueue(AircraftInfo aircraftInfo) {
        // validate inputs
        if(aircraftInfo == null) {
            EnumErrorType error = EnumErrorType.AIRCRAFT_INFO_NULL;
            ResponseWrapper responseResult = new ResponseWrapper(false, "", null, setError(error));
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        }

        if(!EnumAircraftType.isValidAircraftType(aircraftInfo.getAircraftType())) {
            EnumErrorType error = EnumErrorType.AIRCRAFT_TYPE_INVALID;
            ResponseWrapper responseResult = new ResponseWrapper(false, "", null, setError(error));
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        }

        if(!EnumAircraftSize.isValidAircraftSize(aircraftInfo.getAircraftSize())) {
            EnumErrorType error = EnumErrorType.AIRCRAFT_SIZE_INVALID;
            ResponseWrapper responseResult = new ResponseWrapper(false, "", null, setError(error));
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        }

        if(StringUtils.isBlank(aircraftInfo.getAircraftName())) {
            EnumErrorType error = EnumErrorType.AIRCRAFT_NAME_NULL_OR_EMPTY;
            ResponseWrapper responseResult = new ResponseWrapper(false, "", null, setError(error));
            return new ResponseEntity<>(responseResult, HttpStatus.BAD_REQUEST);
        }

        // get aircraft
        Aircraft aircraft = getAircraftFromAircraftInfo(aircraftInfo);
        if(aircraft == null) {
            EnumErrorType error = EnumErrorType.INTERNAL_SERVER_ERROR;
            ResponseWrapper responseResult = new ResponseWrapper(false, "", null, setError(error));
            return new ResponseEntity<>(responseResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // enqueue
        boolean enqueueResult = aircraftQueueManager.enqueue(aircraft);

        // return
        ResponseWrapper responseResult = new ResponseWrapper(true, "Aircraft successfully added to the queue", enqueueResult, null);
        return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
    }

    public ResponseEntity<Object> removeAircraftFromQueue() {
        Aircraft aircraft = aircraftQueueManager.dequeue();
        AircraftInfo aircraftInfo = getAircraftInfoFromAircraft(aircraft);
        ResponseWrapper responseResult = new ResponseWrapper(true, "Aircraft successfully removed to the queue (value can be null if queue is empty)", aircraftInfo, null);
        return new ResponseEntity<Object>(responseResult, HttpStatus.OK);
    }

    private Aircraft getAircraftFromAircraftInfo(AircraftInfo info) {
        Aircraft aircraft = new Aircraft();

        EnumAircraftType type = EnumAircraftType.getIfContains(info.getAircraftType());
        if(type == null) {
            return null;
        }

        EnumAircraftSize size = EnumAircraftSize.getIfContains(info.getAircraftSize());
        if(size == null) {
            return null;
        }

        aircraft.setAircraftType(type);
        aircraft.setAircraftSize(size);
        aircraft.setAircraftName(info.getAircraftName());
        return aircraft;

    }

    private AircraftInfo getAircraftInfoFromAircraft(Aircraft aircraft) {
        if(aircraft == null) {
            return null;
        }

        AircraftInfo info = new AircraftInfo();
        info.setAircraftType(aircraft.getAircraftType().getCode());
        info.setAircraftSize(aircraft.getAircraftSize().getCode());
        info.setAircraftName(aircraft.getAircraftName());
        return info;
    }

    private ErrorObject setError(EnumErrorType error){
        return new ErrorObject(error.getErrorCode(),error.getErrorMsg());
    }

}
