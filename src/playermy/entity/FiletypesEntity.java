package entity;

import javax.persistence.*;


@Entity
@Table(name = "filetypes", schema = "", catalog = "player")
public class FiletypesEntity {
    private int idFiletypes;
    private String extensions;
    private String types;

    @Id
    @Column(name = "idFiletypes", nullable = false, insertable = true, updatable = true)
    public int getIdFiletypes() {
        return idFiletypes;
    }

    public void setIdFiletypes(int idFiletypes) {
        this.idFiletypes = idFiletypes;
    }

    @Basic
    @Column(name = "EXTENSIONS", nullable = true, insertable = true, updatable = true, length = 15)
    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    @Basic
    @Column(name = "TYPES", nullable = true, insertable = true, updatable = true, length = 15)
    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FiletypesEntity that = (FiletypesEntity) o;

        if (idFiletypes != that.idFiletypes) return false;
        if (extensions != null ? !extensions.equals(that.extensions) : that.extensions != null) return false;
        if (types != null ? !types.equals(that.types) : that.types != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFiletypes;
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        return result;
    }

    private int idfiletypes;

    @Id
    @Column(name = "IDFILETYPES", nullable = false, insertable = true, updatable = true)
    public int getIdfiletypes() {
        return idfiletypes;
    }

    public void setIdfiletypes(int idfiletypes) {
        this.idfiletypes = idfiletypes;
    }
}
