package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Coordenada;
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
public class EnderecoCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoCRUDJPQLTest() {
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
    public void t01_criaEndereco() {

        logger.info("Executando: criaEndereco");
        Endereco ender = new Endereco();
        Coordenada coord = new Coordenada();
        PontoDeRisco ptorisco = new PontoDeRisco();

        ender.setRua("Rua Lidia da Cruz Cavacante");
        ender.setNumero("50");
        ender.setBairro("Afogados");
        ender.setCidade("Recife");

        coord.setPontoX(-8.0786413);
        coord.setPontoY(-34.9098545);
        
        ptorisco.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);

        ender.setPontoDeRisco(ptorisco);
        ender.setCoordenada(coord);

        em.persist(ender);
        em.flush();
        assertNotNull(coord.getId());
        assertNotNull(ender.getId());
        assertNotNull(ptorisco.getId());

    }

    @Test
    public void t02_atualizaBairro() {

        logger.info("Executando: atualizaBairro");
        Endereco endereco = em.find(Endereco.class, 3L);
        endereco.setBairro("Curado");
        em.flush();
        assertSame("Curado", endereco.getBairro());

    }

    @Test
    public void t03_atualizaBairroMerge() {

        logger.info("Executando: atualizaBairroMerge");
        Endereco endereco = em.find(Endereco.class, 4L);
        em.clear();
        endereco.setBairro("Curado IV");
        em.merge(endereco);
        em.flush();
        assertSame("Curado IV", endereco.getBairro());

    }
    
    @Test
    public void t04_inserirEnderecoInvalido() {

        logger.info("Executando: inserirEnderecoInvalido");

        Endereco ender = new Endereco();
        Coordenada coord = new Coordenada();
        PontoDeRisco ptorisco = new PontoDeRisco();

        ender.setNumero("50");
        ender.setBairro("Afogados");
        ender.setCidade("Recife");

        coord.setPontoX(-8.0786213);
        coord.setPontoY(-34.9097545);
        
        ptorisco.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);

        ender.setPontoDeRisco(ptorisco);
        ender.setCoordenada(coord);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Endereco>> constraintViolations = validator.validate(ender);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(1, constraintViolations.size());

    }
    
    @Test
    public void t05_removeEndereco() {

        logger.info("Executando: removeEndereco");
        Endereco endereco = em.find(Endereco.class, 4L);
        assertNotNull(endereco);
        em.remove(endereco);
        em.flush();
        assertEquals(null, em.find(Endereco.class, 4L));

    }
    
}
