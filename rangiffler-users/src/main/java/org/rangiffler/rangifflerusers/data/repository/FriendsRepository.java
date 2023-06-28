package org.rangiffler.rangifflerusers.data.repository;

import org.rangiffler.rangifflerusers.data.FriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendsRepository extends JpaRepository<FriendsEntity, UUID> {

    List<FriendsEntity> findAllByUserId(UUID userId);
}
