/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import mx.gob.imss.dpes.personaback.entity.MclcEntidadFinanciera;
import mx.gob.imss.dpes.personaback.repository.EntidadFinancieraRepository;

/**
 *
 * @author juanf.barragan
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class EntidadFinancieraService {
    
    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;
    
    public MclcEntidadFinanciera findbyRPandCVE(String registroPatronal, String entidadFinanciera){
        return entidadFinancieraRepository.findbyRPandCVE(registroPatronal, entidadFinanciera);
    }
}
