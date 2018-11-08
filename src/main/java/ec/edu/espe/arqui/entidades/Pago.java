/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bigdata09
 */
@Entity
@Table(name = "PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByPagId", query = "SELECT p FROM Pago p WHERE p.pagId = :pagId")
    , @NamedQuery(name = "Pago.findByPagFecha", query = "SELECT p FROM Pago p WHERE p.pagFecha = :pagFecha")
    , @NamedQuery(name = "Pago.findByPagValor", query = "SELECT p FROM Pago p WHERE p.pagValor = :pagValor")
    , @NamedQuery(name = "Pago.findByPagEstado", query = "SELECT p FROM Pago p WHERE p.pagEstado = :pagEstado")
    , @NamedQuery(name = "Pago.findByPagValorSer", query = "SELECT p FROM Pago p WHERE p.pagValorSer = :pagValorSer")
    , @NamedQuery(name = "Pago.findByPagFechaCorte", query = "SELECT p FROM Pago p WHERE p.pagFechaCorte = :pagFechaCorte")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_ID")
    private BigDecimal pagId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pagFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_VALOR")
    private BigDecimal pagValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_ESTADO")
    private BigInteger pagEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_VALOR_SER")
    private BigDecimal pagValorSer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAG_FECHA_CORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pagFechaCorte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pago")
    private Collection<DetalleMoneda> detalleMonedaCollection;
    @JoinColumn(name = "CLI_IDENTIFICACION", referencedColumnName = "CLI_IDENTIFICACION")
    @ManyToOne(optional = false)
    private Cliente cliIdentificacion;
    @JoinColumn(name = "SER_ID", referencedColumnName = "SER_ID")
    @ManyToOne(optional = false)
    private Servicio serId;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public Pago() {
    }

    public Pago(BigDecimal pagId) {
        this.pagId = pagId;
    }

    public Pago(BigDecimal pagId, Date pagFecha, BigDecimal pagValor, BigInteger pagEstado, BigDecimal pagValorSer, Date pagFechaCorte) {
        this.pagId = pagId;
        this.pagFecha = pagFecha;
        this.pagValor = pagValor;
        this.pagEstado = pagEstado;
        this.pagValorSer = pagValorSer;
        this.pagFechaCorte = pagFechaCorte;
    }

    public BigDecimal getPagId() {
        return pagId;
    }

    public void setPagId(BigDecimal pagId) {
        this.pagId = pagId;
    }

    public Date getPagFecha() {
        return pagFecha;
    }

    public void setPagFecha(Date pagFecha) {
        this.pagFecha = pagFecha;
    }

    public BigDecimal getPagValor() {
        return pagValor;
    }

    public void setPagValor(BigDecimal pagValor) {
        this.pagValor = pagValor;
    }

    public BigInteger getPagEstado() {
        return pagEstado;
    }

    public void setPagEstado(BigInteger pagEstado) {
        this.pagEstado = pagEstado;
    }

    public BigDecimal getPagValorSer() {
        return pagValorSer;
    }

    public void setPagValorSer(BigDecimal pagValorSer) {
        this.pagValorSer = pagValorSer;
    }

    public Date getPagFechaCorte() {
        return pagFechaCorte;
    }

    public void setPagFechaCorte(Date pagFechaCorte) {
        this.pagFechaCorte = pagFechaCorte;
    }

    @XmlTransient
    public Collection<DetalleMoneda> getDetalleMonedaCollection() {
        return detalleMonedaCollection;
    }

    public void setDetalleMonedaCollection(Collection<DetalleMoneda> detalleMonedaCollection) {
        this.detalleMonedaCollection = detalleMonedaCollection;
    }

    public Cliente getCliIdentificacion() {
        return cliIdentificacion;
    }

    public void setCliIdentificacion(Cliente cliIdentificacion) {
        this.cliIdentificacion = cliIdentificacion;
    }

    public Servicio getSerId() {
        return serId;
    }

    public void setSerId(Servicio serId) {
        this.serId = serId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagId != null ? pagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.pagId == null && other.pagId != null) || (this.pagId != null && !this.pagId.equals(other.pagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.Pago[ pagId=" + pagId + " ]";
    }
    
}
