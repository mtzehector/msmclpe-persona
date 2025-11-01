/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author antonio
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mx.gob.imss.dpes.common.exception.AlternateFlowMapper.class);
        resources.add(mx.gob.imss.dpes.common.exception.BusinessMapper.class);
        resources.add(mx.gob.imss.dpes.common.rule.MontoTotalRule.class);
        resources.add(mx.gob.imss.dpes.common.rule.PagoMensualRule.class);
        resources.add(mx.gob.imss.dpes.personaback.assembler.PersonaUsuarioPerfilAssembler.class);
        resources.add(mx.gob.imss.dpes.personaback.endpoint.PensionadoEndPoint.class);
        resources.add(mx.gob.imss.dpes.personaback.endpoint.PersonaEndPoint.class);
        resources.add(mx.gob.imss.dpes.personaback.service.ActualizaDatosContactoPensionadoService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.DelegacionPersonalEFService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.EntidadFinancieraService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.GetPersona.class);
        resources.add(mx.gob.imss.dpes.personaback.service.GetPersonaByCurpService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.GetPersonaEF.class);
        resources.add(mx.gob.imss.dpes.personaback.service.GuardarPersona.class);
        resources.add(mx.gob.imss.dpes.personaback.service.GuardarPersonaEF.class);
        resources.add(mx.gob.imss.dpes.personaback.service.PerfilPersonaService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.PersonaEFPersistenceJpaService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.PersonaPersistenceJpaService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.PromotorByEfService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.ReadPensionadoService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.RegistroPatronalJpaService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.TokenRegistroService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.UpdateDatosPensionado.class);
        resources.add(mx.gob.imss.dpes.personaback.service.UpdatePersonaAdminService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.UsuarioService.class);
        resources.add(mx.gob.imss.dpes.personaback.service.PersonaUsuarioPerfilService.class);
    }

}
