package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Solicitacao;
import ifpe.recife.tads.alerta_recife.Usuario;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class UsuarioCRUDSQLTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public UsuarioCRUDSQLTest(){
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
    public void t01_atualizaSenhaUsuarioSQL(){
     
        logger.info("Executando: atualizaSenhaUsuarioSQL");
        Query query = em.createNamedQuery("Usuario.AtualizaSenhaUsuarioSQL");
        query.setParameter(1, "bHyi120");
        query.setParameter(2, 1L);
        query.executeUpdate();
        em.flush();
        Usuario usuario = em.find(Usuario.class, 1L);
        assertNotNull(usuario);
        assertEquals("bHyi120", usuario.getSenha());
        
    }
    
    @Test
    public void t02_removeUsuarioSQL() {

        logger.info("Executando: removeUsuarioSQL");
        Query querySolicitacoes = em.createNamedQuery("Solicitacao.RecuperarPorUsuarioSQL");
        querySolicitacoes.setParameter(1, 4L);
        List<Solicitacao> solicitacoes = querySolicitacoes.getResultList();
        solicitacoes.forEach((Solicitacao solicitacao) -> {
            
            em.clear();
            Query queryRemoveSol = em.createNamedQuery("Solicitacao.RemoveSolicitacaoSQL");
            queryRemoveSol.setParameter(1, solicitacao.getId());
            queryRemoveSol.executeUpdate();
            em.flush();
            assertEquals(null, em.find(Solicitacao.class, solicitacao.getId()));
            
        });
        
        em.clear();
        Query queryRemoveUsuario = em.createNamedQuery("Usuario.DeletaPorIdSQL");
        queryRemoveUsuario.setParameter(1, 4L);
        queryRemoveUsuario.executeUpdate();
        em.flush();
        assertEquals(null, em.find(Usuario.class, 4L));
        
    }
    
}
