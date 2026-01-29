package com.workspace.hero.booking_service.Repository;

import com.workspace.hero.booking_service.Entity.WorkspaceEntity;
import com.workspace.hero.booking_service.Entity.enums.WorkSpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    Optional<WorkspaceEntity> findByNameAndType(String name, WorkSpaceType type);
}
