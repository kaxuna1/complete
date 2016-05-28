package main.Repositorys;

import main.models.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KGelashvili on 11/2/2015.
 */
@Transactional
public interface FormatRepository extends JpaRepository<Format, Long> {
}
