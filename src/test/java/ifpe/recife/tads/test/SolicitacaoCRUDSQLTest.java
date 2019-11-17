package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Solicitacao;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class SolicitacaoCRUDSQLTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public SolicitacaoCRUDSQLTest() {
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
    public void t01_atualizaDataConclusaoSolicitacaoSQL() throws ParseException{
     
        logger.info("Executando: atualizaDataConclusaoSolicitacaoSQL");
        Query query = em.createNamedQuery("Solicitacao.AtualizaDataConclusaoSolicitacaoSQL");
        Calendar date = new GregorianCalendar(2019,10,14);
        query.setParameter(1, date.getTime());
        query.setParameter(2, 6L);
        query.executeUpdate();
        em.flush();
        Solicitacao solicitacao = em.find(Solicitacao.class, 6L);
        assertNotNull(solicitacao);
        assertEquals(date.getTime(), solicitacao.getDataConclusao());
        
    }
    
    @Test
    public void t02_removeSolicitacaoSQL() {

        logger.info("Executando: removeSolicitacaoSQL");
        Query queryRemove = em.createNamedQuery("Solicitacao.RemoveSolicitacaoSQL");
        queryRemove.setParameter(1, 4L);
        queryRemove.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Solicitacao.class, 4L));
        
    }

}
