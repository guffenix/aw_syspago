
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
    
    public Cliente obtenerClienteByPlaca(String _placa) {
        return (Cliente)getEntityManager().createQuery("Select c from Cliente c where c.viPlaca.viPlaca = ?1").setParameter(1, _placa).getSingleResult();
    }
    
    public Cliente obtenerClientePorContrapartida(String _contrapartida)
    {
        List<Cliente> listaR = getEntityManager().createNamedQuery("Cliente.findByCliCodContrapartida").setParameter("cliCodContrapartida", _contrapartida).getResultList();
        if(listaR != null && !listaR.isEmpty())
        {
            return listaR.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public Cliente obtenerClientePorIdentificacion(String _identificacion)
    {
        List<Cliente> listaC=getEntityManager().createNamedQuery("Cliente.findByCliIdentificacion").setParameter("cliIdentificacion", _identificacion).getResultList();
        if (listaC!=null && !listaC.isEmpty())
        {
            return listaC.get(0);
        }
        else
        {
            return null;
        }
        
    }
    
    
}
