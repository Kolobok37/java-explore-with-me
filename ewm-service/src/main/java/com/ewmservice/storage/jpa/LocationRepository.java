package com.ewmservice.storage.jpa;

import com.ewmservice.model.auxiliaryEntities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}