package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
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
public class AdministradorConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public AdministradorConsultaTest() {
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
    public void t01_recuperaAdministradorPorMatriculaJPQL() {

        logger.info("Executando: recuperaAdministradorPorMatriculaJPQL");
        Query query = em.createNamedQuery("Administrador.RecuperarPorMatricula", Administrador.class);
        query.setParameter("matricula", "96532441");
        Administrador adm = (Administrador) query.getSingleResult();
        assertTrue(adm.getMatricula().equals("96532441"));

    }

    @Test
    public void t02_recuperaAdministradorPorCargoJPQL() {

        logger.info("Executando: recuperaAdministradorPorCargoJPQL");
        Query query = em.createNamedQuery("Administrador.RecuperarPorCargo", Administrador.class);
        query.setParameter("cargo", Cargo.TECNICO.numCargo);
        List<Administrador> adms = query.getResultList();
        adms.forEach((Administrador adm) -> {
            assertTrue(adm.getCargo() == Cargo.TECNICO.numCargo);
        });
        assertEquals(4, adms.size());

    }

    @Test
    public void t03_recuperaAdministradorPorMatriculaSQL() {

        logger.info("Executando: recuperaAdministradorPorMatriculaSQL");
        Query query = em.createNamedQuery("Administrador.RecuperarPorMatriculaSQL");
        query.setParameter(1, "96532441");
        Administrador adm = (Administrador) query.getSingleResult();
        assertTrue(adm.getMatricula().equals("96532441"));

    }

    @Test
    public void t04_recuperaAdministradorPorCargoSQL() {

        logger.info("Executando: recuperaAdministradorPorCargoSQL");
        Query query = em.createNamedQuery("Administrador.RecuperarPorCargoSQL");
        query.setParameter(1, Cargo.TECNICO.numCargo);
        List<Administrador> adms = query.getResultList();
        adms.forEach((Administrador adm) -> {
            assertTrue(adm.getCargo() == Cargo.TECNICO.numCargo);
        });
        assertEquals(4, adms.size());

    }
    
    @Test
    public void t05_recuperaTodosAdministradoresJPQL() {

        logger.info("Executando: recuperaTodosAdministradoresJPQL");
        Query query = em.createNamedQuery("Administrador.RecuperarAdministradores", Administrador.class);
        List<Administrador> adms = query.getResultList();
        assertEquals(8, adms.size());

    }

    @Test
    public void t06_recuperaAdministradoresHabilitadosJPQL() {

        logger.info("Executando: recuperaAdministradoresHabilitadosJPQL");
        Query query = em.createNamedQuery("Administrador.RecuperarAdministradoresHabilitados", Administrador.class);
        query.setParameter("habilitado", true);
        List<Administrador> adms = query.getResultList();
        assertEquals(7, adms.size());

    }

    @Test
    public void t07_recuperaAdministradoresHabilitadosSQL() {

        logger.info("Executando: recuperaAdministradoresHabilitadosSQL");
        Query query = em.createNamedQuery("Administrador.RecuperarAdministradoresHabilitadosSQL");
        query.setParameter(1, true);
        List<Administrador> adms = query.getResultList();
        assertEquals(7, adms.size());

    }

}
