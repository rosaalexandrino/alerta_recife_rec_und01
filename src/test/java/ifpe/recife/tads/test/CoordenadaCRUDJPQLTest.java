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
public class CoordenadaCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CoordenadaCRUDJPQLTest() {
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
    public void t01_criaCoordenada() {

        logger.info("Executando: criaCoordenada");
        Coordenada coord = new Coordenada();
        Endereco ender = new Endereco();
        PontoDeRisco ptorisco = new PontoDeRisco();

        coord.setPontoX(-8.039628);
        coord.setPontoY(-34.8954143);

        ender.setRua("Rua Carneiro Vilela");
        ender.setNumero("90");
        ender.setBairro("Encruzilhada");
        ender.setCidade("Recife");

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
    public void t02_criaCoordenadaInvalida() {

        logger.info("Executando: criaCoordenadaInvalida");
        Coordenada coordenada = new Coordenada();
        coordenada.setPontoY(-34.8954143);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Coordenada>> constraintViolations = validator.validate(coordenada);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(1, constraintViolations.size());

    }

    @Test
    public void t03_atualizaCoordenada() {

        logger.info("Executando: atualizaCoordenada");
        Coordenada coordenada = em.find(Coordenada.class, 3L);
        coordenada.setPontoX(-8.0792002);
        em.flush();
        assertEquals(-8.0792002, em.find(Coordenada.class, 3L).getPontoX(), 0.0);

    }

    @Test
    public void t04_atualizaCordenadaMerge() {

        logger.info("Executando: atualizaCordenadaMerge");
        Coordenada coordenada = em.find(Coordenada.class, 3L);
        em.clear();
        coordenada.setPontoX(-8.0792004);
        em.merge(coordenada);
        em.flush();
        assertEquals(-8.0792004, em.find(Coordenada.class, 3L).getPontoX(), 0.0);

    }

    @Test
    public void t05_removeCoordenada() {

        logger.info("Executando: removeCoordenada");
        Coordenada coordenada = em.find(Coordenada.class, 9L);
        assertNotNull(coordenada);
        em.remove(coordenada);
        em.flush();
        assertEquals(null, em.find(Coordenada.class, 9L));

    }

}
