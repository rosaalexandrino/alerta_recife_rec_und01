package ifpe.recife.tads.alerta_recife;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "TB_PONTO_RISCO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "PontoDeRisco.RecuperarPorTipo",
                    query = "SELECT p FROM PontoDeRisco p WHERE p.tipoDeRisco = :tipo"
            )
            ,
            @NamedQuery(
                    name = "PontoDeRisco.RecuperarPorEndereco",
                    query = "SELECT p FROM PontoDeRisco p WHERE p.endereco = :endereco"
            )
        }
)
@NamedNativeQueries(
        {            
            @NamedNativeQuery(
                    name = "PontoDeRisco.RecuperarPorTipoSQL",
                    query = "SELECT * FROM TB_PONTO_RISCO WHERE TIPO_RISCO = ?1",
                    resultClass = PontoDeRisco.class
            )
            ,
            @NamedNativeQuery(
                    name = "PontoDeRisco.RecuperarPorEnderecoSQL",
                    query = "SELECT * FROM TB_PONTO_RISCO WHERE ID_ENDERECO = ?1",
                    resultClass = PontoDeRisco.class
            )
            ,
            @NamedNativeQuery(
                    name = "PontoDeRisco.DeletaPorIdSQL",
                    query = "DELETE FROM TB_PONTO_RISCO WHERE ID = ?1",
                    resultClass = PontoDeRisco.class
            )
            ,
            @NamedNativeQuery(
                    name = "PontoDeRisco.AtualizaTipoSQL",
                    query = "UPDATE TB_PONTO_RISCO SET TIPO_RISCO = ?1 WHERE ID = ?2",
                    resultClass = PontoDeRisco.class
            )
        }
)

@Access(AccessType.FIELD)
public class PontoDeRisco implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.PontoDeRisco.endereco_required}")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID")
    private Endereco endereco;
    
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.PontoDeRisco.tipo_required}")
    @Column(name = "TIPO_RISCO")
    private int tipoDeRisco;

    public PontoDeRisco() {
        
    }

    public PontoDeRisco(Long id, Endereco endereco, int tipoDeRisco) {
        this.id = id;
        this.endereco = endereco;
        this.tipoDeRisco = tipoDeRisco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getTipoDeRisco() {
        return tipoDeRisco;
    }

    public void setTipoDeRisco(int tipoDeRisco) {
        this.tipoDeRisco = tipoDeRisco;
    }
    
}
