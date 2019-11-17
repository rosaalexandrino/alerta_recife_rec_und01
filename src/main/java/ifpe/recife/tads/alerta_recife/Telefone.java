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
import javax.persistence.ManyToMany;
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
@Table(name = "TB_TELEFONE")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Telefone.RecuperarPorDDD",
                    query = "SELECT t FROM Telefone t WHERE t.ddd = :ddd ORDER BY t.numero"
            )
            ,
            @NamedQuery(
                    name = "Telefone.RecuperarTodosTelefones",
                    query = "SELECT t FROM Telefone t ORDER BY t.ddd"
            )
            ,
            @NamedQuery(
                    name = "Telefone.RecuperarPorNumero",
                    query = "SELECT t FROM Telefone t WHERE t.numero = :numero"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Telefone.RecuperarPorDDDSQL",
                    query = "SELECT ID, DDD, NUMERO FROM TB_TELEFONE WHERE DDD = ?1 ORDER BY NUMERO",
                    resultClass = Telefone.class
            )
            ,
            @NamedNativeQuery(
                    name = "Telefone.RecuperarTodosTelefonesSQL",
                    query = "SELECT ID, DDD, NUMERO FROM TB_TELEFONE ORDER BY DDD",
                    resultClass = Telefone.class
            )
            ,
            @NamedNativeQuery(
                    name = "Telefone.RecuperarPorNumeroSQL",
                    query = "SELECT ID, DDD, NUMERO FROM TB_TELEFONE WHERE NUMERO = ?1",
                    resultClass = Telefone.class
            )
            ,
            @NamedNativeQuery(
                    name = "Telefone.RemoveTelefoneSQL",
                    query = "DELETE FROM TB_TELEFONE WHERE ID = ?1",
                    resultClass = Telefone.class
            ),
            @NamedNativeQuery(
                    name = "Telefone.AtualizaNumeroSQL",
                    query = "UPDATE TB_TELEFONE SET NUMERO = ?1 WHERE ID = ?2",
                    resultClass = Telefone.class
            )
        }
)

@Access(AccessType.FIELD)
public class Telefone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "telefones", cascade = CascadeType.MERGE)
    private List<Usuario> usuarios;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Telefone.numero_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Telefone.numero_required}")
    @Size(min = 8, max = 20,
            message = "{ifpe.recife.tads.alerta_recife.Telefone.numero_tamanho}")
    @Pattern(regexp = "^[0-9\\-]+$",
            message = "{ifpe.recife.tads.alerta_recife.Telefone.numero_caracter}")
    @Column(name = "NUMERO", length = 20)
    private String numero;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Telefone.ddd_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Telefone.ddd_required}")
    @Size(min = 3, max = 3,
            message = "{ifpe.recife.tads.alerta_recife.Telefone.ddd_tamanho}")
    @Pattern(regexp = "^[0-9]+$",
            message = "{ifpe.recife.tads.alerta_recife.Telefone.ddd_caracter}")
    @Column(name = "DDD", length = 3)
    private String ddd;

    public Telefone() {

    }

    public Telefone(Long id, List<Usuario> usuarios, String numero, String ddd) {
        this.id = id;
        this.usuarios = usuarios;
        this.numero = numero;
        this.ddd = ddd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

}
