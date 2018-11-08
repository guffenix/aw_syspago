/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "VEHICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
    , @NamedQuery(name = "Vehiculo.findByViPlaca", query = "SELECT v FROM Vehiculo v WHERE v.viPlaca = :viPlaca")
    , @NamedQuery(name = "Vehiculo.findByViMarca", query = "SELECT v FROM Vehiculo v WHERE v.viMarca = :viMarca")
    , @NamedQuery(name = "Vehiculo.findByViModelo", query = "SELECT v FROM Vehiculo v WHERE v.viModelo = :viModelo")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "VI_PLACA")
    private String viPlaca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "VI_MARCA")
    private String viMarca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "VI_MODELO")
    private String viModelo;
    @OneToMany(mappedBy = "viPlaca")
    private Collection<Cliente> clienteCollection;

    public Vehiculo() {
    }

    public Vehiculo(String viPlaca) {
        this.viPlaca = viPlaca;
    }

    public Vehiculo(String viPlaca, String viMarca, String viModelo) {
        this.viPlaca = viPlaca;
        this.viMarca = viMarca;
        this.viModelo = viModelo;
    }

    public String getViPlaca() {
        return viPlaca;
    }

    public void setViPlaca(String viPlaca) {
        this.viPlaca = viPlaca;
    }

    public String getViMarca() {
        return viMarca;
    }

    public void setViMarca(String viMarca) {
        this.viMarca = viMarca;
    }

    public String getViModelo() {
        return viModelo;
    }

    public void setViModelo(String viModelo) {
        this.viModelo = viModelo;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viPlaca != null ? viPlaca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.viPlaca == null && other.viPlaca != null) || (this.viPlaca != null && !this.viPlaca.equals(other.viPlaca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arqui.entidades.Vehiculo[ viPlaca=" + viPlaca + " ]";
    }
    
}
