package ru.practicum.ewm.server.model.dto.location;

import ru.practicum.ewm.server.model.entity.Location;

/*
    Сущность места маппер
 */
public class LocationMapper {

    public static LocationDtoOut locationToLocationDtoOut(Location location) {
        return LocationDtoOut.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public static Location locationDtoOutToLocation(LocationDtoOut locationDtoOut) {
        return Location.builder()
                .lat(locationDtoOut.getLat())
                .lon(locationDtoOut.getLon())
                .build();
    }
}
