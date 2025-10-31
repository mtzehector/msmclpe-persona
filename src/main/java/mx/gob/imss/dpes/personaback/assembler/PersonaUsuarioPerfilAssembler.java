package mx.gob.imss.dpes.personaback.assembler;

import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.interfaces.persona.model.PersonaUsuarioPerfil;
import mx.gob.imss.dpes.personaback.entity.McltPersonaUsuarioPerfil;

import javax.ws.rs.ext.Provider;

@Provider
public class PersonaUsuarioPerfilAssembler extends BaseAssembler<McltPersonaUsuarioPerfil, PersonaUsuarioPerfil> {

    @Override
    public PersonaUsuarioPerfil assemble(McltPersonaUsuarioPerfil source) {
        if (source == null)
            return null;

        PersonaUsuarioPerfil persona = new PersonaUsuarioPerfil();
        persona.setCveEntidadFinanciera(source.getCVE_ENTIDAD_FINANCIERA() == null? 0 : source.getCVE_ENTIDAD_FINANCIERA().longValue());
        persona.setConteo(source.getCONTEO() == null? 0 : source.getCONTEO().longValue());
        return persona;
    }
}
