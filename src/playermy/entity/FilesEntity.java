package entity;

import javax.persistence.*;

@Entity
@Table(name = "files", schema = "", catalog = "player")
public class FilesEntity {
    private int idFiles;
    private String title;
    private String path;

    @Id
    @Column(name = "idFiles", nullable = false, insertable = true, updatable = true)
    public int getIdFiles() {
        return idFiles;
    }

    public void setIdFiles(int idFiles) {
        this.idFiles = idFiles;
    }

    @Basic
    @Column(name = "TITLE", nullable = true, insertable = true, updatable = true, length = 75)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "PATH", nullable = true, insertable = true, updatable = true, length = 200)
    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilesEntity that = (FilesEntity) o;

        if (idFiles != that.idFiles) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFiles;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }    private int idfiles;

    @Id
    @Column(name = "IDFILES", nullable = false, insertable = true, updatable = true)
    public int getIdfiles() {
        return idfiles;
    }

    public void setIdfiles(int idfiles) {
        this.idfiles = idfiles;
    }
}
