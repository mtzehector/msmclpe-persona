package mx.gob.imss.dpes.personaback.repository;

import mx.gob.imss.dpes.personaback.entity.McltPersonaUsuarioPerfil;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PersonaUsuarioPerfilRepository {

    @PersistenceContext
    private EntityManager em;

    private String obtenerConsultaSQL(Long cveEntidadFinanciera){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT persona.cve_entidad_financiera as cve_entidad_financiera,");
        sb.append("COUNT(persona.cve_entidad_financiera) as conteo  ");
        sb.append("FROM mgpmclpe04.mclt_persona persona  ");
        sb.append("INNER JOIN mgpmclpe04.mclt_usuario usuario  ");
        sb.append("ON persona.cve_persona = usuario.cve_id_persona  ");
        sb.append("INNER JOIN mgpmclpe04.mclt_perfil_usuario perfil  ");
        sb.append("ON usuario.cve_usuario = perfil.cve_usuario  ");
        sb.append("WHERE  ");
        if (cveEntidadFinanciera != null && cveEntidadFinanciera > 0L)
            sb.append("persona.cve_entidad_financiera = :ef AND  ");
        sb.append("perfil.cve_perfil = 4 AND  ");
        sb.append("perfil.firma_carta_recibo = 1 AND  ");
        sb.append("perfil.fec_registro_baja IS NULL AND  ");
        sb.append("usuario.ind_activo = 1 AND  ");
        sb.append("usuario.fec_registro_baja IS NULL AND  ");
        sb.append("persona.cve_estado_persona_ef = 1 AND  ");
        sb.append("persona.fec_registro_baja IS NULL  ");
        sb.append("GROUP BY persona.cve_entidad_financiera  ");
        sb.append("ORDER BY persona.cve_entidad_financiera ASC");
        return sb.toString();
    }

    public List<McltPersonaUsuarioPerfil> ejecutarConsulta(Long cveEntidadFinanciera) throws Exception {
        try {
            Query query = em.createNativeQuery(this.obtenerConsultaSQL(cveEntidadFinanciera));
            if (cveEntidadFinanciera != null && cveEntidadFinanciera > 0L)
                query.setParameter("ef", cveEntidadFinanciera);
            return query.unwrap(org.hibernate.Query.class)
                    .setResultTransformer(Transformers.aliasToBean(McltPersonaUsuarioPerfil.class))
                    .list();
        }catch (Exception e){
            throw e;
        }
    }

}
