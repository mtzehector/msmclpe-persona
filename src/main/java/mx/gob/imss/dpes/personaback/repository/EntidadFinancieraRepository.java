/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import mx.gob.imss.dpes.personaback.entity.MclcEntidadFinanciera;
/**
 *
 * @author juanf.barragan
 */
public interface EntidadFinancieraRepository extends JpaRepository<MclcEntidadFinanciera, Long>, JpaSpecificationExecutor<MclcEntidadFinanciera> {

    @Query(value = "SELECT * FROM MCLC_ENTIDAD_FINANCIERA " +
                   " WHERE CVE_ENTIDAD_FINANCIERA = :ENTIDAD_FINANCIERA " +
                   "   AND CVE_REG_PATRONAL = :REGISTO_PATRONAL" +
                   "   AND ROWNUM <= 1 ",
            nativeQuery = true)
    MclcEntidadFinanciera findbyRPandCVE(
            @Param("REGISTO_PATRONAL") String registroPatronal,
            @Param("ENTIDAD_FINANCIERA") String entidadFinanciera);
}
