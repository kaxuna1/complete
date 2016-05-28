package main.Repositorys;

import main.models.Movement;
import main.models.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KGelashvili on 10/23/2015.
 */
@Transactional
public interface MovementRepository extends JpaRepository<Movement, Long> {
}
