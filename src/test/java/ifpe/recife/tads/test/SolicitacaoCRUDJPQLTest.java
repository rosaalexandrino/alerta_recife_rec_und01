package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.Solicitacao;
import ifpe.recife.tads.alerta_recife.TipoDeSolicitacao;
import ifpe.recife.tads.alerta_recife.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class SolicitacaoCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public SolicitacaoCRUDJPQLTest() {
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
    public void t01_criaSolicitacao() {

        logger.info("Executando: criaSolicitacao");
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setUsuario(em.find(Usuario.class, 1L));
        solicitacao.setEndereco(em.find(Endereco.class, 1L));
        solicitacao.setDescricao("Solicito vistoria para encosta que deslizou atingindo imóvel");
        solicitacao.setTipoDeSolicitacao(TipoDeSolicitacao.VISTORIA.tipo);
        Calendar date = new GregorianCalendar(2019,10,14);
        solicitacao.setDataSolicitacao(date.getTime());
        em.persist(solicitacao);
        em.flush();
        assertNotNull(solicitacao.getId());

    }

    @Test
    public void t02_criaSolicitacaoInvalida() {

        logger.info("Executando: criaSolicitacaoInvalida");
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setUsuario(em.find(Usuario.class, 2L));
        solicitacao.setEndereco(em.find(Endereco.class, 2L));
        solicitacao.setDescricao("Solicito lona para encosta com urgência");
        solicitacao.setTipoDeSolicitacao(TipoDeSolicitacao.VISTORIA.tipo);
//        Date date = Calendar.getInstance().getTime();
//        solicitacao.setDataSolicitacao(date);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Solicitacao>> constraintViolations = validator.validate(solicitacao);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(1, constraintViolations.size());
        
    }

    @Test
    public void t03_atualizaSolicitacao() {

        logger.info("Executando: atualizaSolicitacao");
        Solicitacao solicitacao = em.find(Solicitacao.class, 1L);
        assertNotNull(solicitacao);
        solicitacao.setTipoDeSolicitacao(TipoDeSolicitacao.LONA.tipo);
        em.flush();
        solicitacao = em.find(Solicitacao.class, 1L);
        assertEquals(TipoDeSolicitacao.LONA.tipo, solicitacao.getTipoDeSolicitacao());

    }

    @Test
    public void t04_atualizaSolicitacaoMerge() {

        logger.info("Executando: atualizaSolicitacaoMerge");
        Solicitacao solicitacao = em.find(Solicitacao.class, 2L);
        assertNotNull(solicitacao);
        solicitacao.setTipoDeSolicitacao(TipoDeSolicitacao.VISTORIA.tipo);
        em.merge(solicitacao);
        em.flush();
        solicitacao = em.find(Solicitacao.class, 2L);
        assertEquals(TipoDeSolicitacao.VISTORIA.tipo, solicitacao.getTipoDeSolicitacao());

    }

    @Test
    public void t05_removeSolicitacao() {

        logger.info("Executando: removeSolicitacao");
        Solicitacao solicitacao = em.find(Solicitacao.class, 1L);
        assertNotNull(solicitacao);
        em.remove(solicitacao);
        em.flush();
        assertEquals(null, em.find(Solicitacao.class, 1L));

    }

}
