/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.personaback.entity.McltRegistrosPatronales;
import mx.gob.imss.dpes.personaback.repository.RegistrosPatronalesRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author marco.gutierrez
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class RegistroPatronalJpaService {

    @Autowired
    private RegistrosPatronalesRepository repository;

    public McltRegistrosPatronales registroPatronalByRP(String registroPatronal, String entidadFinanciera) {
        return repository.findByRegistroPatronal(registroPatronal,entidadFinanciera);

    }
}
