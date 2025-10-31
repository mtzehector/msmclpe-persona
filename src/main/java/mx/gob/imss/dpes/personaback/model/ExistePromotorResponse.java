package mx.gob.imss.dpes.personaback.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.gob.imss.dpes.common.model.BaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistePromotorResponse extends BaseModel {

	private static final long serialVersionUID = 1L;

	private long totalPromotores;
	private boolean existePromotor;

}