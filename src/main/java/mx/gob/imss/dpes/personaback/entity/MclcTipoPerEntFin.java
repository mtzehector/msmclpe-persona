/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;

/**
 *
 * @author eduardo.loyo
 */
@Entity
@Table(name = "MCLC_TIPO_PER_ENT_FIN")
@Data
public class MclcTipoPerEntFin extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_TIPO_PER_ENT_FIN")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DES_TIPO_PER_ENT_FIN")
    private String desTipoPerEntFin;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MclcTipoPerEntFin)) return false;
    MclcTipoPerEntFin that = (MclcTipoPerEntFin) o;
    return id.equals(that.id);
  }
   
}
