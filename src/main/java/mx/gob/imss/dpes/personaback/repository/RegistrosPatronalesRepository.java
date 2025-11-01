/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import mx.gob.imss.dpes.personaback.entity.McltRegistrosPatronales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Marco A
 */
public interface RegistrosPatronalesRepository extends JpaRepository<McltRegistrosPatronales, Long>,
        JpaSpecificationExecutor<McltRegistrosPatronales> {

    @Query(value = "SELECT * FROM MCLT_REGISTROS_PATRONALES "
                    + "WHERE REGISTO_PATRONAL = :REGISTO_PATRONAL"
                    + "  AND CVE_ENTIDAD_FINANCIERA = :ENTIDAD_FINANCIERA"
                    + "  AND ROWNUM <= 1 ",
            nativeQuery = true)
    McltRegistrosPatronales findByRegistroPatronal(
            @Param("REGISTO_PATRONAL") String registroPatronal,
            @Param("ENTIDAD_FINANCIERA") String entidadFinanciera);

    
}
