/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.entity;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author edgar.arenas
 */
@Entity
@Table(name = "MCLT_PERSONAL_EF")
public class McltPersonaEF extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_PERSONAL_EF")
    @GenericGenerator(
            name = "MCLS_PERSONAL_EF",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PERSONAL_EF")
                ,
        @Parameter(name = "initial_value", value = "1")
                ,
        @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_PERSONAL_EF")
    @Getter
    @Setter
    private Long id;

    @Column(name = "CVE_CURP")
    @Getter
    @Setter
    private String cveCurp;

    @Size(max = 50)
    @Column(name = "NUM_EMPLEADO")
    @Getter
    @Setter
    private String numEmpleado;

    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private Long cveEntidadFinanciera;
    
    @Column(name = "CVE_DELEGACION")
    @Getter
    @Setter
    private String cveDelegacion;
    
    @Column(name="CVE_ESTADO_PERSONA_ENT_FI")
    @Getter
    @Setter
    private Long cveEstadoPersonaEf;
    
    @Column(name = "CVE_TIPO_PER_ENT_FIN")
    @Getter
    @Setter
    private Long cveTipoPersonaEf;

    @Column(name = "IND_REGISTRADO")
    @Getter
    @Setter
    private Integer indRegistrado;
    
    @Transient
    @Getter
    @Setter
    private List<McltDelegacionPersonalEF> delegaciones = new LinkedList();
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McltPersonaEF)) {
            return false;
        }
        McltPersonaEF other = (McltPersonaEF) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
