package mx.gob.imss.dpes.personaback.service;

import java.util.List;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.persona.model.Pensionado;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.repository.PersonaByCurpAndNumNssSpecification;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author luisr.rodriguez
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReadPensionadoService extends BaseCRUDService<McltPersona, McltPersona, Long, Long> {
    @Autowired
    private PersonaRepository repository;
    
    @Override
    public JpaSpecificationExecutor<McltPersona> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltPersona, Long> getJpaRepository() {
        return repository;
    }
    
    public Message<Pensionado> findByCurpOrNss(String curp, String nss){
        PersonaByCurpAndNumNssSpecification specification 
                = new PersonaByCurpAndNumNssSpecification();
        
        if(curp != null && !curp.isEmpty() && !curp.equals("NaN")) specification.setCurp(curp);
        if(nss != null && !nss.isEmpty() && !nss.equals("NaN")) specification.setNumNss(nss);
        
        Pensionado pensionado = null;
        List<McltPersona> personaList = repository.findAll(specification);
        
        if(personaList != null && !personaList.isEmpty()){
            McltPersona persona = personaList.get(0);
            if(persona.getCveEntidadFinanciera() == null 
                    && persona.getMatriculaTrabajadorImss() == null)
                pensionado = new Pensionado(String.valueOf(persona.getId()), persona.getCveCurp(), 
                persona.getNumNss(), persona.getNombre(), persona.getPrimerApellido(), 
                persona.getSegundoApellido(), persona.getCorreoElectronico(), 
                String.valueOf(persona.getTelCelular()), String.valueOf(persona.getTelLocal()));
        }
        
        return new Message<>(pensionado); 
    }
    
}
