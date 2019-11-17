package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Telefone;
import ifpe.recife.tads.alerta_recife.Usuario;
import java.util.ArrayList;
import java.util.List;
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
public class TelefoneCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public TelefoneCRUDJPQLTest() {
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
    public void t01_criaTelefone() {

        logger.info("Executando: criaTelefone");
        Telefone telefone = new Telefone();
        telefone.setDdd("081");
        telefone.setNumero("996780772");
        em.persist(telefone);
        em.flush();
        assertNotNull(telefone.getId());

    }
    
    
    @Test
    public void t01_criaTelefoneInvalido() {

        logger.info("Executando: criaTelefoneInvalido");
        Telefone telefone = new Telefone();
        telefone.setDdd("08199");
        telefone.setNumero("9967807a2");
        
         Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Telefone>> constraintViolations = validator.validate(telefone);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(2, constraintViolations.size());

    }
    
     @Test
    public void t03_atualizaTelefone() {
        
        logger.info("Executando: atualizaTelefone");
        Telefone telefone = em.find(Telefone.class, 1L);
        assertNotNull(telefone);
        telefone.setNumero("34528999");
        em.flush();
        assertEquals("34528999", em.find(Telefone.class, 1L).getNumero());

    }

    @Test
    public void t04_atualizaTelefoneMerge() {
        
        logger.info("Executando: atualizaTelefoneMerge");
        Telefone telefone = em.find(Telefone.class, 2L);
        assertNotNull(telefone);
        em.clear();
        telefone.setNumero("34219769");
        em.merge(telefone);
        em.flush();
        assertEquals("34219769", em.find(Telefone.class, 2L).getNumero());
        
    }

    @Test
    public void t05_removeTelefone() {
        
        logger.info("Executando: removeTelefone");
        Telefone telefone = em.find(Telefone.class, 1L);
        assertNotNull(telefone);
        em.remove(telefone);
        em.flush();
        assertEquals(null, em.find(Telefone.class, 1L));
        
    }

}
