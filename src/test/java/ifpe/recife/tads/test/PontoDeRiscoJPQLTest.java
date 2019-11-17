
package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
import ifpe.recife.tads.alerta_recife.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
public class PontoDeRiscoJPQLTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public PontoDeRiscoJPQLTest() {
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
    public void t01_recuperaPorTipoDeRisco() {

//        logger.info("Executando: recuperaPorTipoDeRisco");
//        TypedQuery<PontoDeRisco> query = em.createNamedQuery("PontoDeRisco.RecuperarPorTipoDeRisco", PontoDeRisco.class);
//        query.setParameter("tipoDeRisco", TipoDeRisco.ALAGAMENTO.tipo);
//        List<PontoDeRisco> pr = query.getResultList();
//        pr.forEach((PontoDeRisco pontoRisco) -> {
//            assertEquals(pontoRisco.getTipoDeRisco(), TipoDeRisco.ALAGAMENTO.tipo);
//        });                       
    }
    
    @Test
    public void t02_RecuperarPorIdEndereco() {

//        logger.info("Executando: recuperarPorIdEndereco");
//        TypedQuery<PontoDeRisco> query = em.createNamedQuery("PontoDeRisco.RecuperarPorIdEndereco", PontoDeRisco.class);
//        Long l = new Long("1");
//        query.setParameter("id", l);
//        PontoDeRisco pr = query.getSingleResult();
//        assertEquals(pr.getEndereco().getId(), l);        
    }
    
}