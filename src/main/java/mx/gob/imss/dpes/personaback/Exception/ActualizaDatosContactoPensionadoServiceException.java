package mx.gob.imss.dpes.personaback.Exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class ActualizaDatosContactoPensionadoServiceException extends BusinessException {

    public final static String ERROR_DE_MENSAJE = "msg2007";
    public final static String ERROR_DESCONOCIDO_EN_EL_SERVICIO = "msg001";
    public ActualizaDatosContactoPensionadoServiceException(String messageKey) {
        super(messageKey);
    }

}
