package main.Repositorys;

import main.models.Filial;
import main.models.Format;
import main.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kaxa on 5/24/16.
 */
@Transactional
public interface FilialReporistory extends JpaRepository<Filial, Long> {
    List<Filial> findByRegion(@Param("region")Region one);
}
