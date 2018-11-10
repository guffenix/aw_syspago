package ec.edu.espe.arqui.facade;

import ec.edu.espe.arqui.entidades.Pago;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bigdata09
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> {

    @PersistenceContext(unitName = "ec.edu.espe.arqui_aw_syspago_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }
    
    public Pago obtenerPagoByCliente(String _identificacion) {
        BigDecimal serId = BigDecimal.valueOf(4);
        BigInteger pagEstado = BigInteger.valueOf(1);
        return (Pago)getEntityManager()
                .createQuery("Select p from Pago p where p.serId.serId = ?1 AND p.cliIdentificacion.cliIdentificacion = ?2 AND p.pagEstado = ?3")
                .setParameter(1, serId)
                .setParameter(2, _identificacion)
                .setParameter(3, pagEstado).getSingleResult();
    }
}
