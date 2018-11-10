
package ec.edu.espe.arqui.facade;

import ec.edu.espe.arqui.entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bigdata09
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "ec.edu.espe.arqui_aw_syspago_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    public Cliente obtenerPorPlaca(String _placa) {
        return (Cliente) getEntityManager().createNativeQuery("select * from cliente c where c.vi_placa = ?1").setParameter(1, _placa).getSingleResult();
    }
    
    public Cliente obtenerCliente(String _placa) {
            return (Cliente)getEntityManager().createQuery("Select c from Cliente c where c.viPlaca.viPlaca = ?1").setParameter(1, _placa).getSingleResult();
    }
    
    public int countAllwithSQL() {
        int total = (int) em.createNamedQuery("Player.countAllwithSQL")
                .getSingleResult();
        return total;
    }

 
}
