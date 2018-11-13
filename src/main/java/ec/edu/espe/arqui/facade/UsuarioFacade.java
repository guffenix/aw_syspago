
package ec.edu.espe.arqui.facade;

import ec.edu.espe.arqui.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bigdata09
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ec.edu.espe.arqui_aw_syspago_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario obtenerUsuarioLogin(String user, String clave) {
        return (Usuario)getEntityManager().createQuery("Select u from Usuario u where u.usuUser  = ?1 AND u.usuClave =?2")
                .setParameter(1, user)
                .setParameter(2, clave)
                .getSingleResult();
    }

}
