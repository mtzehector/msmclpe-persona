package mx.gob.imss.dpes.personaback.service;

import mx.gob.imss.dpes.common.enums.PerfilUsuarioEnum;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.MensajeException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.persona.model.ActualizacionDatosPensionado;
import mx.gob.imss.dpes.interfaces.persona.model.Pensionado;
import mx.gob.imss.dpes.personaback.entity.PerfilPersona;
import mx.gob.imss.dpes.personaback.repository.PerfilPersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PerfilPersonaService extends ServiceDefinition<ActualizacionDatosPensionado, ActualizacionDatosPensionado> {

    @Autowired
    private PerfilPersonaRepository perfilPersonaRepository;

    private boolean esValidoMensajeExecute(
            Message<ActualizacionDatosPensionado> request) {
        try {
            if(
                request != null &&
                request.getPayload() != null &&
                request.getPayload().getNumSesion() != null &&
                !request.getPayload().getNumSesion().trim().isEmpty() &&
                request.getPayload().getToken() != null &&
                !request.getPayload().getToken().trim().isEmpty() &&
                request.getPayload().getCurp() != null &&
                !request.getPayload().getCurp().trim().isEmpty() &&
                request.getPayload().getPensionadoDatosAnteriores() != null &&
                request.getPayload().getPensionadoDatosAnteriores().getCvePersona() != null &&
                !request.getPayload().getPensionadoDatosAnteriores().getCvePersona().trim().isEmpty() &&
                request.getPayload().getPensionadoDatosNuevos() != null
            )
                return true;
        } catch (Exception e) {
            log.log(Level.SEVERE,
            "PensionadoEndPoint.esValidoMensajeUpdatePensionadoDatosContacto - datosPensionado = [" +
                    request + "]", e);
        }
        return false;
    }

    @Override
    public Message<ActualizacionDatosPensionado> execute(Message<ActualizacionDatosPensionado> request)
        throws BusinessException {

        try {
            if(
                !esValidoMensajeExecute(request)
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            PerfilPersona perfilPersona = perfilPersonaRepository.
                    buscarPerfilPersonaPorToken(request.getPayload().getToken());

            if(
                !(
                    perfilPersona != null &&
                    (PerfilUsuarioEnum.OPERADOR_IMSS.getTipo().equals(((long) perfilPersona.getCvePerfil())) ||
                        PerfilUsuarioEnum.ADMINISTRADOR_EF.getTipo().equals(((long) perfilPersona.getCvePerfil()))) &&
                    perfilPersona.getNumSesion().equals(request.getPayload().getNumSesion()) &&
                    perfilPersona.getCveCurp().equals(request.getPayload().getCurp())
                )
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            perfilPersona = perfilPersonaRepository.
                    buscarPerfilPersonaPorId(request.getPayload().getPensionadoDatosAnteriores().getCvePersona());

            if(
                !(
                    perfilPersona != null &&
                    PerfilUsuarioEnum.PENSIONADO.getTipo().equals(((long) perfilPersona.getCvePerfil()))
                )
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            Pensionado pensionadoDatosAnteriores = request.getPayload().getPensionadoDatosAnteriores();
            Pensionado pensionadoDatosNuevos = request.getPayload().getPensionadoDatosNuevos();

            if (
                !(
                    pensionadoDatosNuevos.getCorreoElectronico() == null ||
                    (
                        !pensionadoDatosNuevos.getCorreoElectronico().equals(
                            pensionadoDatosAnteriores.getCorreoElectronico()) &&
                        (perfilPersona.getCorreoElectronico() == null ||
                            (perfilPersona.getCorreoElectronico().equals(pensionadoDatosAnteriores.getCorreoElectronico())))
                    )
                )
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            if (
                !(
                    pensionadoDatosNuevos.getTelCelular() == null ||
                    (
                        !pensionadoDatosNuevos.getTelCelular().equals(
                            pensionadoDatosAnteriores.getTelCelular()) &&
                        (perfilPersona.getTelCelular() == null ||
                            (perfilPersona.getTelCelular().equals(pensionadoDatosAnteriores.getTelCelular())))
                    )
                )
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            if (
                !(
                    pensionadoDatosNuevos.getTelLocal() == null ||
                    (
                        !pensionadoDatosNuevos.getTelLocal().equals(
                            pensionadoDatosAnteriores.getTelLocal()) &&
                        (perfilPersona.getTelLocal() == null ||
                            (perfilPersona.getTelLocal().equals(pensionadoDatosAnteriores.getTelLocal())))
                    )
                )
            )
                throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);

            return request;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                    "ERROR PerfilPersonaService.execute(" +
                            request + ")", e);
        }

        throw new MensajeException(MensajeException.MENSAJE_DE_SOLICITUD_INCORRECTO);
    }
}
