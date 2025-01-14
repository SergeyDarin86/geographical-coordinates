package ru.darin.coordinates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darin.coordinates.model.Region;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer> {
    Optional<Region> findRegionByRegionName(String name);
}
