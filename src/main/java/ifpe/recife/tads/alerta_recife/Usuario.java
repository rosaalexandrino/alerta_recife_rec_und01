package ifpe.recife.tads.alerta_recife;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TB_USUARIO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Usuario.RecuperarPorEmail",
                    query = "SELECT u FROM Usuario u WHERE u.email = :email"
            )
            ,
            @NamedQuery(
                    name = "Usuario.RecuperarAtivos",
                    query = "SELECT u FROM Usuario u WHERE u.habilitado = :habilitado ORDER BY u.primeiroNome"
            )
            ,
            @NamedQuery(
                    name = "Usuario.RecuperarPorNome",
                    query = "SELECT u FROM Usuario u WHERE u.primeiroNome LIKE :nome OR u.ultimoNome LIKE :sobrenome ORDER BY u.primeiroNome"
            )
            ,
            @NamedQuery(
                    name = "Usuario.RecuperarTelefones",
                    query = "SELECT u.primeiroNome, u.ultimoNome, ut.numero FROM Usuario u JOIN u.telefones ut WHERE u.id = :id"
            )
            ,
            @NamedQuery(
                    name = "Usuario.RecuperarSolicitacoes",
                    query = "SELECT u.primeiroNome, u.ultimoNome, s.descricao FROM Usuario u JOIN u.solicitacao s WHERE u.id = :id"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Usuario.RecuperarPorEmail",
                    query = "SELECT * FROM TB_USUARIO WHERE EMAIL = ?1",
                    resultClass = Usuario.class
            )
            ,
            @NamedNativeQuery(
                    name = "Usuario.RecuperarAtivos",
                    query = "SELECT * FROM TB_USUARIO WHERE HABILITADO = ?1 ORDER BY PRIMEIRO_NOME",
                    resultClass = Usuario.class
            )
            ,
            @NamedNativeQuery(
                    name = "Usuario.RecuperarPorNome",
                    query = "SELECT * FROM TB_USUARIO WHERE PRIMEIRO_NOME LIKE ?1 OR ULTIMO_NOME LIKE ?2 ORDER BY u.PRIMEIRO_NOME",
                    resultClass = Usuario.class
            )
            ,
            @NamedNativeQuery(
                    name = "Usuario.RecuperarTelefones",
                    query = "SELECT u.PRIMEIRO_NOME, u.ULTIMO_NOME, t.NUMERO FROM TB_USUARIO AS u JOIN TB_USUARIO_TELEFONE AS ut ON u.ID = ut.ID_USUARIO JOIN TB_TELEFONE AS t ON t.ID = ut.ID_TELEFONE WHERE u.ID = ?1",
                    resultClass = Usuario.class
            )
            ,
            @NamedNativeQuery(
                    name = "Usuario.RecuperarSolicitacoes",
                    query = "SELECT u.PRIMEIRO_NOME, u.ULTIMO_NOME, s.DESCRICAO FROM TB_USUARIO AS u JOIN TB_SOLICITACAO AS s ON u.ID = s.ID_USUARIO WHERE u.ID = ?1",
                    resultClass = Usuario.class
            )
            ,
            @NamedNativeQuery(
                    name = "Usuario.DeletaPorIdSQL",
                    query = "DELETE FROM TB_USUARIO WHERE ID = ?1",
                    resultClass = Usuario.class
            ),
            @NamedNativeQuery(
                    name = "Usuario.AtualizaSenhaUsuarioSQL",
                    query = "UPDATE TB_USUARIO SET SENHA = ?1 WHERE ID = ?2",
                    resultClass = Usuario.class
            )
        }
)

@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Usuario.email_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Usuario.email_required}")
    @Size(max = 100,
            message = "{ifpe.recife.tads.alerta_recife.Usuario.email_tamanho}")
    @Email(message = "{ifpe.recife.tads.alerta_recife.Usuario.email_caracter}")
    @Column(name = "EMAIL", unique = true, length = 100)
    private String email;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Usuario.senha_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Usuario.senha_required}")
    @Pattern(regexp = "^[0-9A-Za-z@!?]+$",
            message = "{ifpe.recife.tads.alerta_recife.Usuario.senha_caracter}")
    @Size(min = 5, max = 15,
            message = "{ifpe.recife.tads.alerta_recife.Usuario.senha_tamanho}")
    @Column(name = "SENHA", length = 15)
    private String senha;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Usuario.nomerequired}")
    @Pattern(regexp = "^[A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_caracter}")
    @Size(max = 50,
            message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_tamanho}")
    @Column(name = "PRIMEIRO_NOME", length = 50)
    private String primeiroNome;

    @NotBlank(message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_required}")
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Usuario.nomerequired}")
    @Pattern(regexp = "^[A-Za-z\\s]+$",
            message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_caracter}")
    @Size(max = 50,
            message = "{ifpe.recife.tads.alerta_recife.Usuario.nome_tamanho}")
    @Column(name = "ULTIMO_NOME", length = 50)
    private String ultimoNome;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_USUARIO_TELEFONE", joinColumns
            = {
                @JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns
            = {
                @JoinColumn(name = "ID_TELEFONE")})
    private List<Telefone> telefones;

    @NotNull
    @Column(name = "HABILITADO")
    private boolean habilitado;

    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitacao> solicitacao;

    public Usuario() {
        this.telefones = new ArrayList<>();
        this.solicitacao = new ArrayList<>();
    }

    public Usuario(Long id, String email, String senha, String primeiroNome, String ultimoNome, boolean habilitado, List<Telefone> telefones, List<Solicitacao> solicitacoes) {
        if (telefones.size() > 0) {
            this.telefones = telefones;
        } else {
            this.telefones = new ArrayList<>();
        }
        if (solicitacoes.size() > 0) {
            this.solicitacao = solicitacoes;
        } else {
            this.solicitacao = new ArrayList<>();
        }
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.habilitado = habilitado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public List<Solicitacao> getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(List<Solicitacao> solicitacao) {
        this.solicitacao = solicitacao;
    }

}
