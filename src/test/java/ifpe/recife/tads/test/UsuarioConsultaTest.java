package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.Contato;
import ifpe.recife.tads.alerta_recife.Usuario;
import ifpe.recife.tads.test.DataSet;
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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class UsuarioConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public UsuarioConsultaTest() {
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
    public void t01_recuperaUsuarioPorEmailJPQL() {

        logger.info("Executando: recuperaUsuarioPorEmailJPQL");
        Query query = em.createNamedQuery("Usuario.RecuperarPorEmail", Usuario.class);
        query.setParameter("email", "marcoslima@yahoo.com");
        Usuario usuario = (Usuario) query.getSingleResult();
        assertTrue(usuario.getEmail().equals("marcoslima@yahoo.com"));

    }

    @Test
    public void t02_recuperaUsuariosAtivosJPQL() {

        logger.info("Executando: recuperaUsuariosAtivosJPQL");
        Query query = em.createNamedQuery("Usuario.RecuperarAtivos", Usuario.class);
        query.setParameter("habilitado", true);
        List<Usuario> usuarios = query.getResultList();
        usuarios.forEach((Usuario usuario) -> {
            assertTrue(usuario.isHabilitado());
        });
        assertEquals(13, usuarios.size());

    }

    @Test
    public void t03_recuperarPorNomeJPQL() {

        logger.info("Executando: recuperarPorNomeJPQL");
        Query query = em.createNamedQuery("Usuario.RecuperarPorNome", Usuario.class);
        query.setParameter("nome", "%Silva%");
        query.setParameter("sobrenome", "%Silva%");
        List<Usuario> usuarios = query.getResultList();
        usuarios.forEach((Usuario usuario) -> {
            assertTrue(usuario.isHabilitado());
        });
        assertEquals(2, usuarios.size());

    }
    @Test
    public void t04_recuperaUsuarioPorEmailSQL() {

        logger.info("Executando: recuperaUsuarioPorEmailSQL");
        Query query = em.createNamedQuery("Usuario.RecuperarPorEmailSQL");
        query.setParameter(1, "marcoslima@yahoo.com");
        Usuario usuario = (Usuario) query.getSingleResult();
        assertTrue(usuario.getEmail().equals("marcoslima@yahoo.com"));

    }
    
    @Test
    public void t05_recuperaUsuariosAtivosSQL() {

        logger.info("Executando: recuperaUsuariosAtivosSQL");
        Query query = em.createNamedQuery("Usuario.RecuperarAtivosSQL");
        query.setParameter(1, true);
        List<Usuario> usuarios = query.getResultList();
        usuarios.forEach((Usuario usuario) -> {
            assertTrue(usuario.isHabilitado());
        });
        assertEquals(13, usuarios.size());

    }

    @Test
    public void t06_recuperarPorNomeSQL() {

        logger.info("Executando: recuperarPorNomeSQL");
        Query query = em.createNamedQuery("Usuario.RecuperarPorNomeSQL");
        query.setParameter(1, "%Silva%");
        query.setParameter(2, "%Silva%");
        List<Usuario> usuarios = query.getResultList();
        usuarios.forEach((Usuario usuario) -> {
            assertTrue(usuario.isHabilitado());
        });
        assertEquals(2, usuarios.size());

    }

}
