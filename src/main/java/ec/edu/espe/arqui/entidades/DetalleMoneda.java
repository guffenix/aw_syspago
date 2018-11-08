/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bigdata09
 */
@Entity
@Table(name = "DETALLE_MONEDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleMoneda.findAll", query = "SELECT d FROM DetalleMoneda d")
    , @NamedQuery(name = "DetalleMoneda.findByPagId", query = "SELECT d FROM DetalleMoneda d WHERE d.detalleMonedaPK.pagId = :pagId")
    , @NamedQuery(name = "DetalleMoneda.findByTmoId", query = "SELECT d FROM DetalleMoneda d WHERE d.detalleMonedaPK.tmoId = :tmoId")
    , @NamedQuery(name = "DetalleMoneda.findByDmonCantidad", query = "SELECT d FROM DetalleMoneda d WHERE d.dmonCantidad = :dmonCantidad")
    , @NamedQuery(name = "DetalleMoneda.findByDmonTotal", query = "SELECT d FROM DetalleMoneda d WHERE d.dmonTotal = :dmonTotal")})
public class DetalleMoneda implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleMonedaPK detalleMonedaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DMON_CANTIDAD")
    private BigInteger dmonCantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DMON_TOTAL")
    private double dmonTotal;
    @JoinColumn(name = "PAG_ID", referencedColumnName = "PAG_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pago pago;
    @JoinColumn(name = "TMO_ID", referencedColumnName = "TMO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipomoneda tipomoneda;

    public DetalleMoneda() {
    }

    public DetalleMoneda(DetalleMonedaPK detalleMonedaPK) {
        this.detalleMonedaPK = detalleMonedaPK;
    }

    public DetalleMoneda(DetalleMonedaPK detalleMonedaPK, BigInteger dmonCantidad, double dmonTotal) {
        this.detalleMonedaPK = detalleMonedaPK;
        this.dmonCantidad = dmonCantidad;
        this.dmonTotal = dmonTotal;
    }

    public DetalleMoneda(BigInteger pagId, BigInteger tmoId) {
        this.detalleMonedaPK = new DetalleMonedaPK(pagId, tmoId);
    }

    public DetalleMonedaPK getDetalleMonedaPK() {
        return detalleMonedaPK;
    }

    public void setDetalleMonedaPK(DetalleMonedaPK detalleMonedaPK) {
        this.detalleMonedaPK = detalleMonedaPK;
    }

    public BigInteger getDmonCantidad() {
        return dmonCantidad;
    }

    public void setDmonCantidad(BigInteger dmonCantidad) {
        this.dmonCantidad = dmonCantidad;
    }

    public double getDmonTotal() {
        return dmonTotal;
    }

    public void setDmonTotal(double dmonTotal) {
        this.dmonTotal = dmonTotal;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Tipomoneda getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(Tipomoneda tipomoneda) {
        this.tipomoneda = tipomoneda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleMonedaPK != null ? detalleMonedaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleMoneda)) {
            return false;
        }
        DetalleMoneda other = (DetalleMoneda) object;
        if ((this.detalleMonedaPK == null && other.detalleMonedaPK != null) || (this.detalleMonedaPK != null && !this.detalleMonedaPK.equals(other.detalleMonedaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.DetalleMoneda[ detalleMonedaPK=" + detalleMonedaPK + " ]";
    }
    
}
