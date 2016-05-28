package main.Repositorys;

import main.models.Organisation;
import main.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/22/15.
 */
@Transactional
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    List<Organisation> findByEmail(@Param("email")String email,Pageable pageable);

    @Query("SELECT u FROM Organisation u WHERE u.name LIKE CONCAT('%',:name,'%') OR u.email LIKE CONCAT('%',:email,'%') OR u.address LIKE CONCAT('%',:address,'%')")
    Page<Organisation> findByNameOrEmailOrAddress(@Param("name")String name,@Param("email")String email,@Param("address")String address,Pageable pageable);

    Page<Organisation> findByRegionId(@Param("regionId")int regionId,Pageable pageable);
}
