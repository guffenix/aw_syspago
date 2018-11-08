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
@Table(name = "SERVICIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findBySerId", query = "SELECT s FROM Servicio s WHERE s.serId = :serId")
    , @NamedQuery(name = "Servicio.findBySerNombre", query = "SELECT s FROM Servicio s WHERE s.serNombre = :serNombre")
    , @NamedQuery(name = "Servicio.findBySerDescripcion", query = "SELECT s FROM Servicio s WHERE s.serDescripcion = :serDescripcion")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SER_ID")
    private BigDecimal serId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "SER_NOMBRE")
    private String serNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "SER_DESCRIPCION")
    private String serDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serId")
    private Collection<Pago> pagoCollection;

    public Servicio() {
    }

    public Servicio(BigDecimal serId) {
        this.serId = serId;
    }

    public Servicio(BigDecimal serId, String serNombre, String serDescripcion) {
        this.serId = serId;
        this.serNombre = serNombre;
        this.serDescripcion = serDescripcion;
    }

    public BigDecimal getSerId() {
        return serId;
    }

    public void setSerId(BigDecimal serId) {
        this.serId = serId;
    }

    public String getSerNombre() {
        return serNombre;
    }

    public void setSerNombre(String serNombre) {
        this.serNombre = serNombre;
    }

    public String getSerDescripcion() {
        return serDescripcion;
    }

    public void setSerDescripcion(String serDescripcion) {
        this.serDescripcion = serDescripcion;
    }

    @XmlTransient
    public Collection<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(Collection<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serId != null ? serId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.serId == null && other.serId != null) || (this.serId != null && !this.serId.equals(other.serId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.Servicio[ serId=" + serId + " ]";
    }
    
}
