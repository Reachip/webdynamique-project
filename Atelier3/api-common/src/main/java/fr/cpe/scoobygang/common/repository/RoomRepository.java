package fr.cpe.scoobygang.common.repository;

import fr.cpe.scoobygang.common.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT count(c) FROM Room c")
    long count();
}
