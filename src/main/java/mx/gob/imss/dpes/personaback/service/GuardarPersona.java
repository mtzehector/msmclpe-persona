/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
//import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
//import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
//import mx.gob.imss.dpes.personaback.entity.McltPersona_oldie;
//import mx.gob.imss.dpes.personaback.repository.PersonaByCurpSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author antonio
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class GuardarPersona extends BaseCRUDService<McltPersona, McltPersona, Long, Long> {

    @Autowired
    private PersonaRepository repository;

    public Message<McltPersona> execute(Message<McltPersona> request) throws
            BusinessException {
        // 1. La bucamos por curp
        // 1.a Preparar la especificacion
//        Collection<BaseSpecification> constraints = new ArrayList<>();
//    constraints.add( new PersonaByCurpSpecification( request.getPayload().getCurp() ) );
        
        // Si existe la persona la devolvemos, de lo contrario la creamos y la devolvemos
        log.log(Level.INFO, ">>> GuardarPersona.execute  request.getPayload()="+request.getPayload());
        McltPersona saved = null;
        try{
            saved = save(request.getPayload());
        }
        catch(Exception e){
                e.printStackTrace();
                log.log(Level.SEVERE, ">>> ERROR GuardarPersona.execute  getMessage="+e.getMessage());
        
                throw new UnknowException();
        }
        Message<McltPersona> message = new Message<>(saved);
        return message;
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
