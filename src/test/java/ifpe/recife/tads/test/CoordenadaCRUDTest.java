package ifpe.recife.tads.test;

import ifpe.recife.tads.alerta_recife.Administrador;
import ifpe.recife.tads.alerta_recife.Cargo;
import ifpe.recife.tads.alerta_recife.Contato;
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
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

public class CoordenadaCRUDTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CoordenadaCRUDTest() {
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

    // Insere nova coordenada
    @Test
    public void t01_criaCoordenada() {
    
        logger.info("Executando: Cria dados");
        Coordenada coord = new Coordenada();
        Endereco ender = new Endereco();
        PontoDeRisco ptorisco = new PontoDeRisco();
        
        coord.setPontoX(-8.039628);
        coord.setPontoY(-34.8954143);
        
        ender.setRua("Rua Carneiro Vilela");
        ender.setNumero("90");
        ender.setBairro("Encruzilhada");
        ender.setCidade("Recife");
        
        ptorisco.setTipoDeRisco(TipoDeRisco.ESTRUTURAL.tipo);
        
        ender.setPontoDeRisco(ptorisco);
        ender.setCoordenada(coord);
        
        em.persist(ender);
        em.flush();
        assertNotNull(coord.getId());
        assertNotNull(ender.getId());
        assertNotNull(ptorisco.getId());
        logger.log(Level.INFO, "Cadastro realizado com sucesso!", ender);
        
    }

    // Atualização em:
    //<TB_ENDERECO id_coordenada = "2" rua = "Rua Armando Silva" numero = "95" bairro = "Afogados" cidade = "Recife"/>
    @Test
    public void t02_atualizaEndereco() {

//        logger.info("Executando: Atualização de dados");
//        Coordenada coord;
//        String consulta = "SELECT c FROM Coordenada c WHERE c.PONTO_Y=?1";
//        Query query = em.createQuery(consulta);
//        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
//        query.setParameter(1, -34.9004512);
//        coord = (Coordenada) query.getSingleResult();
//        coord.setPontoY(-34.9004400);
//        em.flush();
//        query.setParameter(1, -34.9004400);
//        coord = (Coordenada) query.getSingleResult();
//        assertEquals(-34.9004400, coord.getPontoY());
        
        
    }

    // Modificação em:
    // <TB_ENDERECO id_coordenada = "4" rua = "Rua da Ladeira" numero = "125" bairro = "Ibura" cidade = "Recife"/>
    @Test
    public void t03_atualizaComMerge() {

//        logger.info("Executando: Atualização de dados com merge");
//        Coordenada coord;
//        String consulta = "SELECT c FROM Coordenada c WHERE c.PONTO_Y=?1";
//        Query query = em.createQuery(consulta);
//        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
//        query.setParameter(1, -34.9388954);
//        coord = (Coordenada) query.getSingleResult();
//        assertNotNull(coord);
//        em.clear();
//        coord.setPontoY(-34.9388900);
//        em.merge(coord);
//        em.flush();
//        query.setParameter(1, -34.9388900);
//        coord = (Coordenada) query.getSingleResult();
//        assertEquals(-34.9388900, coord.getPontoY());
        //assertThat(-34.9388900, coord.getPontoY());
    }
    
    // Remoção de:
    // <TB_ENDERECO id_coordenada = "5" rua = "Rua Córrego do Joaquim" numero = "20" bairro = "Nova Descoberta" cidade = "Recife"/>
    @Test
    public void t04_removeEndereco() {
        
//        logger.info("Executando: Remove dados");
//        Endereco ender;
//        String consulta = "SELECT e FROM Endereco e WHERE e.id=?1";
//        Query query = em.createQuery(consulta);
//        long id = 5;
//        query.setParameter(1, id);
//        ender = (Endereco) query.getSingleResult();
//        em.remove(ender);
//        em.flush();
//        Map map = new HashMap();
//        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
//        Endereco apagado = em.find(Endereco.class, id, map);
//        assertNull(apagado);
        
        
    }
}
