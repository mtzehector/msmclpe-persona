/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.model.BajaOperadorRQ;
import mx.gob.imss.dpes.personaback.model.BajaPromotorRQ;
import mx.gob.imss.dpes.personaback.repository.PersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PersonaPersistenceJpaService {

    protected final Logger log = Logger.getLogger( getClass().getName() );

    @Autowired
    private PersonaRepository repository;

    public List<McltPersona> obtienePromotoresParaAsignar(Long cveDelegacion,
            Long cveEntidadFinanciera) {

        return repository.findByPromotorEnDelegacion(cveDelegacion, cveEntidadFinanciera);

    }

    public void bajaPromotorService(BajaPromotorRQ persona) {
        repository.updateBajaPromotor(
                persona.getBaja(),
                persona.getEstadoPersonaEf().getId(),
                persona.getId(),
                persona.getCveEntidadFinanciera());
    }
    
    public void bajaOperadorService(BajaOperadorRQ persona) {
        repository.updateBajaPromotor(
                persona.getBaja(),
                persona.getEstadoPersonaEf().getId(),
                persona.getId(),
                persona.getCveEntidadFinanciera());
    }

    public McltPersona updatePersona(McltPersona persona) throws BusinessException {
        try {
            return repository.save(persona);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::PersonaPersistenceJpaService.updatePersona - persona = [" + persona +
                "]", e);
            throw new PersonaException(PersonaException.ERROR_DE_ESCRITURA_EN_BD);
        }
    }

    public McltPersona personaById(McltPersona persona) throws BusinessException {
        try {
            return repository.findById(persona.getId());
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR::PersonaPersistenceJpaService.personaById - persona = [" + persona +
                "]", e);
            throw new PersonaException(PersonaException.ERROR_AL_EJECUTAR_CONSULTA);
        }

    }
}
