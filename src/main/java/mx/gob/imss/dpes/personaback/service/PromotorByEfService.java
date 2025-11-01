package mx.gob.imss.dpes.personaback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.model.ExistePromotorResponse;
import mx.gob.imss.dpes.personaback.repository.PersonaByEfSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PromotorByEfService extends BaseCRUDService<McltPersona, McltPersona, Long, Long> {

	@Autowired
	private PersonaRepository repository;

	public Message<ExistePromotorResponse> execute(String idEntidadFinanciera) throws BusinessException {

		ExistePromotorResponse response = new ExistePromotorResponse();

		Long countPromotores = repository.count(new PersonaByEfSpecification(idEntidadFinanciera));

		if (countPromotores > 0) {
			response.setTotalPromotores(countPromotores);
			response.setExistePromotor(true);
		}

		return new Message<ExistePromotorResponse>(response);
	}

	@Override
	public JpaSpecificationExecutor<McltPersona> getRepository() {
		return repository;
	}

	@Override
	public JpaRepository<McltPersona, Long> getJpaRepository() {
		return repository;
	}
}
