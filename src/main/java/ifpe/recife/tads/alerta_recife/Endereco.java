package ifpe.recife.tads.alerta_recife;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "TB_ENDERECO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Endereco.RecuperarPorRua",
                    query = "SELECT e FROM Endereco e WHERE e.rua = :rua"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorNumero",
                    query = "SELECT e FROM Endereco e WHERE e.numero = :numero"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorBairro",
                    query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorBairroAprox",
                    query = "SELECT e FROM Endereco e WHERE e.bairro LIKE :bairro ORDER BY e.bairro"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorRuaOrdenando",
                    query = "SELECT e FROM Endereco e WHERE e.rua = :rua ORDER BY e.rua ASC"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Endereco.RecuperarEnderecosPorBairroSQL",
                    query = "SELECT ID, BAIRRO, CIDADE, NUMERO, RUA, ID_COORDENADA FROM TB_ENDERECO WHERE BAIRRO = ?1",
                    resultClass = Endereco.class
            )
            ,
            @NamedNativeQuery(
                    name = "Endereco.DeletaEnderecoSQL",
                    query = "DELETE FROM TB_ENDERECO WHERE ID = ?1",
                    resultClass = Endereco.class
            ),
            @NamedNativeQuery(
                    name = "Endereco.AtualizaCidadeSQL",
                    query = "UPDATE TB_ENDERECO SET CIDADE = ?1 WHERE ID = ?2",
                    resultClass = Endereco.class
            ),
            @NamedNativeQuery(
                    name = "Endereco.AtualizaBairroSQL",
                    query = "UPDATE TB_ENDERECO SET BAIRRO = ?1 WHERE ID = ?2",
                    resultClass = Endereco.class
            ),
            @NamedNativeQuery(
                    name = "Endereco.AtualizaRuaSQL",
                    query = "UPDATE TB_ENDERECO SET RUA = ?1 WHERE ID = ?2",
                    resultClass = Endereco.class
            ),
            @NamedNativeQuery(
                    name = "Endereco.AtualizaNumeroSQL",
                    query = "UPDATE TB_ENDERECO SET NUMERO = ?1 WHERE ID = ?2",
                    resultClass = Endereco.class
            )
        }
)
@Access(AccessType.FIELD)
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.coordenada_required}")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_COORDENADA", referencedColumnName = "ID")
    private Coordenada coordenada;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.ptorisco_required}")
    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
    private PontoDeRisco pontoDeRisco;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.rua_required}")
    @Pattern(regexp = "^[0-9A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Endereco.rua_caracter}")
    @Column(name = "RUA", length = 100)
    private String rua;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.numero_required}")
    @Pattern(regexp = "^[0-9A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Endereco.numero_caracter}")
    @Column(name = "NUMERO", length = 10)
    private String numero;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.bairro_required}")
    @Pattern(regexp = "^[0-9A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Endereco.bairro_caracter}")
    @Column(name = "BAIRRO", length = 100)
    private String bairro;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Endereco.cidade_required}")
    @Pattern(regexp = "^[A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Endereco.cidade_caracter}")
    @Column(name = "CIDADE", length = 100)
    private String cidade;

    @OneToMany(mappedBy = "endereco",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitacao> solicitacao;

    public Endereco() {

    }

    public Endereco(Long id, Coordenada coordenada, PontoDeRisco pontoDeRisco, String rua, String numero, String bairro, String cidade, List<Solicitacao> solicitacao) {
        this.id = id;
        this.coordenada = coordenada;
        this.pontoDeRisco = pontoDeRisco;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.solicitacao = solicitacao;
    }

    public PontoDeRisco getPontoDeRisco() {
        return pontoDeRisco;
    }

    public void setPontoDeRisco(PontoDeRisco pontoDeRisco) {
        this.pontoDeRisco = pontoDeRisco;
        this.pontoDeRisco.setEndereco(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
        this.coordenada.setEndereco(this);
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Solicitacao> getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(List<Solicitacao> solicitacao) {
        this.solicitacao = solicitacao;
    }

}
