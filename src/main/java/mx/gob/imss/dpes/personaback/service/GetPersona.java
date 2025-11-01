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
//import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
//import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
//import mx.gob.imss.dpes.personaback.repository.EstadoPersonaEfSpecification;
//import mx.gob.imss.dpes.personaback.repository.PersonaByCurpSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class GetPersona extends BaseCRUDService<McltPersona, McltPersona, Long, Long> {

    @Autowired
    private PersonaRepository repository;

    public Message<McltPersona> execute(Long id) throws  BusinessException {
        McltPersona persona = repository.findById(id);
        return new Message<McltPersona>(persona);   
    }

    public McltPersona findByEmail(String email){
        McltPersona persona = null;
        List<McltPersona> personaList = repository.findByCorreoElectronico(email);
        if(personaList != null && personaList.size()>0)
            persona = personaList.get(0);
        return persona;   
    }
    
    public McltPersona findByCurp(String email){
        McltPersona persona = null;
        List<McltPersona> personaList = repository.findByCveCurp(email);
        if(personaList != null && personaList.size()>0)
            persona = personaList.get(0);
        return persona;   
    }
    
    public McltPersona findById(Long id){
        McltPersona persona = repository.findById(id);
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
