/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

/**
 *
 * @author eduardo.loyo
 */
import java.util.List;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import mx.gob.imss.dpes.personaback.repository.PersonaEFRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class GetPersonaEF extends BaseCRUDService<McltPersonaEF, McltPersonaEF, Long, Long> {

    @Autowired
    private PersonaEFRepository repository;

    public Message<McltPersonaEF> execute(String email) throws
            BusinessException {
        
        McltPersonaEF persona = null;
        List<McltPersonaEF> personaList = repository.findByCveCurp(email);
        if(personaList != null && personaList.size()>0)
            persona = personaList.get(0);
        return new Message<McltPersonaEF>(persona);   
    }

    
    public McltPersonaEF findByCurp(String curp){
        McltPersonaEF persona = null;
        List<McltPersonaEF> personaList = repository.findByCveCurp(curp);
        if(personaList != null && personaList.size()>0)
            persona = personaList.get(0);
        return persona;   
    }
    
    @Override
    public JpaSpecificationExecutor<McltPersonaEF> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltPersonaEF, Long> getJpaRepository() {
        return repository;
    }
}
