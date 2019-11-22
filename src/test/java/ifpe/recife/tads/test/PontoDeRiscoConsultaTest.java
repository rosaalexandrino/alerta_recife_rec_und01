
package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class PontoDeRiscoConsultaTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public PontoDeRiscoConsultaTest() {
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
    public void t01_recuperaPorTipoDeRiscoJPQL() {

        logger.info("Executando: recuperaPorTipoDeRiscoJPQL");
        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorTipo", PontoDeRisco.class);
        query.setParameter("tipo", TipoDeRisco.ALAGAMENTO.tipo);
        List<PontoDeRisco> pr = query.getResultList();
        pr.forEach((PontoDeRisco pontoRisco) -> {
            assertEquals(pontoRisco.getTipoDeRisco(), TipoDeRisco.ALAGAMENTO.tipo);
        });     
        assertEquals(2, pr.size());
        
    }
    
    @Test
    public void t02_RecuperarPorEnderecoJPQL() {

        logger.info("Executando: recuperaPorEnderecoJPQL");
        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorEndereco", PontoDeRisco.class);
        query.setParameter("endereco", em.find(Endereco.class, 2L));
        List<PontoDeRisco> pr = query.getResultList();
        pr.forEach((PontoDeRisco pontoRisco) -> {
            assertEquals(pontoRisco.getEndereco(), em.find(Endereco.class, 2L));
        });     
        assertEquals(1, pr.size());
        
    }
    
    @Test
    public void t03_recuperaPorTipoDeRiscoSQL() {

        logger.info("Executando: recuperaPorTipoDeRiscoSQL");
        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorTipoSQL");
        query.setParameter(1, TipoDeRisco.ESTRUTURAL.tipo);
        List<PontoDeRisco> pr = query.getResultList();
        pr.forEach((PontoDeRisco pontoRisco) -> {
            assertEquals(pontoRisco.getTipoDeRisco(), TipoDeRisco.ESTRUTURAL.tipo);
        });     
        assertEquals(2, pr.size());
        
    }
    
    @Test
    public void t04_RecuperarPorEnderecoSQL() {

        logger.info("Executando: recuperaPorEnderecoSQL");
        Query query = em.createNamedQuery("PontoDeRisco.RecuperarPorEnderecoSQL");
        query.setParameter(1, em.find(Endereco.class, 2L));
        List<PontoDeRisco> pr = query.getResultList();
        pr.forEach((PontoDeRisco pontoRisco) -> {
            assertEquals(pontoRisco.getEndereco(), em.find(Endereco.class, 2L));
        });     
        assertEquals(1, pr.size());
             
    }
    
}