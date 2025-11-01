/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author gabriel.rios
 */
@Entity
@Table(name = "MCLT_DELEGACION_PERSONAL_EF")
public class McltDelegacionPersonalEF extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "MCLS_DELEGACION_PERSONAL_EF")
    @GenericGenerator(
            name = "MCLS_DELEGACION_PERSONAL_EF",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_DELEGACION_PERSONAL_EF")
                ,
        @Parameter(name = "initial_value", value = "1")
                ,
        @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CVE_DELEGACION_PERSONAL_EF")
    @Getter
    @Setter
    private Long id;

    @Column(name = "CVE_PERSONAL_EF")
    @Getter
    @Setter
    private Long cvePersonalEF;
    
    @Column(name = "CVE_DELEGACION")
    @Getter
    @Setter
    private Long cveDelegacion;

    
    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private Long cveEntidadFinanciera;
    
    @Column(name = "IND_ACTIVO")
    @Getter
    @Setter
    private Integer isActivo;
    
    @Transient
    @Getter
    @Setter
    private String descDelegacion;
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McltDelegacionPersonalEF)) {
            return false;
        }
        McltDelegacionPersonalEF other = (McltDelegacionPersonalEF) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
