/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import java.util.List;
import mx.gob.imss.dpes.personaback.entity.MclcDelegacion;
import mx.gob.imss.dpes.personaback.entity.McltDelegacionPersonalEF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author antonio
 */
public interface DelegacionRepository extends JpaRepository<MclcDelegacion, Long>, 
        JpaSpecificationExecutor<McltDelegacionPersonalEF> {
    MclcDelegacion findById(Long id);

}

