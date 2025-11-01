/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.gob.imss.dpes.personaback.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.personaback.entity.McltTokenRegistroUsuario;
import mx.gob.imss.dpes.personaback.entity.McltTokenRegistroUsuario_;

/**
 *
 * @author luisr.rodriguez
 */
@AllArgsConstructor
public class TokenRegistroByNomUsuarioSpecification extends BaseSpecification<McltTokenRegistroUsuario> {
    @Getter @Setter private String nomUsuario;

    @Override
    public Predicate toPredicate(Root<McltTokenRegistroUsuario> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.equal(root.get(McltTokenRegistroUsuario_.NOM_USUARIO), this.nomUsuario);
    }
    
}
