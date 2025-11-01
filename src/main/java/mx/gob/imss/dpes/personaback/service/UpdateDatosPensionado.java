package mx.gob.imss.dpes.personaback.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.model.PensionadoSipreModel;

/**
 * Servicio de actualización de nombre pensionado
 * @author luisr.rodriguez
 */
@Provider
public class UpdateDatosPensionado extends ServiceDefinition<PensionadoSipreModel, PensionadoSipreModel> {
    @Inject
    private PersonaPersistenceJpaService personaJpaService;

    @Override
    public Message<PensionadoSipreModel> execute(Message<PensionadoSipreModel> request) throws BusinessException {
        McltPersona persona = new McltPersona();
        persona.setId(request.getPayload().getId());
        McltPersona personaToUpdate = personaJpaService.personaById(persona);
            
        PensionadoSipreModel datosNuevos = request.getPayload();
        
        //Nombre
        if(valueIsValid(datosNuevos.getNombre()) 
                && !datosNuevos.getNombre().equals(personaToUpdate.getNombre()))
            personaToUpdate.setNombre(datosNuevos.getNombre());
        
        //Apellidos
        if(valueIsValid(datosNuevos.getPrimerApellido()) && 
                !datosNuevos.getPrimerApellido().equals(personaToUpdate.getPrimerApellido()))
            personaToUpdate.setPrimerApellido(datosNuevos.getPrimerApellido());
        
        if(valueIsValid(datosNuevos.getSegundoApellido()) && !datosNuevos.getSegundoApellido()
                .equals(personaToUpdate.getSegundoApellido()))
            personaToUpdate.setSegundoApellido(datosNuevos.getSegundoApellido());
        
       personaJpaService.updatePersona(personaToUpdate);
        log.log(Level.INFO, "Actualización de datos Pensionado");
        
        return request;
    }
    
    private boolean valueIsValid(String value){
        return value != null 
                && !value.isEmpty();
    }
    
    
}
