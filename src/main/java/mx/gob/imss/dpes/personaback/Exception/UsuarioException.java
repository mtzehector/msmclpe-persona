package mx.gob.imss.dpes.personaback.Exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

public class UsuarioException extends BusinessException {

    public final static String ERROR_DESCONOCIDO_EN_EL_SERVICIO = "msg001";

    public final static String ERROR_DE_ESCRITURA_EN_BD = "msg003";

    public final static String ERROR_AL_EJECUTAR_CONSULTA = "msg004";
    public final static String ERROR_NO_EXISTE_ID_USUARIO = "msg2005";
    public final static String ERROR_NO_EXISTE_USUARIO = "msg2006";

    public UsuarioException(String messageKey) {
        super(messageKey);
    }

}
