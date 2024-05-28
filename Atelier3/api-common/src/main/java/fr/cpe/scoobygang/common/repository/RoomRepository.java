package fr.cpe.scoobygang.common.repository;

import fr.cpe.scoobygang.common.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
