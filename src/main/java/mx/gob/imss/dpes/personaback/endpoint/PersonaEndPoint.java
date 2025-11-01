/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.endpoint;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.DatabaseException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.personaback.Exception.PersonaException;
import mx.gob.imss.dpes.personaback.assembler.PersonaUsuarioPerfilAssembler;
import mx.gob.imss.dpes.personaback.entity.McltDelegacionPersonalEF;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import mx.gob.imss.dpes.personaback.entity.McltPersonaUsuarioPerfil;
import mx.gob.imss.dpes.personaback.entity.McltRegistrosPatronales;
import mx.gob.imss.dpes.personaback.model.BajaPromotorRQ;
import mx.gob.imss.dpes.personaback.service.DelegacionPersonalEFService;
import mx.gob.imss.dpes.personaback.service.GetPersona;
import mx.gob.imss.dpes.personaback.service.GetPersonaByCurpService;
import mx.gob.imss.dpes.personaback.service.GetPersonaEF;
import mx.gob.imss.dpes.personaback.service.GuardarPersona;
import mx.gob.imss.dpes.personaback.service.GuardarPersonaEF;
import mx.gob.imss.dpes.personaback.service.PersonaPersistenceJpaService;
import mx.gob.imss.dpes.personaback.service.PersonaUsuarioPerfilService;
import mx.gob.imss.dpes.personaback.service.PromotorByEfService;
import mx.gob.imss.dpes.personaback.service.RegistroPatronalJpaService;
import mx.gob.imss.dpes.personaback.entity.MclcEntidadFinanciera;
import mx.gob.imss.dpes.personaback.entity.MclcEstadoPersonaEf;
import mx.gob.imss.dpes.personaback.model.BajaOperadorRQ;
import mx.gob.imss.dpes.personaback.model.UpdateAdminRequest;
import mx.gob.imss.dpes.personaback.repository.PersonaEFRepository;
import mx.gob.imss.dpes.personaback.service.EntidadFinancieraService;
import mx.gob.imss.dpes.personaback.service.PersonaEFPersistenceJpaService;
import mx.gob.imss.dpes.personaback.service.UpdatePersonaAdminService;
import mx.gob.imss.dpes.personaback.service.UsuarioService;

/**
 *
 * @author antonio
 */
@ApplicationScoped
@Path("/persona")
public class PersonaEndPoint extends BaseGUIEndPoint<McltPersona, McltPersona, McltPersona> {

    @Context
    private UriInfo uriInfo;
    @Inject
    private GuardarPersona service;
    @Inject
    private GuardarPersonaEF serviceEF;
    @Inject
    private GetPersonaByCurpService getPersona;
    @Inject
    private GetPersona getPersonaUniqueByField;
    @Inject
    private GetPersonaEF getPersonaEFUniqueByField;
    @Inject
    private PromotorByEfService promotorByEfService;

    @Inject
    private DelegacionPersonalEFService delegacionPersonalEFService;

    @Inject
    private PersonaPersistenceJpaService personaJpaService;
    
    @Inject
    private PersonaEFPersistenceJpaService personaEFJpaService;
    
    @Inject
    private RegistroPatronalJpaService registroPatronalJpaService;
    
    @Inject
    private EntidadFinancieraService entidadFinancieraService;
    
