/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import mx.gob.imss.dpes.support.config.CustomDateDeserializer;
import mx.gob.imss.dpes.support.config.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author antonio
 */
@Entity
@Table(name = "MCLT_PERSONA_oldie")
public class McltPersona_oldie extends LogicDeletedEntity<Long> {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "CVE_PERSONA", updatable = false, nullable = false)
  @Getter @Setter
  //@SequenceGenerator(name = "SEQ_GEN_MCLS_PENSONA", sequenceName = "MCLS_PENSONA")
  //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MCLS_PENSONA")
  @GeneratedValue(generator = "SEQ_GEN_MCLS_PENSONA")
    @GenericGenerator(
      name = "SEQ_GEN_MCLS_PENSONA",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "MCLS_PENSONA"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 18)
  @Column(name = "CVE_CURP")
  @Getter @Setter
  private String curp;
  @Size(max = 150)
  @Column(name = "NOM_NOMBRE")
  @Getter @Setter
  private String nombre;
  @Size(max = 150)
  @Column(name = "NOM_PRIMER_APELLIDO")
  @Getter @Setter
  private String primerApellido;
  @Size(max = 150)
  @Column(name = "NOM_SEGUNDO_APELLIDO")
  @Getter @Setter
  private String segundoApellido;
  @Column(name = "FEC_NACIMIENTO")
  @Temporal(TemporalType.TIMESTAMP)
  @Getter @Setter
  @JsonDeserialize( using = CustomDateDeserializer.class )
    @JsonSerialize(using = CustomDateSerializer.class) private Date fecNacimiento;
  @Size(max = 50)
  @Column(name = "DES_CORREO_ELECTRONICO")
  @Getter @Setter
  private String correoElectronico;  
  @Size(max = 15)
  @Column(name = "NUM_TELEFONO")
  @Getter @Setter
  private String telefono;
  @Size(max = 15)
  @Column(name = "NUM_TELEFONO_CEL")
  @Getter @Setter
  private String telefonoCel;
  
   @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
  
  @Override
  public boolean equals(Object object) {    
    if (!(object instanceof McltPersona_oldie)) {
      return false;
    }
    McltPersona_oldie other = (McltPersona_oldie) object;
    if ((this.id == null && other.id != null) ||
            (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  
}
