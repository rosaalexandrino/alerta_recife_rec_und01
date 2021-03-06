package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
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
public class AdministradorCRUDSQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public AdministradorCRUDSQLTest() {
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
    public void t01_atualizaCargoAdministradorSQL(){
     
        logger.info("Executando: atualizaCargoAdministradorSQL");
        Query query = em.createNamedQuery("Administrador.AtualizaCargoAdministradorSQL");
        query.setParameter(1, Cargo.TECNICO.numCargo);
        query.setParameter(2, 6L);
        query.executeUpdate();
        em.flush();
        Administrador adm = em.find(Administrador.class, 6L);
        assertNotNull(adm);
        assertEquals(Cargo.TECNICO.numCargo, adm.getCargo());
        
    }
    
    @Test
    public void t02_removeAdministradorSQL() {

        logger.info("Executando: removeAdministradorSQL");
        Query queryRemove = em.createNamedQuery("Administrador.DeletaPorIdSQL");
        queryRemove.setParameter(1, 6L);
        queryRemove.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Administrador.class, 6L));
        
    }
    
}
