package mx.gob.imss.dpes.personaback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 * Bean para actualización de datos de pensionado en persona sipre
 * @author luisr.rodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PensionadoSipreModel extends BaseModel {
    private Long id;
    private String cveCurp;
    private String numNss;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String correoElectronico; 
    private String telCelular;
    private String telLocal;
}
