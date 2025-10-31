package mx.gob.imss.dpes.personaback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.entity.McltPersonaUsuarioPerfil;
import mx.gob.imss.dpes.personaback.repository.PersonaUsuarioPerfilRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PersonaUsuarioPerfilService {

    @Autowired
    private PersonaUsuarioPerfilRepository repository;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public List<McltPersonaUsuarioPerfil> obtenerOperadorEFFirma(Long cveEntidadFinanciera) throws BusinessException {
        try {
            return repository.ejecutarConsulta(cveEntidadFinanciera);
        }catch (Exception e){
            log.log(Level.SEVERE, "ERROR PersonaUsuarioPerfilService.obtenerOperadorEFFirma()", e);
            throw new PersonaException(PersonaException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }


}
