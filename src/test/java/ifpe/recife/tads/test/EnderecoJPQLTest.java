package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Contato;
import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.test.DataSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
public class EnderecoJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoJPQLTest() {
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
    public void t01_recuperaEnderecoPorRua() {

        logger.info("Executando: recuperaEnderecoPorRua");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.RecuperarPorRua", Endereco.class);
        query.setParameter("rua", "Rua Leônidas Cravo Gama");
        Endereco ender = (Endereco) query.getSingleResult();
        assertTrue(ender.getRua().equals("Rua Leônidas Cravo Gama"));

    }
    
    @Test
    public void t02_recuperaEnderecoPorBairro() {

        logger.info("Executando: recuperaEnderecoPorBairroAprox");
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.RecuperarPorBairroAprox", Endereco.class);
        query.setParameter("bairro", "Ibura");
        List<Endereco> enderec = query.getResultList();
        enderec.forEach((Endereco ender) -> {
            assertTrue(ender.getBairro().contains("Ibura"));
        });
        assertEquals(2, enderec.size());

    }
 
}
