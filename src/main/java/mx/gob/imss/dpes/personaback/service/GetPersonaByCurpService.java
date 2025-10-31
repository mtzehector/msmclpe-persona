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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
//import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
//import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
//import mx.gob.imss.dpes.personaback.repository.EstadoPersonaEfSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaByCurpSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class GetPersonaByCurpService extends BaseCRUDService<McltPersona, McltPersona, Long, Long> {

    @Autowired
    private PersonaRepository repository;

    public Message<McltPersona> execute(String curp) throws
            BusinessException {
        
        // 1. La bucamos por curp 
        // 1.a Preparar la especificacion
        Collection<BaseSpecification> constraints = new ArrayList<>();
        constraints.add(new PersonaByCurpSpecification(curp));
        McltPersona persona = null;
        List<McltPersona> list = repository.findAll(new PersonaByCurpSpecification(curp));
        if(list!=null && list.size()>0){
            persona = list.get(0);
        }
        return new Message<McltPersona>(persona);
    }

    public McltPersona findByCurp(String curp) throws BusinessException{
        McltPersona persona = new McltPersona();
        List<McltPersona> personaList = repository.findByCveCurp(curp);
        if(personaList != null && personaList.size()>0){
          persona = personaList.get(0);  
        }  
        log.log(Level.INFO, ">>>personaBack", persona);
        return persona;
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
