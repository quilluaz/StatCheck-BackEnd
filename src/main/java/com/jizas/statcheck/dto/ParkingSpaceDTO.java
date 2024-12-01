package com.jizas.statcheck.dto;

import com.jizas.statcheck.entity.ParkingSpaceEntity;

public class ParkingSpaceDTO {
    private Long parkingSpaceId;
    private String parkingName;
    private String status;
    private String spaceType;
    private Long parkingLotId;

    // Constructors
    public ParkingSpaceDTO() {}

    public ParkingSpaceDTO(ParkingSpaceEntity entity) {
        this.parkingSpaceId = entity.getParkingSpaceId();
        this.parkingName = entity.getParkingName();
        this.status = entity.getStatus();
        this.spaceType = entity.getSpaceType();
        this.parkingLotId = entity.getParkingLot().getParkingLotID();
    }

    // Getters and Setters
    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(Long parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
} 