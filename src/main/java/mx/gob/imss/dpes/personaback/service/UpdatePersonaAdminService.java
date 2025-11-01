/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltUsuario;
import mx.gob.imss.dpes.personaback.model.UpdateAdminRequest;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.personaback.repository.UsuarioRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juanf.barragan
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class UpdatePersonaAdminService extends BaseCRUDService<McltPersona, McltPersona, Long, Long>{

    @Autowired
    private PersonaRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public UpdateAdminRequest execute(UpdateAdminRequest request) throws BusinessException{
        try{
            log.log(Level.INFO, "UpdateAdminRequest busqueda de persona por curp {0}", request.getCurpAnterior());
            McltPersona persona = repository.findByCveCurp(request.getCurpAnterior()).get(0);
            log.log(Level.INFO, "UpdateAdminRequest la persona recuperada es {0}", persona);
            persona.setCorreoElectronico(request.getCorreoNuevo());
            persona.setCveCurp(request.getCurpNuevo());
            persona.setNombre(request.getNombre());
            persona.setPrimerApellido(request.getPrimerApellido());
            persona.setSegundoApellido(request.getSegundoApellido());
            persona.setRfc(request.getRfc());
            request.setUsuarioId(persona.getCveUsuario());
            log.log(Level.INFO, "UpdateAdminRequest la persona a guardar es {0}", persona);
            McltPersona saved = repository.save(persona);
            log.log(Level.INFO, "UpdateAdminRequest la persona guardada es {0}", saved);
        }catch(Exception e){
            e.printStackTrace();
                log.log(Level.SEVERE, ">>> ERROR GuardarPersona.execute  getMessage="+e.getMessage());
        
                throw new UnknowException();
        }
        
        
        return request;
    }
    
    public void inhabilitarUsuario(String curp) throws BusinessException{
        try {
            List<McltPersona> listPersonas = repository.findByCveCurp(curp);
            if (listPersonas != null && listPersonas.size() == 1 && listPersonas.get(0).getCveUsuario() != null) {
                McltUsuario usuario = usuarioRepository.findById(listPersonas.get(0).getCveUsuario());
                if (usuario != null) {
                    usuario.setIndActivo(0);
                    usuario.setBajaRegistro(new Date());
                    usuarioRepository.save(usuario);
                }
            }
        }catch (Exception e){
            log.log(Level.SEVERE,"ERROR UpdatePersonaAdminService.inhabilitarUsuario() curp [" + curp + "]");
            throw new PersonaException(PersonaException.ERROR_DE_ESCRITURA_EN_BD);
        }
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
