package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
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
public class PontoDeRiscoCRUDSQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public PontoDeRiscoCRUDSQLTest() {
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
    public void t01_atualizaTipoDeRiscoSQL(){
     
        logger.info("Executando: atualizaTipoDeRiscoSQL");
        Query query = em.createNamedQuery("PontoDeRisco.AtualizaTipoSQL");
        query.setParameter(1, TipoDeRisco.DESLIZAMENTO.tipo);
        query.setParameter(2, 6L);
        query.executeUpdate();
        em.flush();
        assertEquals(TipoDeRisco.DESLIZAMENTO.tipo, em.find(PontoDeRisco.class, 6L).getTipoDeRisco());
        
    }
    
    @Test
    public void t02_removePontoDeRiscoSQL() {

        logger.info("Executando: removePontoDeRiscoSQL");
        Query queryRemove = em.createNamedQuery("PontoDeRisco.DeletaPorIdSQL");
        queryRemove.setParameter(1, 4L);
        queryRemove.executeUpdate();
        em.flush();
        assertEquals(null, em.find(PontoDeRisco.class, 4L));
        
    }
    
}
