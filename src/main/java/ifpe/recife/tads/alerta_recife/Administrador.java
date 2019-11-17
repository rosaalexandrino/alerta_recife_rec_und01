package ifpe.recife.tads.alerta_recife;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name="TB_ADMINISTRADOR")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Administrador.RecuperarPorMatricula",
                    query = "SELECT a FROM Administrador a WHERE a.matricula = :matricula"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorCargo",
                    query = "SELECT a FROM Administrador a WHERE a.cargo = :cargo ORDER BY a.primeiroNome"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarAdministradores",
                    query = "SELECT a FROM Administrador a"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarAdministradoresHabilitados",
                    query = "SELECT a FROM Administrador a WHERE a.habilitado = :habilitado"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Administrador.RecuperarPorMatriculaSQL",
                    query = "SELECT a.ID_ADMIN, a.CARGO, a.MATRICULA, u.ID, u.EMAIL, u.HABILITADO, u.SENHA, u.PRIMEIRO_NOME,"
                    +"u.ULTIMO_NOME FROM TB_ADMINISTRADOR AS a JOIN TB_USUARIO AS u ON a.ID_ADMIN = u.ID WHERE a.MATRICULA = ?",
                    resultClass = Administrador.class
            )
            ,
            @NamedNativeQuery(
                    name = "Administrador.RecuperarPorCargoSQL",
                    query = "SELECT a.ID_ADMIN, a.CARGO, a.MATRICULA, u.ID, u.EMAIL, u.HABILITADO, u.SENHA, u.PRIMEIRO_NOME,"
                    +"u.ULTIMO_NOME FROM TB_ADMINISTRADOR AS a JOIN TB_USUARIO AS u ON a.ID_ADMIN = u.ID WHERE a.CARGO = ?1 ORDER BY u.PRIMEIRO_NOME",
                    resultClass = Administrador.class
            ),
            @NamedNativeQuery(
                    name = "Administrador.RecuperarAdministradoresHabilitadosSQL",
                    query = "SELECT a.ID_ADMIN, a.CARGO, a.MATRICULA, u.ID, u.EMAIL, u.HABILITADO, u.SENHA, u.PRIMEIRO_NOME,"
                    +"u.ULTIMO_NOME FROM TB_ADMINISTRADOR AS a JOIN TB_USUARIO AS u ON a.ID_ADMIN = u.ID WHERE u.HABILITADO = ?1",
                    resultClass = Administrador.class
            ),
            @NamedNativeQuery(
                    name = "Administrador.DeletaPorIdSQL",
                    query = "DELETE FROM TB_ADMINISTRADOR WHERE ID_ADMIN = ?1",
                    resultClass = Administrador.class
            ),
            @NamedNativeQuery(
                    name = "Administrador.AtualizaCargoAdministradorSQL",
                    query = "UPDATE TB_ADMINISTRADOR SET CARGO = ?1 WHERE ID_ADMIN = ?2",
                    resultClass = Administrador.class
            )
        }
)
@Access(AccessType.FIELD)
@DiscriminatorValue(value = "ADMIN")
@PrimaryKeyJoinColumn(name = "ID_ADMIN", referencedColumnName = "ID")
public class Administrador extends Usuario implements Serializable {
	
    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Administrador.matricula_required}")
    @Size(min = 8, max = 10, 
            message = "{ifpe.recife.tads.alerta_recife.Administrador.matricula_tamanho}")
    @Pattern(regexp = "^[0-9]+$",
            message = "{ifpe.recife.tads.alerta_recife.Administrador.matricula_caracter}")
    @Column(name="MATRICULA", unique=true, length = 10)
    private String matricula;

    @NotNull(message = "{ifpe.recife.tads.alerta_recife.Administrador.cargo_required}")
    @Column(name="CARGO")
    private int cargo; 

    public Administrador(){
        
    }
    
    public Administrador(String matricula, int cargo) {
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int numCargo) {
        this.cargo = numCargo;
    }

}