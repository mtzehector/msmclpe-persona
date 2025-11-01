package mx.gob.imss.dpes.personaback.service;

import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.Exception.UsuarioException;
import mx.gob.imss.dpes.personaback.entity.McltUsuario;
import mx.gob.imss.dpes.personaback.model.UpdateAdminRequest;
import mx.gob.imss.dpes.personaback.repository.UsuarioByNomUsuarioSpecification;
import mx.gob.imss.dpes.personaback.repository.UsuarioRepository;
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
public class UsuarioService extends BaseCRUDService<McltUsuario, McltUsuario, Long, Long> {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public JpaSpecificationExecutor<McltUsuario> getRepository() {
     return usuarioRepository;   
    }
    @Override
    public JpaRepository<McltUsuario, Long> getJpaRepository() {
        return usuarioRepository; 
    }
    
    public Message<McltUsuario> findByNomUsuario(String nomUsuario){
        List<McltUsuario> usuarioList = usuarioRepository
                .findAll(new UsuarioByNomUsuarioSpecification(nomUsuario));
        McltUsuario usuario = null;
        
        if(usuarioList != null && !usuarioList.isEmpty()){
            usuario = usuarioList.get(0);
        }
        return new Message<>(usuario);
    }
    
    
    public Message<McltUsuario> updateUsuario(McltUsuario usuario) throws BusinessException {
        try {
            McltUsuario usuarioUpdated = usuarioRepository.save(usuario);
            return new Message<>(usuarioUpdated);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::UsuarioService.updateUsuario - usuario = [" + usuario + "]", e);
            throw new UsuarioException(UsuarioException.ERROR_DE_ESCRITURA_EN_BD);
        }
    }
    
    public UpdateAdminRequest execute (UpdateAdminRequest request) throws BusinessException{
        try {
            McltUsuario usuario = usuarioRepository.findById(request.getUsuarioId());
            if (usuario != null) {
                usuario.setNomUsuario(request.getCorreoNuevo());
                usuarioRepository.save(usuario);
            }
            return request;
        }catch (Exception e){
            log.log(Level.SEVERE, "ERROR UsuarioService.execute() UpdateAdminRequest[ " + request + "]");
            throw new UsuarioException(UsuarioException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }

    public Message<McltUsuario> buscarPorCveUsuario(Long cveUsuario) throws BusinessException {
        try {
            return new Message<>(usuarioRepository.findById(cveUsuario));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::UsuarioService.buscarPorCveUsuario cveUsuario = [" +
                cveUsuario + "]", e);
            throw new UsuarioException(UsuarioException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }
    
}
