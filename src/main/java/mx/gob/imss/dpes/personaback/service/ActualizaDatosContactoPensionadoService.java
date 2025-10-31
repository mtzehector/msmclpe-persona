package mx.gob.imss.dpes.personaback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.persona.model.ActualizacionDatosPensionado;
import mx.gob.imss.dpes.interfaces.persona.model.Pensionado;
import mx.gob.imss.dpes.personaback.Exception.ActualizaDatosContactoPensionadoServiceException;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.Exception.UsuarioException;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltUsuario;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ActualizaDatosContactoPensionadoService {
    protected final Logger log = Logger.getLogger( getClass().getName() );

    @Inject
    private PersonaPersistenceJpaService personaJpaService;

    @Inject
    private UsuarioService usuarioService;

    private boolean validaContenidoPayload(ActualizacionDatosPensionado datosPensionado) {
        try {
            if (
                datosPensionado != null &&
                datosPensionado.getPensionadoDatosAnteriores() != null &&
                datosPensionado.getPensionadoDatosAnteriores().getCvePersona() != null &&
                !datosPensionado.getPensionadoDatosAnteriores().getCvePersona().trim().isEmpty() &&
                Long.parseLong(datosPensionado.getPensionadoDatosAnteriores().getCvePersona()) > 0L &&
                datosPensionado.getPensionadoDatosNuevos() != null &&
                (
                    (
                        datosPensionado.getPensionadoDatosNuevos().getCorreoElectronico() != null &&
                        !datosPensionado.getPensionadoDatosNuevos().getCorreoElectronico().trim().isEmpty()
                    ) ||
                    (
                        datosPensionado.getPensionadoDatosNuevos().getTelLocal() != null &&
                        !datosPensionado.getPensionadoDatosNuevos().getTelLocal().trim().isEmpty() &&
                        Long.parseLong(datosPensionado.getPensionadoDatosNuevos().getTelLocal()) > 0
                    ) ||
                    (
                        datosPensionado.getPensionadoDatosNuevos().getTelCelular() != null &&
                        !datosPensionado.getPensionadoDatosNuevos().getTelCelular().trim().isEmpty() &&
                        Long.parseLong(datosPensionado.getPensionadoDatosNuevos().getTelCelular()) > 0
                    )
                )
            )
                return true;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ActualizaDatosContactoPensionadoService.validaContenidoPayload - datosPensionado = ["
                + datosPensionado + "]", e);
        }

        return false;
    }

    private McltPersona obtienePersona(ActualizacionDatosPensionado datosPensionado) throws BusinessException {
        try {
            McltPersona persona = new McltPersona();
            persona.setId(Long.parseLong(datosPensionado.getPensionadoDatosAnteriores().getCvePersona()));
            return personaJpaService.personaById(persona);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ActualizaDatosContactoPensionadoService.obtienePersona - datosPensionado = [" +
                datosPensionado + "]", e);
            throw new PersonaException(PersonaException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }

    private McltUsuario obtieneUsuario(Long cveUsuario) throws BusinessException {
        try {
            Message<McltUsuario> usuario = usuarioService.buscarPorCveUsuario(cveUsuario);
            if (usuario != null && usuario.getPayload() != null)
                return usuario.getPayload();
            else
                return null;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ActualizaDatosContactoPensionadoService.obtieneUsuario - cveUsuario = [" +
                cveUsuario + "]", e);
            throw new UsuarioException(UsuarioException.ERROR_AL_EJECUTAR_CONSULTA);
        }
    }

    public void actualizaDatosContactoPensionado(ActualizacionDatosPensionado datosPensionado)
        throws BusinessException {

        try {

            if (!this.validaContenidoPayload(datosPensionado))
                throw new ActualizaDatosContactoPensionadoServiceException(
                        ActualizaDatosContactoPensionadoServiceException.ERROR_DE_MENSAJE);

            McltPersona persona = this.obtienePersona(datosPensionado);

            if (!(persona != null && persona.getId() != null))
                throw new PersonaException(PersonaException.ERROR_NO_SE_ENCONTRO_PERSONA);

            McltUsuario usuario = null;

            if (persona.getCveUsuario() != null) {
                usuario = this.obtieneUsuario(persona.getCveUsuario());

                if (!(usuario != null && usuario.getId() != null)) {
                    usuario = null;
                    persona.setCveUsuario(null);
                }
            }

            Pensionado datosNuevos = datosPensionado.getPensionadoDatosNuevos();

            // Asigna nuevos datos a los datos actuales de BD

            if (esValidoElValor(datosNuevos.getTelLocal()))
                persona.setTelLocal(BigInteger.valueOf(Long.parseLong(datosNuevos.getTelLocal())));

            if (esValidoElValor(datosNuevos.getTelCelular()))
                persona.setTelCelular(Long.parseLong(datosNuevos.getTelCelular()));

            if (this.esValidoElValor(datosNuevos.getCorreoElectronico())) {
                persona.setCorreoElectronico(datosNuevos.getCorreoElectronico());

                if(usuario != null) {
                    usuario.setNomUsuario(datosNuevos.getCorreoElectronico());

                    //Actualiza en BD los datos en usuario
                    usuarioService.updateUsuario(usuario);
                }
            }

            // Actualiza en BD los datos en persona
            personaJpaService.updatePersona(persona);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(
                Level.SEVERE,
                "ActualizaDatosContactoPensionadoService.actualizaDatosContactoPensionado - datosPensionado = [" +
                datosPensionado + "]", e);

            throw new ActualizaDatosContactoPensionadoServiceException(
                ActualizaDatosContactoPensionadoServiceException.ERROR_DESCONOCIDO_EN_EL_SERVICIO);
        }

    }

    private boolean esValidoElValor(String value){
        return value != null
            && !value.trim().isEmpty();
    }
}
