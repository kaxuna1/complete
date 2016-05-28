package main.Repositorys;

import main.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KGelashvili on 11/3/2015.
 */
@Transactional
public interface ZoneRepository extends JpaRepository<Zone, Long>{
}
