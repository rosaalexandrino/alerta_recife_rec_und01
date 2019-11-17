package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Solicitacao;
import ifpe.recife.tads.alerta_recife.TipoDeSolicitacao;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SolicitacaoConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public SolicitacaoConsultaTest() {
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
    public void t01_recuperaSolicitacaoPorDescricaoJPQL() {

        logger.info("Executando: recuperaSolicitacaoPorDescricaoJPQL");
        TypedQuery<Solicitacao> query = em.createNamedQuery("Solicitacao.RecuperarPorDescricao", Solicitacao.class);
        query.setParameter("descricao", "%barreira%");
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertTrue(solicitacao.getDescricao().contains("barreira"));
        });
        assertEquals(3, solicitacoes.size());

    }

    @Test
    public void t02_recuperaSolicitacaoPorTipoJPQL() {

        logger.info("Executando: recuperaSolicitacaoPorTipoJPQL");
        TypedQuery<Solicitacao> query = em.createNamedQuery("Solicitacao.RecuperarPorTipo", Solicitacao.class);
        query.setParameter("tipo", TipoDeSolicitacao.VISTORIA.tipo);
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertTrue(solicitacao.getTipoDeSolicitacao() == TipoDeSolicitacao.VISTORIA.tipo);
        });
        assertEquals(7, solicitacoes.size());
    }

    @Test
    public void t03_recuperaNaoAtendidosJPQL() {

        logger.info("Executando: recuperaNaoAtendidosJPQL");
        TypedQuery<Solicitacao> query = em.createNamedQuery("Solicitacao.RecuperarNaoAtendidos", Solicitacao.class);
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertTrue(solicitacao.getDataConclusao() == null);
        });
        assertEquals(11, solicitacoes.size());

    }

    @Test
    public void t04_recuperaPorDescricaoSQL() {

        logger.info("Executando: recuperaPorDescricaoSQL");
        Query query = em.createNamedQuery("Solicitacao.RecuperarPorDescricaoSQL");
        query.setParameter(1, "%barreira%");
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertTrue(solicitacao.getDescricao().contains("barreira"));
        });
        assertEquals(3, solicitacoes.size());

    }

    @Test
    public void t05_recuperaPorTipoSQL() {

        logger.info("Executando: recuperaPorTipoSQL");
        Query query = em.createNamedQuery("Solicitacao.RecuperarPorTipoSQL");
        query.setParameter(1, TipoDeSolicitacao.VISTORIA.tipo);
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertTrue(solicitacao.getTipoDeSolicitacao() == TipoDeSolicitacao.VISTORIA.tipo);
        });
        assertEquals(7, solicitacoes.size());

    }
    
    @Test
    public void t06_recuperaPorDataSolicitacaoSQL() {

        logger.info("Executando: recuperaPorDataSolicitacaoSQL");
        Query query = em.createNamedQuery("Solicitacao.RecuperarPorDataSolicitacaoSQL");
        Calendar date = new GregorianCalendar(2019,6,20);
        query.setParameter(1, date.getTime());
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertEquals(solicitacao.getDataSolicitacao(), date.getTime());
        });
        assertEquals(2, solicitacoes.size());

    }
    
        @Test
    public void t07_recuperaPorDataConclusaoSQL() {

        logger.info("Executando: recuperaPorDataConclusaoSQL");
        Query query = em.createNamedQuery("Solicitacao.RecuperarPorDataConclusaoSQL");
        Calendar date = new GregorianCalendar(2019,7,2);
        query.setParameter(1, date.getTime());
        List<Solicitacao> solicitacoes = query.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            assertEquals(solicitacao.getDataConclusao(), date.getTime());
        });
        assertEquals(1, solicitacoes.size());

    }
    
}
