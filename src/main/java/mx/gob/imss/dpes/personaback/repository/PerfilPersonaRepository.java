package mx.gob.imss.dpes.personaback.repository;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RequestTokenException;
import mx.gob.imss.dpes.personaback.entity.PerfilPersona;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PerfilPersonaRepository {
    protected final transient Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    private Date agregarHoras(Date fecha, int horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        return calendar.getTime();
    }

    private String obtenerConsultaPerfilPersonaPorToken() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("    t.cve_persona AS cve_persona, ")
                .append("    t.cve_perfil AS cve_perfil, ")
                .append("    t.num_sesion AS num_sesion, ")
                .append("    p.num_nss AS num_nss, ")
                .append("    p.cve_curp AS cve_curp, ")
                .append("    p.correo_electronico AS correo_electronico, ")
                .append("    p.tel_local AS tel_local, ")
                .append("    p.tel_celular AS tel_celular ")
                .append("FROM ")
                .append("    mgpmclpe04.mclt_token t ")
                .append("    INNER JOIN mgpmclpe04.mclt_persona p ")
                .append("        ON (t.cve_persona = p.cve_persona) ")
                .append("WHERE ")
                .append("    t.token = :tokenSeguridad ")
                .append("    AND t.fec_registro_baja IS NULL ")
                .append("    AND t.fec_registro_alta >= :fechaInicio ")
                .append("    AND t.fec_registro_alta <= :fechaFin ")
                .append("    AND p.fec_registro_baja IS NULL ");
        return sb.toString();
    }

    public PerfilPersona buscarPerfilPersonaPorToken(String tokenSeguridad) throws BusinessException {
        try {
            Date fechaActual = new Date();

            List<PerfilPersona> personaList = entityManager.createNativeQuery(
                    this.obtenerConsultaPerfilPersonaPorToken(), PerfilPersona.class)
                    .setParameter("tokenSeguridad", tokenSeguridad)
                    .setParameter("fechaInicio", agregarHoras(fechaActual, -4))
                    .setParameter("fechaFin", agregarHoras(fechaActual, 4))
                    .getResultList();

            if(personaList != null && !personaList.isEmpty())
                return personaList.get(0);
        }
        catch(Exception e) {
            log.log(Level.SEVERE, "PerfilPersonaRepository.buscarPerfilPersonaPorToken - tokenSeguridad = [" +
                    tokenSeguridad + "]", e);
        }

        throw new RequestTokenException();
    }

    private String obtenerConsultaPerfilPersonaPorId() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("    p.cve_persona cve_persona, ")
                .append("    pu.cve_perfil cve_perfil, ")
                .append("    0 num_sesion, ")
                .append("    p.num_nss AS num_nss, ")
                .append("    p.cve_curp AS cve_curp, ")
                .append("    p.correo_electronico AS correo_electronico, ")
                .append("    p.tel_local AS tel_local, ")
                .append("    p.tel_celular AS tel_celular ")
                .append("FROM ")
                .append("    mgpmclpe04.mclt_persona p ")
                .append("    INNER JOIN mgpmclpe04.mclt_usuario u ")
                .append("        ON (p.cve_usuario = u.cve_usuario) ")
                .append("    INNER JOIN mgpmclpe04.mclt_perfil_usuario pu ")
                .append("        ON (u.cve_usuario = pu.cve_usuario) ")
                .append("WHERE ")
                .append("    p.cve_persona = :cvePersona ")
                .append("    AND p.fec_registro_baja IS NULL ")
                .append("    AND u.fec_registro_baja IS NULL ")
                .append("    AND u.ind_activo = 1 ")
                .append("    AND p.correo_electronico = u.nom_usuario ")
                .append("    AND pu.fec_registro_baja IS NULL ");
        return sb.toString();
    }

    public PerfilPersona buscarPerfilPersonaPorId(String cvePersona) throws BusinessException {
        try {

            List<PerfilPersona> personaList = entityManager.createNativeQuery(
                            this.obtenerConsultaPerfilPersonaPorId(), PerfilPersona.class)
                    .setParameter("cvePersona", cvePersona)
                    .getResultList();

            if(personaList != null && !personaList.isEmpty())
                return personaList.get(0);
        }
        catch(Exception e) {
            log.log(Level.SEVERE, "PerfilPersonaRepository.buscarPerfilPersonaPorId - cvePersona = [" +
                    cvePersona + "]", e);
        }

        throw new RequestTokenException();
    }
}
