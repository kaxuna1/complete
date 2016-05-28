package main.Repositorys;

import main.models.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KGelashvili on 11/2/2015.
 */

@Transactional
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
