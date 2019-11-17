package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Contato;
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
public class CoordenadaJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CoordenadaJPQLTest() {
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

//    @Test
//    public void t01_recuperaContatoPorDescricao() {
//
//        logger.info("Executando: recuperaContatoPorDescricao");
//        TypedQuery<Contato> query = em.createNamedQuery("Contato.RecuperarPorDescricao", Contato.class);
//        query.setParameter("descricao", "%Regional%");
//        List<Contato> contatos = query.getResultList();
//        contatos.forEach((Contato contato) ->{
//            assertTrue(contato.getDescricao().contains("Regional"));
//        });
//        assertEquals(7, contatos.size());
//
//    }
//
//    @Test
//    public void t02_recuperaContatos() {
//
//        logger.info("Executando: recuperaContatos");
//        TypedQuery<Contato> query = em.createNamedQuery("Contato.RecuperarContatos", Contato.class);
//        List<Contato> contatos = query.getResultList();
//        assertEquals(14, contatos.size());
//
//    }

}

//logger.info("Executando: recuperaContatoPorDescricao");
//        TypedQuery<Contato> query = em.createNamedQuery("Contato.RecuperarPorDescricao", Contato.class);
//        query.setParameter("descricao", "%Regional%");
//        List<Contato> contatos = query.getResultList();
//        contatos.forEach((Contato contato) ->{
//            assertTrue(contato.getDescricao().contains("Regional"));
//        });
//        assertEquals(7, contatos.size());