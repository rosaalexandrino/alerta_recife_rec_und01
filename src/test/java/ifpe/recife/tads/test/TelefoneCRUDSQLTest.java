package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Telefone;
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
public class TelefoneCRUDSQLTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public TelefoneCRUDSQLTest(){
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
    public void t01_atualizaNumeroTelefoneSQL() {

        logger.info("Executando: atualizaNumeroTelefoneSQL");
        Query query = em.createNamedQuery("Telefone.AtualizaNumeroSQL");
        query.setParameter(1, "999558762");
        query.setParameter(2, 1L);
        query.executeUpdate();
        em.flush();
        Telefone tel = em.find(Telefone.class, 1L);
        assertNotNull(tel);
        assertEquals("999558762", tel.getNumero());

    }

    @Test
    public void t01_removeTelefoneSQL() {

        logger.info("Executando: removeTelefoneSQL");
        Query queryRemove = em.createNamedQuery("Telefone.RemoveTelefoneSQL");
        queryRemove.setParameter(1, 2L);
        queryRemove.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Telefone.class, 2L));

    }
 
}
