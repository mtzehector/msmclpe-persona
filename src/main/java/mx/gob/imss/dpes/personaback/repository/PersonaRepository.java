/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import java.util.List;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
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
public interface PersonaRepository extends JpaRepository<McltPersona, Long>,
        JpaSpecificationExecutor<McltPersona> {

    List<McltPersona> findByCorreoElectronico(String correoElectronico);

    List<McltPersona> findByCveCurp(String cveCurp);

    McltPersona findById(Long id);

    @Query(value = "select * from MCLT_PERSONA where cve_curp in "
            + "("
            + "select cve_curp from mclt_personal_ef where cve_personal_ef in"
            + " (select cve_personal_ef from mclt_delegacion_personal_ef "
            + "where cve_delegacion=:cve_delegacion and"
            + " cve_entidad_financiera=:cve_entidad_financiera and ind_activo=1) "
            + " and cve_estado_persona_ent_fi=1"
            + ")",
            nativeQuery = true)
    List<McltPersona> findByPromotorEnDelegacion(
            @Param("cve_delegacion") Long status,
            @Param("cve_entidad_financiera") Long name);

    @Transactional
    @Modifying
    @Query(value = "update mclt_persona set "
            + "cve_motivo_baja=:cve_motivo_baja, "
            + "cve_estado_persona_ef=:cve_estado_persona_ef "
            + "where "
            + "cve_persona=:cve_persona and "
            + "cve_entidad_financiera=:cve_entidad_financiera",
            nativeQuery = true)
    void updateBajaPromotor(
            @Param("cve_motivo_baja") Long cve_motivo_baja,
            @Param("cve_estado_persona_ef") Long cve_estado_persona_ef,
            @Param("cve_persona") Long cve_persona,
            @Param("cve_entidad_financiera") Long cve_entidad_financiera
    );
    
}
