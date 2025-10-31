package mx.gob.imss.dpes.personaback.repository;

import mx.gob.imss.dpes.personaback.entity.McltTokenRegistroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author luisr.rodriguez
 */
public interface TokenRegistroRepository extends JpaRepository<McltTokenRegistroUsuario, Long>,
        JpaSpecificationExecutor<McltTokenRegistroUsuario> {
    
}
