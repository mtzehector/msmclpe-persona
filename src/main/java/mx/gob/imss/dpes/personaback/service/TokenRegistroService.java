/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.gob.imss.dpes.personaback.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.personaback.Exception.TokenRegistroException;
import mx.gob.imss.dpes.personaback.entity.McltTokenRegistroUsuario;
import mx.gob.imss.dpes.personaback.repository.TokenRegistroByNomUsuarioSpecification;
import mx.gob.imss.dpes.personaback.repository.TokenRegistroRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisr.rodriguez
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class TokenRegistroService {

    protected final Logger log = Logger.getLogger( getClass().getName() );

    @Autowired
    private TokenRegistroRepository tokenRegistroRepository;
    
    public Message<McltTokenRegistroUsuario> findByNomUsuario(String nomUsuario) throws BusinessException {
        try {
            TokenRegistroByNomUsuarioSpecification specification
                    = new TokenRegistroByNomUsuarioSpecification(nomUsuario);
            List<McltTokenRegistroUsuario> tokenRegistroList
                    = tokenRegistroRepository.findAll(specification);
            McltTokenRegistroUsuario tokenRegistro = null;

            if (tokenRegistroList != null && !tokenRegistroList.isEmpty()) {
                tokenRegistro = tokenRegistroList.get(0);
            }

            return new Message<>(tokenRegistro);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::TokenRegistroService.findByNomUsuario {0}", e);
            throw new TokenRegistroException(TokenRegistroException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }
    
    public Message<McltTokenRegistroUsuario> updateTokenRegistro(McltTokenRegistroUsuario tokenRegistro) throws BusinessException {
        try {
            return new Message<>(tokenRegistroRepository.save(tokenRegistro));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::TokenRegistroService.updateTokenRegistro {0}", e);
            throw new TokenRegistroException((TokenRegistroException.ERROR_DE_ESCRITURA_EN_BD));
        }
    }
}
