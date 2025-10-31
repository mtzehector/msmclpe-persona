package mx.gob.imss.dpes.personaback.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.personaback.entity.McltUsuario;
import mx.gob.imss.dpes.personaback.entity.McltUsuario_;

/**
 *
 * @author luisr.rodriguez
 */
@AllArgsConstructor
public class UsuarioByNomUsuarioSpecification extends BaseSpecification<McltUsuario> {
    @Getter @Setter private String nomUsuario;

    @Override
    public Predicate toPredicate(Root<McltUsuario> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.equal(root.get(McltUsuario_.NOM_USUARIO), this.nomUsuario);
        
    }
}
