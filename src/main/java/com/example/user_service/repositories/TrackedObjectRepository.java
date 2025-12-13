package com.example.user_service.repositories;

import com.example.user_service.models.TrackedObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackedObjectRepository extends JpaRepository<TrackedObject, Long> {
    List<TrackedObject> findByUserUserId(Long userId);
    Optional<TrackedObject> findByIdAndUserUserId(Long id, Long userId);
}
