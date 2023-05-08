package com.sichizhande.drone.dto.response;


public record DroneBatteryResponse(double batteryLevel) {

    public DroneBatteryResponse(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public double batteryLevel() {
        return batteryLevel;
    }
}
