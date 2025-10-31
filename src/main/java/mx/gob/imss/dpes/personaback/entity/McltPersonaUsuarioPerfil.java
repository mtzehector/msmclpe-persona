package mx.gob.imss.dpes.personaback.entity;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;

import java.math.BigDecimal;

public class McltPersonaUsuarioPerfil extends BaseModel {

    @Getter
    @Setter
    private BigDecimal CVE_ENTIDAD_FINANCIERA;

    @Getter
    @Setter
    private BigDecimal CONTEO;


}
