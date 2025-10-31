/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import mx.gob.imss.dpes.support.config.CustomDateDeserializer;
import mx.gob.imss.dpes.support.config.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author juanf.barragan
 */
@Entity
@Table(name = "MCLC_ENTIDAD_FINANCIERA")
public class MclcEntidadFinanciera extends LogicDeletedEntity<Long> {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @GeneratedValue(generator = "SEQ_GEN_MCLS_ENTIDAD_FINANCIERA")
    @GenericGenerator(
            name = "SEQ_GEN_MCLS_ENTIDAD_FINANCIERA",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_ENTIDAD_FINANCIERA"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )

    @Getter
    @Setter
    private Long id;

    @Size(max = 200)
    @Column(name = "NOM_COMERCIAL")
    @Getter
    @Setter
    private String nombreComercial;

    @Size(max = 200)

    @Column(name = "NOM_RAZON_SOCIAL")

    @Getter
    @Setter
    private String razonSocial;

    @Size(max = 13)

    @Column(name = "REF_RFC")

    @Getter
    @Setter
    private String rfc;

    @Size(max = 25)

    @Column(name = "NUM_TELEFONO")

    @Getter
    @Setter
    private String numeroTelefono;

    @Size(max = 25)

    @Column(name = "NUM_TEL_ATENCION_CLIENTES")

    @Getter
    @Setter
    private String telefonoAtencionClientes;

    @Size(max = 200)

    @Column(name = "REF_PAGINA_WEB")

    @Getter
    @Setter
    private String paginaWeb;

    @Size(max = 50)

    @Column(name = "DES_CORREO_ELECTRONICO")

    @Getter
    @Setter
    private String CorreoElectronico;

    @Column(name = "NUM_CAT_PROMEDIO")
    @Getter
    @Setter
    private BigDecimal catPromedio;

    @Size(max = 2)

    @Column(name = "CVE_DELEGACION")

    @Getter
    @Setter
    private String idDelegacion;

    @Size(max = 2)

    @Column(name = "CVE_SUBDELEGACION")

    @Getter
    @Setter
    private String idSubdelegacion;

    @Size(max = 11)

    @Column(name = "CVE_REG_PATRONAL")

    @Getter
    @Setter
    private String registroPatronal;

    

    

    @Column(name = "CURP_REP_LEGAL")

    @Size(max = 20)

    @Getter
    @Setter
    private String curpRepresentateLegal;

    @Column(name = "NOM_NOMBRE_REP_LEGAL")

    @Size(max = 200)

    @Getter
    @Setter
    private String nombreLegal;

    @Column(name = "NOM_PRIM_APE_REP_LEGAL")

    @Size(max = 50)

    @Getter
    @Setter
    private String primerApeLegal;

    @Column(name = "NOM_SEG_APE_REP_LEGAL")

    @Size(max = 50)

    @Getter
    @Setter
    private String segundoApeLegal;

    @Column(name = "CORREO2_CONTACTO")

    @Size(max = 200)

    @Getter
    @Setter
    private String correo2;

    @Column(name = "CORREO3_CONTACTO")

    @Size(max = 200)

    @Getter
    @Setter
    private String correo3;

    @Column(name = "DIRECCION")

    @Size(max = 200)

    @Getter
    @Setter
    private String direccion;

    @Column(name = "FEC_FIRMA_CONTRATO", nullable = false)

    @Temporal(TemporalType.DATE)

    @Getter

    @Setter

    @JsonDeserialize(using = CustomDateDeserializer.class)

    @JsonSerialize(using = CustomDateSerializer.class)

    private Date fecFirmaContra;

    @Column(name = "FEC_INICIO_CONTRATO", nullable = false)

    @Temporal(TemporalType.DATE)

    @Getter

    @Setter

    @JsonDeserialize(using = CustomDateDeserializer.class)

    @JsonSerialize(using = CustomDateSerializer.class)

    private Date fecIniFirmaContra;

    @Column(name = "NUMERO_PROVEEDOR")

    @Getter
    @Setter
    private String numeroProveedor;

    @Column(name = "INST_BANCARIA")

    @Size(max = 20)

    @Getter
    @Setter
    private String instBancaria;

    @Column(name = "CLABE")

    @Size(max = 20)

    @Getter
    @Setter
    private String clabe;

    @Column(name = "CONF_CLABE")

    @Size(max = 20)

    @Getter
    @Setter
    private String confClabe;

    @Column(name = "CURP_ADMIN")

    @Size(max = 20)

    @Getter
    @Setter
    private String curpAdmin;

    @Column(name = "NOM_NOMBRE_ADMIN")

    @Size(max = 200)

    @Getter
    @Setter
    private String nombreAdmin;

    @Column(name = "NOM_PRIMER_APE_ADMIN")

    @Size(max = 47)

    @Getter
    @Setter
    private String primerApAdmin;

    @Column(name = "NOM_SEGUNDO_APE_ADMIN")

    @Size(max = 50)

    @Getter
    @Setter
    private String segundoApeAdmin;

    @Column(name = "CORREO_ADMIN")

    @Size(max = 200)

    @Getter
    @Setter
    private String correoAdmin;
    
    @Column(name = "CVE_ENTIDAD_FINANCIERA_SIPRE")
    @Getter
    @Setter
    private Long idSipre;

    @Override
    public int hashCode() {

        int hash = 0;

        hash += (id != null ? id.hashCode() : 0);

        return hash;

    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MclcEntidadFinanciera)) {

            return false;

        }

        MclcEntidadFinanciera other = (MclcEntidadFinanciera) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {

            return false;

        }

        return true;

    }
    
    
}
