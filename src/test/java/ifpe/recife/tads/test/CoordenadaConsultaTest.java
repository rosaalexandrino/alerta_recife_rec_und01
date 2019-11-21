package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Coordenada;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class CoordenadaConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CoordenadaConsultaTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
        logger.setLevel(Level.SEVERE);
        emf = Persistence.createEntityManagerFactory("alerta_recife");
        DataSet.inserirDados();

    }

    @AfterClass
    public static void tearDownClass() {

        emf.close();

    }

    @Before
    public void setUp() {

        em = emf.createEntityManager();
        beginTransaction();

    }

    @After
    public void tearDown() {

        commitTransaction();
        em.close();

    }

    private void beginTransaction() {

        et = em.getTransaction();
        et.begin();

    }

    private void commitTransaction() {

        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            et.rollback();
            fail(ex.getMessage());
        }

    }

    @Test
    public void t01_recuperarPorPontoXJPQL() {

        logger.info("Executando: recuperarPorPontoXJPQL");
        Query query = em.createNamedQuery("Coordenada.RecuperarPorPontoX", Coordenada.class);
        query.setParameter("ponto", -8.0391283);
        Coordenada coordenada = (Coordenada) query.getSingleResult();
        assertEquals(-8.0391283, coordenada.getPontoX(), 0.0);

    }

    @Test
    public void t02_recuperarPorPontoYJPQL() {

        logger.info("Executando: recuperarPorPontoYJPQL");
        Query query = em.createNamedQuery("Coordenada.RecuperarPorPontoY", Coordenada.class);
        query.setParameter("ponto", -34.8238971);
        Coordenada coordenada = (Coordenada) query.getSingleResult();
        assertEquals(-34.8238971, coordenada.getPontoY(), 0.0);

    }

    @Test
    public void t03_RecuperarPorPontoXSQL() {

        logger.info("Executando: RecuperarPorPontoXSQL");
        Query query = em.createNamedQuery("Coordenada.RecuperarPorPontoXSQL");
        query.setParameter(1, -8.0391283);
        Coordenada coordenada = (Coordenada) query.getSingleResult();
        assertEquals(-8.0391283, coordenada.getPontoX(), 0.0);

    }

    @Test
    public void t04_recuperarPorPontoYSQL() {

        logger.info("Executando: recuperarPorPontoYSQL");
        Query query = em.createNamedQuery("Coordenada.RecuperarPorPontoYSQL");
        query.setParameter(1, -34.8238971);
        Coordenada coordenada = (Coordenada) query.getSingleResult();
        assertEquals(-34.8238971, coordenada.getPontoY(), 0.0);

    }

}
