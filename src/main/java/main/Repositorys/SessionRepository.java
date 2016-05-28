package main.Repositorys;

import main.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KGelashvili on 10/26/2015.
 */
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {
}
