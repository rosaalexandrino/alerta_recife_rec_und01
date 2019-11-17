package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Coordenada;
import ifpe.recife.tads.alerta_recife.Endereco;
import ifpe.recife.tads.alerta_recife.PontoDeRisco;
import ifpe.recife.tads.alerta_recife.TipoDeRisco;
import java.util.HashMap;
import java.util.Map;
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
import org.junit.Test;

public class EnderecoCRUDTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoCRUDTest() {
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

    // Insere novo endereço
    @Test
    public void t01_criaEndereco() {
    
        logger.info("Executando: Cria dados");
        Coordenada coord = new Coordenada();
        Endereco ender = new Endereco();
        PontoDeRisco ptorisco = new PontoDeRisco();
        
        coord.setPontoX(-8.1020010);
        coord.setPontoY(-34.9382211);
        
        ender.setRua("Rua de Teste");
        ender.setNumero("100");
        ender.setBairro("Ibura");
        ender.setCidade("Recife");
        ender.setCoordenada(coord);
        
        ptorisco.setTipoDeRisco(TipoDeRisco.DESLIZAMENTO.tipo);
        ender.setPontoDeRisco(ptorisco);
        
        em.persist(ender);
        em.flush();
        assertNotNull(ender.getId());
        assertNotNull(coord.getId());
        assertNotNull(ptorisco.getId());
        logger.log(Level.INFO, "Cadastro realizado com sucesso!", ender);
        
    }

    // Atualização em:
    //<TB_ENDERECO id_coordenada = "2" rua = "Rua Armando Silva" numero = "95" bairro = "Afogados" cidade = "Recife"/>
    @Test
    public void t02_atualizaEndereco() {

        logger.info("Executando: Atualização de dados");
        Endereco ender;
        String consulta = "SELECT e FROM Endereco e WHERE e.rua=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, "Rua Armando Silva");
        ender = (Endereco) query.getSingleResult();
        ender.setRua("Rua Armando da Silva");
        em.flush();
        query.setParameter(1, "Rua Armando da Silva");
        ender = (Endereco) query.getSingleResult();
        assertEquals("Rua Armando da Silva", ender.getRua());
        
    }

    // Modificação em:
    //<TB_ENDERECO id_coordenada = "3" rua = "Rua do Cacique" numero = "40" bairro = "Ibura" cidade = "Recife"/>
    @Test
    public void t03_atualizaComMerge() {

        logger.info("Executando: Atualização de dados com merge");
        Endereco ender;
        String consulta = "SELECT e FROM Endereco e WHERE e.rua=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, "Rua do Cacique");
        ender = (Endereco) query.getSingleResult();
        assertNotNull(ender);
        em.clear();
        ender.setRua("Rua do Cacique Em Pé");
        em.merge(ender);
        em.flush();
        query.setParameter(1, "Rua do Cacique Em Pé");
        ender = (Endereco) query.getSingleResult();
        assertEquals("Rua do Cacique Em Pé", ender.getRua());
    }

    // Remoção de:
    // <TB_ENDERECO id_coordenada = "5" rua = "Rua Córrego do Joaquim" numero = "20" bairro = "Nova Descoberta" cidade = "Recife"/>
    @Test
    public void t04_removeEndereco() {
        
        logger.info("Executando: Remove dados");
        Endereco ender;
        String consulta = "SELECT e FROM Endereco e WHERE e.id=?1";
        Query query = em.createQuery(consulta);
        long id = 5;
        query.setParameter(1, id);
        ender = (Endereco) query.getSingleResult();
        em.remove(ender);
        em.flush();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Endereco apagado = em.find(Endereco.class, id, map);
        assertNull(apagado);
        
        
    }
    
}
