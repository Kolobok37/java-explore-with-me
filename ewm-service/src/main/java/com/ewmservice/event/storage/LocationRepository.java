package com.ewmservice.event.storage;

import com.ewmservice.event.auxiliaryEntities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}