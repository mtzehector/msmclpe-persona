package mx.gob.imss.dpes.personaback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import mx.gob.imss.dpes.personaback.repository.PersonaEFRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisr.rodriguez
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PersonaEFPersistenceJpaService {
    @Autowired
    private PersonaEFRepository repository;
    
    public McltPersonaEF updatePersona(McltPersonaEF personaEF) {
        return repository.save(personaEF);
    }
    
    public McltPersonaEF findByCurp(McltPersonaEF personaEF){
        return repository.findByCurp(personaEF.getCveCurp());
    }
}
