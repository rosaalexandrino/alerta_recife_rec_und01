package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Telefone;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class TelefoneConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public TelefoneConsultaTest() {
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
    public void t01_recuperaPorDDDJPQL() {

        logger.info("Executando: recuperaPorDDDJPQL");
        Query query = em.createNamedQuery("Telefone.RecuperarPorDDD", Telefone.class);
        query.setParameter("ddd", "081");
        List<Telefone> telefones = query.getResultList();
        telefones.forEach((Telefone tel) -> {
            assertTrue(tel.getDdd().equals("081"));
        });
        assertEquals(11, telefones.size());

    }

    @Test
    public void t02_recuperarTodosJPQL() {

        logger.info("Executando: recuperarTodosJPQL");
        Query query = em.createNamedQuery("Telefone.RecuperarTodosTelefones", Telefone.class);
        List<Telefone> telefones = query.getResultList();
        assertEquals(16, telefones.size());

    }

    @Test
    public void t03_recuperarPorNumeroJPQL() {

        logger.info("Executando: recuperarPorNumeroJPQL");
        Query query = em.createNamedQuery("Telefone.RecuperarPorNumero", Telefone.class);
        query.setParameter("numero", "999358013");
        Telefone telefone = (Telefone) query.getSingleResult();
        assertEquals("999358013", telefone.getNumero());

    }

    @Test
    public void t04_recuperaPorDDDSQL() {

        logger.info("Executando: recuperaPorDDDSQL");
        Query query = em.createNamedQuery("Telefone.RecuperarPorDDDSQL");
        query.setParameter(1, "081");
        List<Telefone> telefones = query.getResultList();
        telefones.forEach((Telefone tel) -> {
            assertTrue(tel.getDdd().equals("081"));
        });
        assertEquals(11, telefones.size());

    }

    @Test
    public void t05_recuperarTodosSQL() {

        logger.info("Executando: recuperarTodosSQL");
        Query query = em.createNamedQuery("Telefone.RecuperarTodosTelefonesSQL");
        List<Telefone> telefones = query.getResultList();
        assertEquals(16, telefones.size());

    }

    @Test
    public void t06_recuperarPorNumeroSQL() {

        logger.info("Executando: recuperarPorNumeroSQL");
        Query query = em.createNamedQuery("Telefone.RecuperarPorNumeroSQL");
        query.setParameter(1, "999358013");
        Telefone telefone = (Telefone) query.getSingleResult();
        assertEquals("999358013", telefone.getNumero());

    }

}
