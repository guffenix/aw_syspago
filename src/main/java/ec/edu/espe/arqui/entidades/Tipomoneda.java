/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bigdata09
 */
@Entity
@Table(name = "TIPOMONEDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipomoneda.findAll", query = "SELECT t FROM Tipomoneda t")
    , @NamedQuery(name = "Tipomoneda.findByTmoId", query = "SELECT t FROM Tipomoneda t WHERE t.tmoId = :tmoId")
    , @NamedQuery(name = "Tipomoneda.findByTmoTipo", query = "SELECT t FROM Tipomoneda t WHERE t.tmoTipo = :tmoTipo")
    , @NamedQuery(name = "Tipomoneda.findByTmoValor", query = "SELECT t FROM Tipomoneda t WHERE t.tmoValor = :tmoValor")})
public class Tipomoneda implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TMO_ID")
    private BigDecimal tmoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "TMO_TIPO")
    private String tmoTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TMO_VALOR")
    private double tmoValor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipomoneda")
    private Collection<DetalleMoneda> detalleMonedaCollection;

    public Tipomoneda() {
    }

    public Tipomoneda(BigDecimal tmoId) {
        this.tmoId = tmoId;
    }

    public Tipomoneda(BigDecimal tmoId, String tmoTipo, double tmoValor) {
        this.tmoId = tmoId;
        this.tmoTipo = tmoTipo;
        this.tmoValor = tmoValor;
    }

    public BigDecimal getTmoId() {
        return tmoId;
    }

    public void setTmoId(BigDecimal tmoId) {
        this.tmoId = tmoId;
    }

    public String getTmoTipo() {
        return tmoTipo;
    }

    public void setTmoTipo(String tmoTipo) {
        this.tmoTipo = tmoTipo;
    }

    public double getTmoValor() {
        return tmoValor;
    }

    public void setTmoValor(double tmoValor) {
        this.tmoValor = tmoValor;
    }

    @XmlTransient
    public Collection<DetalleMoneda> getDetalleMonedaCollection() {
        return detalleMonedaCollection;
    }

    public void setDetalleMonedaCollection(Collection<DetalleMoneda> detalleMonedaCollection) {
        this.detalleMonedaCollection = detalleMonedaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmoId != null ? tmoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipomoneda)) {
            return false;
        }
        Tipomoneda other = (Tipomoneda) object;
        if ((this.tmoId == null && other.tmoId != null) || (this.tmoId != null && !this.tmoId.equals(other.tmoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.Tipomoneda[ tmoId=" + tmoId + " ]";
    }
    
}
