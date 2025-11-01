/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.personaback.entity.McltDelegacionPersonalEF;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import mx.gob.imss.dpes.personaback.model.BajaOperadorRQ;
import mx.gob.imss.dpes.personaback.model.BajaPromotorRQ;
import mx.gob.imss.dpes.personaback.repository.PersonaEFRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author gabriel.rios
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class GuardarPersonaEF extends BaseCRUDService<McltPersonaEF, McltPersonaEF, Long, Long> {

    @Autowired
    private PersonaEFRepository repository;

    public Message<McltPersonaEF> execute(Message<McltPersonaEF> request) throws
            BusinessException {
        // 1. La bucamos por curp
        // 1.a Preparar la especificacion
//        Collection<BaseSpecification> constraints = new ArrayList<>();
//    constraints.add( new PersonaByCurpSpecification( request.getPayload().getCurp() ) );

        // Si existe la persona la devolvemos, de lo contrario la creamos y la devolvemos
        log.log(Level.INFO, "GuardarPersonaEF.execute  request.getPayload() JGV 1 :{0}", request.getPayload().getDelegaciones());
        
        for ( McltDelegacionPersonalEF d : request.getPayload().getDelegaciones()) {
            if(d.getIsActivo()==null) {
                d.setId(null);
                d.setCvePersonalEF(request.getPayload().getId());
                d.setCveEntidadFinanciera(request.getPayload().getCveEntidadFinanciera());
                d.setIsActivo(1);
            }
        }
        log.log(Level.INFO, "GuardarPersonaEF.execute  request.getPayload() JGV 2:{0}", request.getPayload().getDelegaciones());
        McltPersonaEF saved = null;
        try {
            log.log(Level.INFO, "save(request.getPayload()) JGV {0}", request.getPayload());
            McltPersonaEF personaEF  = repository.findByCurp(request.getPayload().getCveCurp());
            if(personaEF != null)
                request.getPayload().setId(personaEF.getId());
            saved = save(request.getPayload());
        } catch (Exception e) {

            log.log(Level.SEVERE, "ERROR GuardarPersonaEF.execute JGV {0}", e);
            throw new UnknowException();
        }
        return new Message<>(saved);
    }

    @Override
    public JpaSpecificationExecutor<McltPersonaEF> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltPersonaEF, Long> getJpaRepository() {
        return repository;
    }
    
    public void bajaPromotorService(BajaPromotorRQ persona){
        repository.updateBajaPromotor(persona.getEstadoPersonaEf().getId(), 
                                      persona.getCvePersonalEf(), 
                                      persona.getCveEntidadFinanciera());
    }
    
    public void bajaOperadorService(BajaOperadorRQ persona){
        List<McltPersonaEF> personaList = repository.findByCveCurp(persona.getCveCurp());
        if(personaList.size() == 1 ){
            McltPersonaEF personaEF = personaList.get(0);
            repository.updateBajaPromotor(persona.getEstadoPersonaEf().getId(), 
                                      personaEF.getId(), 
                                      persona.getCveEntidadFinanciera());
        }
    }

}
