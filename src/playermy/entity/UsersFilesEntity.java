package entity;

import javax.persistence.*;


@Entity
@javax.persistence.Table(name = "users_files", schema = "", catalog = "player")
public class UsersFilesEntity {
    private int idusersFiles;

    @javax.persistence.Id
    @javax.persistence.Column(name = "idusers_files", nullable = false, insertable = true, updatable = true)
    public int getIdusersFiles() {
        return idusersFiles;
    }

    public void setIdusersFiles(int idusersFiles) {
        this.idusersFiles = idusersFiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersFilesEntity that = (UsersFilesEntity) o;

        if (idusersFiles != that.idusersFiles) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idusersFiles;
    }

    private FilesEntity filesByIdFilesFromFiles;

    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name = "idFiles_from_files", referencedColumnName = "IDFILES")
    public FilesEntity getFilesByIdFilesFromFiles() {
        return filesByIdFilesFromFiles;
    }

    public void setFilesByIdFilesFromFiles(FilesEntity filesByIdFilesFromFiles) {
        this.filesByIdFilesFromFiles = filesByIdFilesFromFiles;
    }

    private UsersEntity usersByIdUsersFromUsers;

    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name = "idUsers_from_users", referencedColumnName = "IDUSER")
    public UsersEntity getUsersByIdUsersFromUsers() {
        return usersByIdUsersFromUsers;
    }

    public void setUsersByIdUsersFromUsers(UsersEntity usersByIdUsersFromUsers) {
        this.usersByIdUsersFromUsers = usersByIdUsersFromUsers;
    }
}
