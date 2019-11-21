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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "TB_COORDENADA")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Coordenada.RecuperarPorPontoX",
                    query = "SELECT c FROM Coordenada c WHERE c.pontoX = :ponto"
            )
            ,
            @NamedQuery(
                    name = "Coordenada.RecuperarPorPontoY",
                    query = "SELECT c FROM Coordenada c WHERE c.pontoY = :ponto"
            )
        }
)
@NamedNativeQueries(
        {            
            @NamedNativeQuery(
                    name = "Coordenada.RecuperarPorPontoXSQL",
                    query = "SELECT ID, PONTO_X, PONTO_Y FROM TB_COORDENADA WHERE PONTO_X = ?1",
                    resultClass = Coordenada.class
            )
            ,
            @NamedNativeQuery(
                    name = "Coordenada.RecuperarPorPontoYSQL",
                    query = "SELECT ID, PONTO_X, PONTO_Y FROM TB_COORDENADA WHERE PONTO_Y = ?1",
                    resultClass = Coordenada.class
            )
            ,
            @NamedNativeQuery(
                    name = "Coordenada.DeletaPorIdSQL",
                    query = "DELETE FROM TB_COORDENADA WHERE ID = ?1",
                    resultClass = Coordenada.class
            )
            ,
            @NamedNativeQuery(
                    name = "Coordenada.AtualizaCoordenadaXSQL",
                    query = "UPDATE TB_COORDENADA SET PONTO_X = ?1 WHERE ID = ?2",
                    resultClass = Coordenada.class
            )
        }
)

@Access(AccessType.FIELD)
public class Coordenada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Coordenada.endereco_required}")
    @OneToOne(mappedBy = "coordenada", cascade = CascadeType.ALL)
    private Endereco endereco;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Coordenada.pontoX_required}")
    @Column(name = "PONTO_X")
    double pontoX;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Coordenada.pontoY_required}")
    @Column(name = "PONTO_Y")
    double pontoY; 

    public Coordenada() {

    }

    public Coordenada(Long id, Endereco endereco, double pontoX, double pontoY) {
        this.id = id;
        this.endereco = endereco;
        this.pontoX = pontoX;
        this.pontoY = pontoY;
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
    
    public double getPontoX() {
        return pontoX;
    }

    public void setPontoX(double pontoX) {
        this.pontoX = pontoX;
    }

    public double getPontoY() {
        return pontoY;
    }

    public void setPontoY(double pontoY) {
        this.pontoY = pontoY;
    }

}
