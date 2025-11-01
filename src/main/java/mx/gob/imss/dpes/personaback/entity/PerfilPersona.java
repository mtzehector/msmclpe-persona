package mx.gob.imss.dpes.personaback.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PerfilPersona {
    @Id
    @Column(name = "CVE_PERSONA")
    @Getter
    @Setter
    private Long cvePersona;

    @Column(name = "CVE_PERFIL")
    @Getter
    @Setter
    private Integer cvePerfil;

    @Column(name = "NUM_NSS")
    @Getter
    @Setter
    private String nss;

    @Column(name = "CVE_CURP")
    @Getter
    @Setter
    private String cveCurp;

    @Column(name = "NUM_SESION")
    @Getter
    @Setter
    private String numSesion;

    @Column(name = "CORREO_ELECTRONICO")
    @Getter
    @Setter
    private String correoElectronico;

    @Column(name = "TEL_LOCAL")
    @Getter
    @Setter
    private String telLocal;

    @Column(name = "TEL_CELULAR")
    @Getter
    @Setter
    private String telCelular;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cvePersona != null ? cvePersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PerfilPersona)) {
            return false;
        }
        PerfilPersona other = (PerfilPersona) object;
        if (
            (this.cvePersona == null && other.cvePersona != null) ||
            (this.cvePersona != null && !this.cvePersona.equals(other.cvePersona))
        ) {
            return false;
        }
        return true;
    }
}
