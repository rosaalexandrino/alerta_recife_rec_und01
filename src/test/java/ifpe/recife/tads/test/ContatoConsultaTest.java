package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Contato;
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
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class ContatoConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ContatoConsultaTest() {
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
    public void t01_recuperaContatoPorDescricaoJPQL() {

        logger.info("Executando: recuperaContatoPorDescricaoJPQL");
        Query query = em.createNamedQuery("Contato.RecuperarPorDescricao", Contato.class);
        query.setParameter("descricao", "%Regional%");
        List<Contato> contatos = query.getResultList();
        contatos.forEach((Contato contato) -> {
            assertTrue(contato.getDescricao().contains("Regional"));
        });
        assertEquals(7, contatos.size());

    }

    @Test
    public void t02_recuperaContatosJPQL() {

        logger.info("Executando: recuperaContatosJPQL");
        Query query = em.createNamedQuery("Contato.RecuperarTodosContatos", Contato.class);
        List<Contato> contatos = query.getResultList();
        assertEquals(14, contatos.size());

    }

    @Test
    public void t03_recuperaContatoPorNumeroJPQL() {

        logger.info("Executando: recuperaContatoPorNumeroJPQL");
        Query query = em.createNamedQuery("Contato.RecuperarPorNumero", Contato.class);
        query.setParameter("numero", "8132556679");
        Contato contato = (Contato) query.getSingleResult();
        assertEquals(contato.getNumero(), "8132556679");
        
    }

    @Test
    public void t04_recuperaContatoPorDescricaoSQL() {

        logger.info("Executando: recuperaContatoPorDescricaoSQL");
        Query query = em.createNamedQuery("Contato.RecuperarPorDescricaoSQL");
        query.setParameter(1, "%Regional%");
        List<Contato> contatos = query.getResultList();
        contatos.forEach((Contato contato) -> {
            assertTrue(contato.getDescricao().contains("Regional"));
        });
        assertEquals(7, contatos.size());

    }

    @Test
    public void t05_recuperaContatosSQL() {

        logger.info("Executando: recuperaContatosSQL");
        Query query = em.createNamedQuery("Contato.RecuperarTodosContatosSQL");
        List<Contato> contatos = query.getResultList();
        assertEquals(14, contatos.size());

    }

    @Test
    public void t06_recuperaContatoPorNumeroSQL() {

        logger.info("Executando: recuperaContatoPorNumeroSQL");
        Query query = em.createNamedQuery("Contato.RecuperarPorNumeroSQL");
        query.setParameter(1, "8132556679");
        Contato contato = (Contato) query.getSingleResult();
        assertEquals(contato.getNumero(), "8132556679");
        
    }
    
}
