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
 * @author antonio
 */
public class PersonaByCurpSpecification extends BaseSpecification<McltPersona> {
    
    private final String curp;
   
    
    public PersonaByCurpSpecification(String curp) {
        this.curp = curp;
       
    }
    
    @Override
    public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
         Join<McltPersona, MclcEstadoPersonaEf> estado = root.join(McltPersona_.cveEstadoPersonaEf);
         
//    return cb.and(cb.equal(root.get(McltPersona_.cveCurp), this.curp),
//            cb.equal(root.get(McltPersona_.cveEstadoPersonaEf), MclcEstadoPersonaEf_.id));
    
     return cb.and(cb.equal(root.get(McltPersona_.cveCurp), this.curp),
            cb.equal(root.get(McltPersona_.cveEstadoPersonaEf), estado.get(MclcEstadoPersonaEf_.id)));
     
//        return cb.equal(cb.equal(join.get(McltPersona_.cveEstadoPersonaEf)
//                .get(MclcEstadoPersonaEf.id), pensionado.getSexo().getId())root.get(McltPersona_.cveCurp), this.curp);
        
    
//    public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> cq,
//            CriteriaBuilder cb) {
//        return cb.equal(root.get(McltPersona_.cveCurp), this.curp);
//        
    
    }

}
