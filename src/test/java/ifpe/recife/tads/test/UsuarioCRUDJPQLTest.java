package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Telefone;
import ifpe.recife.tads.alerta_recife.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class UsuarioCRUDJPQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public UsuarioCRUDJPQLTest() {
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
    public void t01_criaUsuario() {

        logger.info("Executando: criaUsuario");
        Usuario usuario = new Usuario();
        usuario.setEmail("pedro.dantas@gmail.com");
        usuario.setPrimeiroNome("Pedro");
        usuario.setUltimoNome("Dantas");
        usuario.setSenha("queue");
        usuario.setHabilitado(true);
        
        Telefone telefone = new Telefone();
        telefone.setDdd("081");
        telefone.setNumero("997934730");
        List<Telefone> telefones = new ArrayList<Telefone>();
        telefones.add(telefone);
       
        usuario.setTelefones(telefones);
        em.persist(usuario);
        em.flush();
        assertNotNull(usuario.getId());

    }
    
    @Test
    public void t02_criaUsuarioInvalido() {

        logger.info("Executando: criaUsuarioInvalido");
        Usuario usuario = new Usuario();
        usuario.setEmail("alberto.freitas");
        usuario.setPrimeiroNome("Alberto");
        usuario.setUltimoNome("Freitas");
        usuario.setSenha("qu/////@@33534@@@@@@@@@");
        usuario.setHabilitado(true);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(3, constraintViolations.size());

    }
    
    @Test
    public void t03_atualizaUsuario() {
        
        logger.info("Executando: atualizaUsuario");
        Query query = em.createNamedQuery("Usuario.RecuperarPorEmail", Usuario.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter("email", "joanamendonca@gmail.com");
        Usuario usuario = (Usuario) query.getSingleResult();
        usuario.setUltimoNome("Souza");
        em.flush();
        usuario = (Usuario) query.getSingleResult();
        assertEquals("Souza", usuario.getUltimoNome());
        
    }
    
    @Test
    public void t04_atualizaUsuarioMerge() {

        logger.info("Executando: atualizaUsuarioMerge");
        Query query = em.createNamedQuery("Usuario.RecuperarPorEmail", Usuario.class);
        query.setParameter("email", "mariojorge@outlook.com");
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Usuario usuario = (Usuario) query.getSingleResult();
        assertNotNull(usuario);        
        em.clear();
        usuario.setHabilitado(false);
        em.merge(usuario);
        em.flush();
        usuario = (Usuario) query.getSingleResult();
        assertEquals(false, usuario.isHabilitado());    
        
    }
    
    @Test
    public void t05_removeUsuario() {

        logger.info("Executando: removeUsuario");
        Query query = em.createNamedQuery("Usuario.RecuperarPorEmail", Usuario.class);
        query.setParameter("email", "mariojorge@outlook.com");
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Usuario usuario = (Usuario) query.getSingleResult();
        assertNotNull(usuario);
        em.remove(usuario);
        em.flush();
        assertEquals(0, query.getResultList().size());
        
    }
    
}
