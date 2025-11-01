package mx.gob.imss.dpes.personaback.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.persona.model.ActualizacionDatosPensionado;
import mx.gob.imss.dpes.interfaces.persona.model.Pensionado;
import mx.gob.imss.dpes.personaback.Exception.ActualizaDatosContactoPensionadoServiceException;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.model.PensionadoSipreModel;
import mx.gob.imss.dpes.personaback.service.*;
import org.eclipse.microprofile.openapi.annotations.Operation;

/**
 *
 * @author luisr.rodriguez
 */
@ApplicationScoped
@Path("/pensionado")
public class PensionadoEndPoint extends BaseGUIEndPoint<McltPersona, McltPersona, McltPersona>{
    @Inject
    private ReadPensionadoService readPensionadoService;
    @Context
    private UriInfo uriInfo;
    @Inject 
    private UpdateDatosPensionado actualizarDatosPensionado;
    @Inject
    private ActualizaDatosContactoPensionadoService actualizaDatosContactoPensionadoService;
    @Inject
    private PerfilPersonaService perfilPersonaService;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener pensionado por curp o nss",
            description = "Obtener pensionado por curp o nss")
    @Path("/getByCurpOrNss/{curp}/{nss}")
    public Response getByCurpOrNss(@PathParam("curp") String curp, @PathParam("nss") String numNss) 
        throws BusinessException {
        log.log(Level.INFO, ">>> personaBack PensionadoEndPoint.loadByCurpOrNss "
            + "Request: {0}", "curp: " + curp + " - " + " numNss: " + numNss);
        Message<Pensionado> response = readPensionadoService.findByCurpOrNss(curp, numNss);
        log.log(Level.INFO, ">>> personaBack PensionadoEndPoint.load response: {0}", response.getPayload());
        if (response.getPayload() == null) return toResponse(new Message(new Pensionado()));
        return Response.ok(uriInfo.getAbsolutePath()).entity(response.getPayload()).build();
    }

    private boolean esValidoMensajeUpdatePensionadoDatosContacto(
        ActualizacionDatosPensionado datosPensionado) {
        try {
            if(
                datosPensionado != null &&
                datosPensionado.getCurp() != null &&
                !datosPensionado.getCurp().trim().isEmpty() &&
                datosPensionado.getNumSesion() != null &&
                !datosPensionado.getNumSesion().trim().isEmpty() &&
                Long.parseLong(datosPensionado.getNumSesion()) > 0L &&
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
            "PensionadoEndPoint.esValidoMensajeUpdatePensionadoDatosContacto - datosPensionado = ["
            + datosPensionado + "]", e);
        }

        return false;
    }
    
    @PUT
    @Path("/actualizarDatosContacto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePensionadoDatosContacto(@Context HttpHeaders headers,
        ActualizacionDatosPensionado datosPensionado) {

        try {

            if(
                !esValidoMensajeUpdatePensionadoDatosContacto(datosPensionado)
            )
                return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new ActualizaDatosContactoPensionadoServiceException(
                        ActualizaDatosContactoPensionadoServiceException.ERROR_DE_MENSAJE), null));

            String tokenSeguridad = this.obtenerTokenSeguridad(headers.getRequestHeaders());
            datosPensionado.setToken(tokenSeguridad);

            perfilPersonaService.execute(new Message<>(datosPensionado));

            actualizaDatosContactoPensionadoService.actualizaDatosContactoPensionado(datosPensionado);

            return Response.accepted().build();
        } catch (BusinessException e) {
            log.log(Level.SEVERE,
            "ERROR::PensionadoEndPoint.updatePensionadoDatosContacto: Ocurrio un error desconocido al " +
                "actualizar datos del pensionado - datosPensionado = [" + datosPensionado + "]", e);
            return toResponse(new Message(
                    null,
                    ServiceStatusEnum.EXCEPCION,
                    e,
                    null
            ));
        } catch (Exception e) {
            //e.printStackTrace();
            //return Response.notModified().build();
            log.log(Level.SEVERE,
                "ERROR::PensionadoEndPoint.updatePensionadoDatosContacto: Ocurrio un error desconocido al " +
                    "actualizar datos del pensionado - datosPensionado = [" + datosPensionado + "]", e);
            return toResponse(new Message(
                    null,
                    ServiceStatusEnum.EXCEPCION,
                    new PersonaException(PersonaException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                    null
            ));
        }
    }
    
    @PUT
    @Path("/actualizarNombre")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNombre(PensionadoSipreModel request) throws BusinessException {
        log.log(Level.INFO, ">>> personaBack PensionadoEndPoint.updateNombre request {0}", request);
        ServiceDefinition[] steps = {actualizarDatosPensionado};
        
        Message<PensionadoSipreModel> response = actualizarDatosPensionado
                .executeSteps(steps, new Message<>(request));
        
        if (Message.isException(response)) {
            log.log(Level.INFO, "Response catch: {0}", response);
            return toResponse(response);
        }
        
        return toResponse(response);
    }

}
