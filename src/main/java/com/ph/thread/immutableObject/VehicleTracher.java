package com.ph.thread.immutableObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleTracher {

    private Map<String, Location> locationMap = new ConcurrentHashMap<String, Location>();

    public void updateLocation(String vehicleId, Location newLocation){
        locationMap.put(vehicleId, newLocation);
    }
}
