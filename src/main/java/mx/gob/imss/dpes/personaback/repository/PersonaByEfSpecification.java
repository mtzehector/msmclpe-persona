package mx.gob.imss.dpes.personaback.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersona_;

/**
 *
 * @author juc
 */
public class PersonaByEfSpecification extends BaseSpecification<McltPersona> {

	private final String idEntidadFinanciera;

	public PersonaByEfSpecification(String idEntidadFinanciera) {
		this.idEntidadFinanciera = idEntidadFinanciera;

	}

	@Override
	public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		return cb.and(cb.equal(root.get(McltPersona_.CVE_ENTIDAD_FINANCIERA), this.idEntidadFinanciera));

	}

}