    @Inject
    private UpdatePersonaAdminService updatePersonaAdminService;
    
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private PersonaUsuarioPerfilService perfilService;
    @Inject
    private PersonaUsuarioPerfilAssembler personaAssembler;
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response create(McltPersona persona) throws BusinessException {
        log.log(Level.INFO, ">>> personaBack.PersonaEndPoint. create McltPersona=" + persona);

        Message<McltPersona> execute = service.execute(new Message<>(persona));
        Response response = toResponse(execute);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/personaEF")
    public Response createPersonaEF(McltPersonaEF persona) throws BusinessException {
        log.log(Level.INFO, ">>> PersonaEndPoint.createPersonaEF McltPersonaEF=" + persona);
        Message<McltPersonaEF> execute = serviceEF.execute(new Message<>(persona));
        execute.getPayload().setDelegaciones(persona.getDelegaciones());
        log.log(Level.INFO, ">>> delegacionPersonalEFService.execute {0}", execute.getPayload());
        if (!execute.getPayload().getDelegaciones().isEmpty()) {
            LinkedList<McltDelegacionPersonalEF> savedList = delegacionPersonalEFService.execute(execute.getPayload());
        }
        return toResponse(execute);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delegacionpersonaEF")
    public Response createDelegacionPersonalEF(List<McltDelegacionPersonalEF> delegacionPersonalEFList) throws BusinessException {
        log.log(Level.INFO, ">>> personaBack PersonaEndPoint URL=delegacionpersonaEFlist List<McltDelegacionPersonalEF=" + delegacionPersonalEFList);
        LinkedList<McltDelegacionPersonalEF> savedList = delegacionPersonalEFService.execute(delegacionPersonalEFList);
        return toResponse(new Message(savedList));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/personaEF/{curp}")
    public Response loadPersonaEF(@PathParam("curp") String curp) {
        try {
            McltPersonaEF persona = getPersonaEFUniqueByField.findByCurp(curp);
            if (persona != null) {
                LinkedList<McltDelegacionPersonalEF> actualList = delegacionPersonalEFService.getActualDelegacionList(persona.getId());
                persona.setDelegaciones(actualList);
                log.log(Level.INFO, ">>> personaBack PersonaEndPoint loadPersonaEF actualList.size=" + actualList.size() + " persona=" + persona);
            }
            return Response.ok(uriInfo.getAbsolutePath()).entity(persona).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delegaciones/{id}")
    public Response loadDelegacionesPersonaEF(@PathParam("id") Long id) {
        try {
            LinkedList<McltDelegacionPersonalEF> actualList = delegacionPersonalEFService.getActualDelegacionList(id);
            log.log(Level.INFO, ">>> personaBack PersonaEndPoint loadDelegacionesPersonaEF actualList.size=" + actualList.size());
            return toResponse(new Message(actualList));
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{curp}")
    public Response load(@PathParam("curp") String curp) {
        try {
            //Message<McltPersona> personaM = getPersona.execute(curp);
            //McltPersona persona = personaM.getPayload();
            McltPersona persona = getPersona.findByCurp(curp);
            log.log(Level.INFO, ">>> personaBack PersonaEndPoint.load getPersona.execute(" + curp + ")= {0}", persona);
            return Response.ok(uriInfo.getAbsolutePath()).entity(persona).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "NOT_FOUND", e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    public Response getPersonaById(@PathParam("id") Long id) {
        try {
            Message<McltPersona> personaM = getPersonaUniqueByField.execute(id);
            McltPersona persona = personaM.getPayload();
            return Response.ok(uriInfo.getAbsolutePath()).entity(persona).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/email/{email}")
    public Integer validateByEMail(@PathParam("email") String email) throws BusinessException {
        log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByEMail email={0}", email);
        McltPersona persona = null;
        try {
            persona = getPersonaUniqueByField.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        Integer usuario = 0;
        if (persona != null) {
            usuario = 1;
            log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByEMail usuario= {0}", usuario);

        }
        return usuario;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/curp/{curp}")
    public Integer validateByCurp(@PathParam("curp") String curp) throws BusinessException {
        log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByCurp curp={0}", curp);
        Integer usuario = 0;
        McltPersona persona = null;
        McltPersonaEF personaEF = null;
        try {
            persona = getPersonaUniqueByField.findByCurp(curp);
            personaEF = getPersonaEFUniqueByField.findByCurp(curp);
            if (persona != null) {
                if(persona.getCveEstadoPersonaEf().getId() == 3){
                    if(persona.getBaja() == 1 || persona.getBaja() == 3){
                        log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByCurp Promotor Inactivo, motivo: ", persona.getBaja());
                        usuario = persona.getId().intValue();
                        return usuario;
                    }
                }
                usuario = 1;
                log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByCurp Persona usuario= {0}", usuario);
            }
            if (personaEF != null) {
                usuario = 2;
                log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByCurp PersonaEF usuario= {0}", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, null, e);
            throw new UnknowException();
        }
        return usuario;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/entidadFinanciera/{idEntidadFinanciera}")
    public Response existenPromotores(@PathParam("idEntidadFinanciera") String id) {
        try {
            return toResponse(promotorByEfService.execute(id));
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/promotoresValidos/{cveDelegacion}/{cveEntidadFinanciera}")
    public Response promotoresParaAsignar(
            @PathParam("cveDelegacion") Long cveDelegacion,
            @PathParam("cveEntidadFinanciera") Long cveEntidadFinanciera
    ) {
        try {
            return Response.ok(personaJpaService.obtienePromotoresParaAsignar(
                    cveDelegacion,
                    cveEntidadFinanciera
            )).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/bajaPromotor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bajaPromotor(BajaPromotorRQ promotor) {
        log.log(Level.INFO, "personaBack persona/bajaPromotor {0}", promotor);

        personaJpaService.bajaPromotorService(promotor);
        serviceEF.bajaPromotorService(promotor);

        return Response.accepted().build();
    }
    
    @DELETE
    @Path("/bajaOperador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bajaOperador(BajaOperadorRQ operador) {
        log.log(Level.INFO, "personaBack persona/bajaOperador {0}", operador);

        //Baja persona
        personaJpaService.bajaOperadorService(operador);
        //Baja personal
        serviceEF.bajaOperadorService(operador);

        return Response.accepted().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response update(McltPersona persona) throws BusinessException {

        try {
            McltPersona personaForUpdate = personaJpaService.personaById(persona);
            personaForUpdate.setNumEmpleado(persona.getNumEmpleado());
            personaForUpdate.setRegistroPatronal(persona.getRegistroPatronal());
            personaForUpdate.setTelCelular(persona.getTelCelular());
            personaForUpdate.setCorreoElectronico(persona.getCorreoElectronico());
            personaForUpdate.setRfc(persona.getRfc());
            personaForUpdate.setActualizacionRegistro(new Date());
            if(persona.getCveEntidadFinanciera() != null) 
                personaForUpdate.setCveEntidadFinanciera(persona.getCveEntidadFinanciera());
            if(persona.getBaja() != null)
                personaForUpdate.setBaja(persona.getBaja());
            if(persona.getCveEstadoPersonaEf() != null)
                personaForUpdate.setCveEstadoPersonaEf(new MclcEstadoPersonaEf());
                personaForUpdate.getCveEstadoPersonaEf().setId(persona.getCveEstadoPersonaEf().getId());
                personaForUpdate.getCveEstadoPersonaEf().setDesEstadoPersonaEf(persona.getCveEstadoPersonaEf().getDesEstadoPersonaEf());
            personaJpaService.updatePersona(personaForUpdate);

            return Response.accepted().build();
        } catch (Exception e) {
            return Response.notModified().build();

        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updatePersonalEF")
    public Response updatePersonal(McltPersonaEF personaEF) throws BusinessException {
        try {
            McltPersonaEF personaForUpdate = personaEFJpaService.findByCurp(personaEF);
            personaForUpdate.setNumEmpleado(personaEF.getNumEmpleado());
            personaForUpdate.setCveEntidadFinanciera(personaEF.getCveEntidadFinanciera());
            personaForUpdate.setCveEstadoPersonaEf(1L);
            personaForUpdate.setActualizacionRegistro(new Date());
            personaEFJpaService.updatePersona(personaEF);
            return Response.accepted().build();
        }catch (Exception e) {
            return Response.notModified().build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rp/{rp}/{ef}")
    public Integer validateByRP(@PathParam("rp") String rp,@PathParam("ef") String ef ) throws BusinessException {
        log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByRP rp={0}", rp);
        Integer existe = 3;
        McltRegistrosPatronales registroPatronal = null; 
        MclcEntidadFinanciera entidadFinanciera = null;
        try {
            entidadFinanciera = entidadFinancieraService.findbyRPandCVE(rp, ef);
            if(entidadFinanciera != null){
                existe = 1;
            }else{
                registroPatronal = registroPatronalJpaService.registroPatronalByRP(rp,ef);
                if (registroPatronal != null) {
                    log.log(Level.INFO, ">>>>>>>>>>>personaBack PersonaEndPoint.validateByRP numero registro patronal= {0}", registroPatronal.getRegistroPatronal());
                    existe = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, null, e);
            throw new UnknowException();
        }
        return existe;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizaAdmin")
    public Response actualizaAdmin (UpdateAdminRequest request)throws BusinessException{
        
        request= updatePersonaAdminService.execute(request);
        request= usuarioService.execute(request);
        return toResponse(new Message(request));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/obtenerOperadorEFFirma/{cveEntidadFinanciera}")
    public Response obtenerOperadorEFFirma(@PathParam("cveEntidadFinanciera") Long cveEntidadFinanciera) throws BusinessException {
        try {
            List<McltPersonaUsuarioPerfil> lista = perfilService.obtenerOperadorEFFirma(
                    cveEntidadFinanciera
            );
            if (lista == null || lista.isEmpty())
                return Response.noContent().build();

            return Response.ok(
                    personaAssembler.assembleList(lista)
            ).build();
        }catch (BusinessException e){
            return toResponse(new Message<>(null, ServiceStatusEnum.EXCEPCION, e, null));
        }catch(Exception e){
            log.log(Level.SEVERE, "ERROR PersonaEndPoint.obtenerOperadorEFFirma()", e);
        }
        return toResponse(
                new Message<>(
                        null,
                        ServiceStatusEnum.EXCEPCION,
                        new PersonaException(PersonaException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                        null
                ));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/inhabilitaUsuario")
    public Response inhabilitarUsuario(UpdateAdminRequest request)throws BusinessException{
        try{
            updatePersonaAdminService.inhabilitarUsuario(request.getCurpAnterior());
            return Response.ok().build();
        }catch (BusinessException e){
            return toResponse(new Message<>(null, ServiceStatusEnum.EXCEPCION, e, null));
        }catch (Exception e){
            log.log(Level.SEVERE,"ERROR PersonaEndPoint.inhabilitarUsuario() curp ["+request.getCurpAnterior()+"]");
        }

        return toResponse(
                new Message<>(
                        null,
                        ServiceStatusEnum.EXCEPCION,
                        new PersonaException(PersonaException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                        null
                ));
    }
    
}
