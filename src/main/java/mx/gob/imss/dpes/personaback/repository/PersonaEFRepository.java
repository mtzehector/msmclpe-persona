/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import java.util.List;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author antonio
 */
public interface PersonaEFRepository extends JpaRepository<McltPersonaEF, Long>, 
        JpaSpecificationExecutor<McltPersonaEF> {
    List<McltPersonaEF> findByCveCurp(String  cveCurp);

    @Transactional
    @Modifying
    @Query(value = "update mclt_personal_ef set" +
                   "       cve_estado_persona_ent_fi = :cve_estado_persona_ef" +
                   " where cve_personal_ef = :cve_personal_ef and" +
                   "       cve_entidad_financiera = :cve_entidad_financiera",
            nativeQuery = true)
    void updateBajaPromotor(
            @Param("cve_estado_persona_ef") Long cve_estado_persona_ef,
            @Param("cve_personal_ef") Long cve_personal_ef,
            @Param("cve_entidad_financiera") Long cve_entidad_financiera
    );
    
    @Query(value = "SELECT * FROM mclt_personal_ef " +
                   " WHERE cve_curp = :cve_curp",
            nativeQuery = true)
    McltPersonaEF findByCurp(
            @Param("cve_curp") String cveCurp);
    
}

