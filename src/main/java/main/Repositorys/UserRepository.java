package main.Repositorys;

import java.util.List;

import main.models.Filial;
import main.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
	@Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:username,'%') OR u.email LIKE CONCAT('%',:email,'%') OR u.address LIKE CONCAT('%',:address,'%')")
	Page<User> findByUsernameOrEmailOrAddress(@Param("username")String username,@Param("email")String email,@Param("address")String address,Pageable pageable);
	@Query("SELECT u FROM User u WHERE (u.username LIKE CONCAT('%',:username,'%') OR u.email LIKE CONCAT('%',:email,'%') OR u.address LIKE CONCAT('%',:address,'%')) AND u.organisation.id=:organisation")
	Page<User> findByUsernameOrEmailOrAddressAndOrganisation(@Param("username")String username,@Param("email")String email,@Param("address")String address,@Param("organisation")long organisation,Pageable pageable);
	List<User> findByEmail(@Param("email")String email,Pageable pageable);
	List<User> findByPersonalNumber(@Param("personalNumber")String personalNumber,Pageable pageable);
	List<User> findByType(@Param("type")int type);
	List<User> findByUsername(@Param("username") String username);
	List<User> findByFilialAndType(@Param("filial")Filial filial,@Param("type")int type);
}
