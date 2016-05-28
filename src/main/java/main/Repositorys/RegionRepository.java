package main.Repositorys;

import main.models.Region;
import main.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vakhtanggelashvili on 10/23/15.
 */
@Transactional
public interface RegionRepository extends JpaRepository<Region, Long> {

}
