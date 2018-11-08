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
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bigdata09
 */
@Embeddable
public class DetalleMonedaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_ID")
    private BigInteger pagId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TMO_ID")
    private BigInteger tmoId;

    public DetalleMonedaPK() {
    }

    public DetalleMonedaPK(BigInteger pagId, BigInteger tmoId) {
        this.pagId = pagId;
        this.tmoId = tmoId;
    }

    public BigInteger getPagId() {
        return pagId;
    }

    public void setPagId(BigInteger pagId) {
        this.pagId = pagId;
    }

    public BigInteger getTmoId() {
        return tmoId;
    }

    public void setTmoId(BigInteger tmoId) {
        this.tmoId = tmoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagId != null ? pagId.hashCode() : 0);
        hash += (tmoId != null ? tmoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleMonedaPK)) {
            return false;
        }
        DetalleMonedaPK other = (DetalleMonedaPK) object;
        if ((this.pagId == null && other.pagId != null) || (this.pagId != null && !this.pagId.equals(other.pagId))) {
            return false;
        }
        if ((this.tmoId == null && other.tmoId != null) || (this.tmoId != null && !this.tmoId.equals(other.tmoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.DetalleMonedaPK[ pagId=" + pagId + ", tmoId=" + tmoId + " ]";
    }
    
}
