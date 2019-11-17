package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Contato;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class ContatoCRUDSQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ContatoCRUDSQLTest() {
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
    public void t01_atualizaDescricaoContatoSQL() {

        logger.info("Executando: atualizaDescricaoContatoSQL");
        Query query = em.createNamedQuery("Contato.AtualizaDescricaoContatoSQL");
        query.setParameter(1, "Defesa Civil");
        query.setParameter(2, 2L);
        query.executeUpdate();
        em.flush();
        Contato contato = em.find(Contato.class, 2L);
        assertNotNull(contato);
        assertEquals("Defesa Civil", contato.getDescricao());

    }

    @Test
    public void t01_removeContatoSQL() {

        logger.info("Executando: removeContatoSQL");
        Query queryRemove = em.createNamedQuery("Contato.RemoveContatoPorIdSQL");
        queryRemove.setParameter(1, 1L);
        queryRemove.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Contato.class, 1L));

    }

}
