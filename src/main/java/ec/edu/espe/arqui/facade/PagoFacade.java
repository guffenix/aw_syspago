package ec.edu.espe.arqui.facade;

import ec.edu.espe.arqui.entidades.Pago;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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

    public void actualizarPago(Pago _pago) {
        super.edit(_pago);
    }

    public Pago obtenerPagoByCliente(String _identificacion) {
        BigDecimal serId = BigDecimal.valueOf(4);
        BigInteger pagEstado = BigInteger.valueOf(1);
        em.getEntityManagerFactory().getCache().evictAll();
        return (Pago) getEntityManager()
                .createQuery("Select p from Pago p where p.serId.serId = ?1 AND p.cliIdentificacion.cliIdentificacion = ?2 AND p.pagEstado = ?3")
                .setParameter(1, serId)
                .setParameter(2, _identificacion)
                .setParameter(3, pagEstado).getSingleResult();
    }

    public Pago obtenerPagoByCliente(String _identificacion, boolean esPagoBasico, boolean esAgua) {
        BigDecimal serId = BigDecimal.valueOf(esPagoBasico ? esAgua ? 20 : 21 : 4);
        BigInteger pagEstado = BigInteger.valueOf(1);
        em.getEntityManagerFactory().getCache().evictAll();
        List<Pago> listaR = getEntityManager()
                .createQuery("Select p from Pago p where p.serId.serId = ?1 AND p.cliIdentificacion.cliIdentificacion = ?2 AND p.pagEstado = ?3")
                .setParameter(1, serId)
                .setParameter(2, _identificacion)
                .setParameter(3, pagEstado).getResultList();
        if (listaR != null && !listaR.isEmpty()) {
            return listaR.get(0);
        } else {
            return null;
        }
    }

    public Pago obtenerPago(String _identificacion) {
        BigDecimal serId = BigDecimal.valueOf(22);
        BigInteger pagEstado = BigInteger.valueOf(1);
        em.getEntityManagerFactory().getCache().evictAll();
        List<Pago> listaR = getEntityManager()
                .createQuery("Select p from Pago p where p.serId.serId = ?1 AND p.cliIdentificacion.cliIdentificacion = ?2 AND p.pagEstado = ?3")
                .setParameter(1, serId)
                .setParameter(2, _identificacion)
                .setParameter(3, pagEstado).getResultList();
        if (listaR != null && !listaR.isEmpty()) {
            return listaR.get(0);
        } else {
            return null;
        }
    }
}
