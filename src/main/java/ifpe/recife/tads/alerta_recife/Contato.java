package ifpe.recife.tads.alerta_recife;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TB_CONTATO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Contato.RecuperarPorDescricao",
                    query = "SELECT c FROM Contato c WHERE c.descricao LIKE :descricao"
            )
            ,
            @NamedQuery(
                    name = "Contato.RecuperarTodosContatos",
                    query = "SELECT c FROM Contato c ORDER BY c.descricao"
            )
            ,
            @NamedQuery(
                    name = "Contato.RecuperarPorNumero",
                    query = "SELECT c FROM Contato c WHERE c.numero = :numero"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Contato.RecuperarPorDescricaoSQL",
                    query = "SELECT ID, DESCRICAO, NUMERO FROM TB_CONTATO WHERE DESCRICAO LIKE ?1 ORDER BY DESCRICAO",
                    resultClass = Contato.class
            )
            ,
            @NamedNativeQuery(
                    name = "Contato.RecuperarTodosContatosSQL",
                    query = "SELECT ID, DESCRICAO, NUMERO FROM TB_CONTATO ORDER BY DESCRICAO",
                    resultClass = Contato.class
            )
            ,
            @NamedNativeQuery(
                    name = "Contato.RecuperarPorNumeroSQL",
                    query = "SELECT ID, DESCRICAO, NUMERO FROM TB_CONTATO WHERE NUMERO = ?1 ORDER BY DESCRICAO",
                    resultClass = Contato.class
            )
            ,
            @NamedNativeQuery(
                    name = "Contato.RemoveContatoPorIdSQL",
                    query = "DELETE FROM TB_CONTATO WHERE ID = ?1",
                    resultClass = Contato.class
            ),
            @NamedNativeQuery(
                    name = "Contato.AtualizaDescricaoContatoSQL",
                    query = "UPDATE TB_CONTATO SET DESCRICAO = ?1 WHERE ID = ?2",
                    resultClass = Contato.class
            )
        }
)
@Access(AccessType.FIELD)
public class Contato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Contato.descricao_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Contato.descricao_required}")
    @Size(min = 1, max = 220,
            message = "{ifpe.recife.tads.alerta_recife.Contato.descricao_tamanho}")
    @Pattern(regexp = "^[A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Contato.descricao_caracter}")
    @Column(name = "DESCRICAO", length = 100)
    private String descricao;
    
    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Contato.numero_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Contato.numero_required}")
    @Size(min = 3, max = 20,
            message = "{ifpe.recife.tads.alerta_recife.Contato.numero_tamanho}")
    @Pattern(regexp = "^[0-9\\-]+$",
            message = "{ifpe.recife.tads.alerta_recife.Contato.numero_caracter}")
    @Column(name = "NUMERO", length = 20)
    private String numero;

    public Contato() {

    }

    public Contato(Long id, String descricao, String numero) {
        this.id = id;
        this.descricao = descricao;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
