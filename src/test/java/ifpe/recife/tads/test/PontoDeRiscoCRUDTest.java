
package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
import ifpe.recife.tads.alerta_recife.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheRetrieveMode;
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
import org.junit.Test;

public class PontoDeRiscoCRUDTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public PontoDeRiscoCRUDTest() {
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

//        logger.info("Executando: criaPontoDeRisco");
//        PontoDeRisco pr = new PontoDeRisco();
//
//        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.RecuperarPorId", Endereco.class);
//        query.setParameter("id", new Long("1"));
//        Endereco endereco = (Endereco) query.getSingleResult();
//        pr.setEndereco(endereco);
//        pr.setTipoDeRisco(TipoDeRisco.DESLIZAMENTO.tipo);
//        em.persist(pr);
//        em.flush();
//        assertNotNull(pr.getId());
    }

    @Test
    public void t02_atualizaTipoDeRisco() {

//        logger.info("Executando: atualizaTipoDeRisco");
//        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorTipoDeRiscoSQL");
//        query.setParameter(1, TipoDeRisco.ALAGAMENTO.tipo);
//        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
//        List<PontoDeRisco> pr = query.getResultList();
//        pr.forEach((PontoDeRisco pontoRisco) -> {
//            pontoRisco.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);
//        });           
//        em.flush();
//        pr = query.getResultList();
//        pr.forEach((PontoDeRisco pontoRisco) -> {
//            assertEquals(pontoRisco.getTipoDeRisco(), TipoDeRisco.ESTRUTURAL.tipo);
//        });        
    }
    
    @Test
    public void t03_atualizaTipoDeRiscoMerge() {

//        logger.info("Executando: atualizaTipoDeRiscoMerge");
//        TypedQuery<PontoDeRisco> query = em.createNamedQuery("PontoDeRisco.RecuperarPorIdEndereco", PontoDeRisco.class);
//        Long l = new Long("3");
//        query.setParameter("id", l);
//        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
//        PontoDeRisco pr = (PontoDeRisco) query.getSingleResult();
//        assertNotNull(pr);
//        em.clear();
//        pr.setTipoDeRisco(TipoDeRisco.ALAGAMENTO.tipo);
//        em.merge(pr);
//        em.flush();
//        pr = (PontoDeRisco) query.getSingleResult();
//        assertEquals(TipoDeRisco.ALAGAMENTO.tipo, pr.getTipoDeRisco());
    }

    @Test
    public void t04_removePontoDeRisco() {

//        logger.info("Executando: removePontoDeRisco");
//        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorTipoDeRiscoSQL");
//        query.setParameter(1, TipoDeRisco.ALAGAMENTO.tipo);
//        List<PontoDeRisco> pr = query.getResultList();
//        pr.forEach((PontoDeRisco pontoRisco) -> {
//            assertNotNull(pontoRisco);
//            em.remove(pontoRisco);
//        });           
//        em.flush();
//        assertEquals(0, query.getResultList().size());

    }
    
}