package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Contato;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class ContatoCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ContatoCRUDJPQLTest() {
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
    public void t01_criaContato() {

        logger.info("Executando: criaContato");
        Contato contato = new Contato();
        contato.setNumero("156");
        contato.setDescricao("Emlurb");
        em.persist(contato);
        em.flush();
        assertNotNull(contato.getId());

    }

    @Test
    public void t02_criaContatoInvalido() {

        logger.info("Executando: criaContatoInvalido");
        Contato contato = new Contato();
        contato.setNumero("1");
        contato.setDescricao("@@contato");
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Contato>> constraintViolations = validator.validate(contato);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void t03_atualizaContato() {
        
        logger.info("Executando: aualizaContato");
        Contato contato = em.find(Contato.class, 7L);
        assertNotNull(contato);
        contato.setNumero("34508766");
        em.flush();
        assertEquals("34508766", em.find(Contato.class, 7L).getNumero());

    }

    @Test
    public void t04_atualizaContatoMerge() {
        
        logger.info("Executando: AtualizaContatoMerge");
        Contato contato = em.find(Contato.class, 5L);
        assertNotNull(contato);
        em.clear();
        contato.setNumero("33518976");
        em.merge(contato);
        em.flush();
        assertEquals("33518976", em.find(Contato.class, 5L).getNumero());
        
    }

    @Test
    public void t05_removeContato() {
        
        logger.info("Executando: removeContato");
        Contato contato = em.find(Contato.class, 1L);
        assertNotNull(contato);
        em.remove(contato);
        em.flush();
        assertEquals(null, em.find(Contato.class, 1L));
        
    }
}
