package entity;

import javax.persistence.*;


@Entity
@Table(name = "users", schema = "", catalog = "player")
public class UsersEntity {
    private int idUser;
    private String login;
    private String pass;

    @Id
    @Column(name = "IdUser", nullable = false, insertable = true, updatable = true)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "LOGIN", nullable = true, insertable = true, updatable = true, length = 10)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "PASS", nullable = true, insertable = true, updatable = true, length = 8)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUser != that.idUser) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    private int iduser;

    @Id
    @Column(name = "IDUSER", nullable = false, insertable = true, updatable = true)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
}
