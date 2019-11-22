package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.Endereco;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheRetrieveMode;
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
public class EnderecoConsultaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoConsultaTest() {
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
    public void t01_recuperaEnderecoPorNumeroJPQL() {
        
        logger.info("Executando: recuperaEnderecoPorNumeroJPQL");
        Query query = em.createNamedQuery("Endereco.RecuperarPorNumero", Endereco.class);
        query.setParameter("numero", "125");
        List<Endereco> enderecos = query.getResultList();
        enderecos.forEach((Endereco endereco) -> {
            assertEquals(endereco.getNumero(),"125");
        });
        assertEquals(1, enderecos.size());
 
    }
    
    @Test
    public void t02_recuperaEnderecoPorRuaJPQL() {

        logger.info("Executando: recuperaEnderecoPorRuaJPQL");
        Query query = em.createNamedQuery("Endereco.RecuperarPorRua", Endereco.class);
        query.setParameter("rua", "Rua da Ladeira");
        List<Endereco> enderecos = query.getResultList();
        enderecos.forEach((Endereco endereco) -> {
            assertEquals(endereco.getRua(),"Rua da Ladeira");
        });
        assertEquals(1, enderecos.size());
 
    }
    
    @Test
    public void t03_recuperaEnderecoPorBairroJPQL() {

        logger.info("Executando: recuperaEnderecoPorBairroJPQL");
        Query query = em.createNamedQuery("Endereco.RecuperarPorBairro", Endereco.class);
        query.setParameter("bairro", "Nova Descoberta");
        List<Endereco> enderecos = query.getResultList();
        enderecos.forEach((Endereco endereco) -> {
            assertEquals(endereco.getBairro(),"Nova Descoberta");
        });
        assertEquals(1, enderecos.size());
 
    }
    
    @Test
    public void t04_recuperaEnderecoPorBairroSQL() {

        logger.info("Executando: recuperaEnderecoPorBairroSQL");
        Query query = em.createNamedQuery("Endereco.RecuperarEnderecosPorBairroSQL");
        query.setParameter(1, "Ibura");
        List<Endereco> enderecos = query.getResultList();
        enderecos.forEach((Endereco endereco) -> {
            assertEquals(endereco.getBairro(),"Ibura");
        });
        assertEquals(2, enderecos.size());
 
    }
    
}
