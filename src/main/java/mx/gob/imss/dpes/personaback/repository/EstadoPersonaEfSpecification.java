/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.personaback.entity.MclcEstadoPersonaEf;
import mx.gob.imss.dpes.personaback.entity.MclcEstadoPersonaEf_;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersona_;

/**
 *
 * @author eduardo.loyo
 */
public class EstadoPersonaEfSpecification extends BaseSpecification<McltPersona> {

    private final Long cveEstadoPersonaEf;

    public EstadoPersonaEfSpecification(Long cveEstadoPersonaEf) {
        this.cveEstadoPersonaEf = cveEstadoPersonaEf;
    }

    @Override
    public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> query, 
            CriteriaBuilder cb) {
        
        Join<McltPersona, MclcEstadoPersonaEf> estado = root.join(McltPersona_.cveEstadoPersonaEf);
        return cb.equal(estado.get(MclcEstadoPersonaEf_.id),this.cveEstadoPersonaEf);
//        return cb.equal(root.join(McltPersona_.cveEstadoPersonaEf)
//                .get(MclcEstadoPersonaEf_.id), this.cveEstadoPersonaEf);
        

//        return null;
    }

}
