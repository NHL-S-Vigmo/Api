package unit.java.com.nhlstenden.student.vigmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit.java.com.nhlstenden.student.vigmo.models.NoIdEntity;

@Repository
public interface NoIdEntityRepository extends JpaRepository<NoIdEntity, Long> {
}
