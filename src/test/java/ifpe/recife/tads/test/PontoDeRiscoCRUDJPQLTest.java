package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
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
public class PontoDeRiscoCRUDJPQLTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public PontoDeRiscoCRUDJPQLTest() {
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
    public void t01_criaPontoDeRisco() {

        logger.info("Executando: criaPontoDeRisco");
        PontoDeRisco pr = new PontoDeRisco();

        Endereco endereco = em.find(Endereco.class, 1L);
        assertNotNull(endereco);
        pr.setEndereco(endereco);
        pr.setTipoDeRisco(TipoDeRisco.DESLIZAMENTO.tipo);
        em.persist(pr);
        em.flush();
        assertNotNull(pr.getId());
    }
    
    @Test
    public void t02_criaPontoDeRiscoInvalido() {

        logger.info("Executando: criaPontoDeRiscoInvalido");
        PontoDeRisco pr = new PontoDeRisco();
        pr.setTipoDeRisco(TipoDeRisco.DESLIZAMENTO.tipo);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PontoDeRisco>> constraintViolations = validator.validate(pr);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(1, constraintViolations.size());
        
    }

    @Test
    public void t03_atualizaTipoDeRisco() {

        logger.info("Executando: atualizaTipoDeRisco");
        PontoDeRisco pr = em.find(PontoDeRisco.class, 2L);
        assertNotNull(pr);
        pr.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);
        em.flush();
        assertEquals(TipoDeRisco.ESTRUTURAL.tipo, em.find(PontoDeRisco.class, 2L).getTipoDeRisco());  

    }
    
@Test
    public void t04_atualizaTipoDeRiscoMerge() {

        logger.info("Executando: atualizaTipoDeRiscoMerge");
        PontoDeRisco pr = em.find(PontoDeRisco.class, 2L);
        assertNotNull(pr);
        em.clear();
        pr.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);
        em.merge(pr);
        em.flush();
        assertEquals(TipoDeRisco.ESTRUTURAL.tipo, em.find(PontoDeRisco.class, 2L).getTipoDeRisco());  

    }

    @Test
    public void t05_removePontoDeRisco() {

        logger.info("Executando: removePontoDeRisco");
        PontoDeRisco pr = em.find(PontoDeRisco.class, 5L);
        assertNotNull(pr);
        em.remove(pr);
        em.flush();
        assertEquals(null, em.find(PontoDeRisco.class, 5L));  


    }
    
}