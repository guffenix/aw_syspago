/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bigdata09
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByCliIdentificacion", query = "SELECT c FROM Cliente c WHERE c.cliIdentificacion = :cliIdentificacion")
    , @NamedQuery(name = "Cliente.findByCliTipo", query = "SELECT c FROM Cliente c WHERE c.cliTipo = :cliTipo")
    , @NamedQuery(name = "Cliente.findByCliNombre", query = "SELECT c FROM Cliente c WHERE c.cliNombre = :cliNombre")
    , @NamedQuery(name = "Cliente.findByCliRazSocial", query = "SELECT c FROM Cliente c WHERE c.cliRazSocial = :cliRazSocial")
    , @NamedQuery(name = "Cliente.findByCliMovil", query = "SELECT c FROM Cliente c WHERE c.cliMovil = :cliMovil")
    , @NamedQuery(name = "Cliente.findByCliFijo", query = "SELECT c FROM Cliente c WHERE c.cliFijo = :cliFijo")
    , @NamedQuery(name = "Cliente.findByCliDireccion", query = "SELECT c FROM Cliente c WHERE c.cliDireccion = :cliDireccion")
    , @NamedQuery(name = "Cliente.findByCliCodContrapartida", query = "SELECT c FROM Cliente c WHERE c.cliCodContrapartida = :cliCodContrapartida")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "CLI_IDENTIFICACION")
    private String cliIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "CLI_TIPO")
    private String cliTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "CLI_NOMBRE")
    private String cliNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "CLI_RAZ_SOCIAL")
    private String cliRazSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "CLI_MOVIL")
    private String cliMovil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "CLI_FIJO")
    private String cliFijo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "CLI_DIRECCION")
    private String cliDireccion;
    @Size(max = 64)
    @Column(name = "CLI_COD_CONTRAPARTIDA")
    private String cliCodContrapartida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliIdentificacion")
    private Collection<Pago> pagoCollection;
    @JoinColumn(name = "VI_PLACA", referencedColumnName = "VI_PLACA")
    @ManyToOne
    private Vehiculo viPlaca;

    public Cliente() {
    }

    public Cliente(String cliIdentificacion) {
        this.cliIdentificacion = cliIdentificacion;
    }

    public Cliente(String cliIdentificacion, String cliTipo, String cliNombre, String cliRazSocial, String cliMovil, String cliFijo, String cliDireccion) {
        this.cliIdentificacion = cliIdentificacion;
        this.cliTipo = cliTipo;
        this.cliNombre = cliNombre;
        this.cliRazSocial = cliRazSocial;
        this.cliMovil = cliMovil;
        this.cliFijo = cliFijo;
        this.cliDireccion = cliDireccion;
    }

    public String getCliIdentificacion() {
        return cliIdentificacion;
    }

    public void setCliIdentificacion(String cliIdentificacion) {
        this.cliIdentificacion = cliIdentificacion;
    }

    public String getCliTipo() {
        return cliTipo;
    }

    public void setCliTipo(String cliTipo) {
        this.cliTipo = cliTipo;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliRazSocial() {
        return cliRazSocial;
    }

    public void setCliRazSocial(String cliRazSocial) {
        this.cliRazSocial = cliRazSocial;
    }

    public String getCliMovil() {
        return cliMovil;
    }

    public void setCliMovil(String cliMovil) {
        this.cliMovil = cliMovil;
    }

    public String getCliFijo() {
        return cliFijo;
    }

    public void setCliFijo(String cliFijo) {
        this.cliFijo = cliFijo;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliCodContrapartida() {
        return cliCodContrapartida;
    }

    public void setCliCodContrapartida(String cliCodContrapartida) {
        this.cliCodContrapartida = cliCodContrapartida;
    }

    @XmlTransient
    public Collection<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(Collection<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    public Vehiculo getViPlaca() {
        return viPlaca;
    }

    public void setViPlaca(Vehiculo viPlaca) {
        this.viPlaca = viPlaca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliIdentificacion != null ? cliIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliIdentificacion == null && other.cliIdentificacion != null) || (this.cliIdentificacion != null && !this.cliIdentificacion.equals(other.cliIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.Cliente[ cliIdentificacion=" + cliIdentificacion + " ]";
    }
    
}
