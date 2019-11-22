package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.Solicitacao;
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
import org.junit.Test;

public class EnderecoCRUDSQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoCRUDSQLTest() {
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
    public void t01_atualizaEnderecoSQL() {

        logger.info("Executando: atualizaEnderecoSQL");
        Query query = em.createNamedQuery("Endereco.AtualizaRuaSQL");
        query.setParameter(1, "Rua Nove");
        query.setParameter(2, 3L);
        query.executeUpdate();
        em.flush();
        assertEquals("Rua Nove", em.find(Endereco.class, 3L).getRua());

    }

    @Test
    public void t02_removeEnderecoSQL() {

        logger.info("Executando: removeCoordenadaSQL");

        Query queryPtoRisco = em.createNativeQuery("SELECT ID, TIPO_RISCO, ID_ENDERECO FROM TB_PONTO_RISCO WHERE ID_ENDERECO = ?1", PontoDeRisco.class);
        queryPtoRisco.setParameter(1, 2L);

        List<PontoDeRisco> ptos = queryPtoRisco.getResultList();
        ptos.forEach((PontoDeRisco pto) -> {
            em.clear();
            Query queryRemovePto = em.createNativeQuery("DELETE FROM TB_PONTO_RISCO WHERE ID = ?1", PontoDeRisco.class);
            queryRemovePto.setParameter(1, pto.getId());
            queryRemovePto.executeUpdate();
            em.flush();
            assertEquals(null, em.find(PontoDeRisco.class, pto.getId()));

        });

        Query querySolicitacao = em.createNativeQuery("SELECT ID, DATA_CONCLUSAO, DATA_SOLICITACAO, DESCRICAO, TIPO_SOLICITACAO, ID_ENDERECO, ID_USUARIO FROM TB_SOLICITACAO WHERE ID_ENDERECO = ?1", Solicitacao.class);
        querySolicitacao.setParameter(1, 2L);

        List<Solicitacao> solicitacoes = querySolicitacao.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            em.clear();
            Query queryRemoveSol = em.createNativeQuery("DELETE FROM TB_SOLICITACAO WHERE ID = ?1", Solicitacao.class);
            queryRemoveSol.setParameter(1, solicitacao.getId());
            queryRemoveSol.executeUpdate();
            em.flush();
            assertEquals(null, em.find(Solicitacao.class, solicitacao.getId()));

        });

        em.clear();
        Query queryRemoveEndereco = em.createNamedQuery("Endereco.DeletaEnderecoSQL");
        queryRemoveEndereco.setParameter(1, 2L);
        queryRemoveEndereco.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Endereco.class, 2L));

    }

}
