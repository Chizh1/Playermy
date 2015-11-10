package entity;

import javax.persistence.*;

@Entity
@Table(name = "settings", schema = "", catalog = "player")
public class SettingsEntity {
    private int idSettings;
    private String actions;

    @Id
    @Column(name = "idSettings", nullable = false, insertable = true, updatable = true)
    public int getIdSettings() {
        return idSettings;
    }

    public void setIdSettings(int idSettings) {
        this.idSettings = idSettings;
    }

    @Basic
    @Column(name = "ACTIONS", nullable = true, insertable = true, updatable = true, length = 17)
    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsEntity that = (SettingsEntity) o;

        if (idSettings != that.idSettings) return false;
        if (actions != null ? !actions.equals(that.actions) : that.actions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSettings;
        result = 31 * result + (actions != null ? actions.hashCode() : 0);
        return result;
    }

    private int idsettings;

    @Id
    @Column(name = "IDSETTINGS", nullable = false, insertable = true, updatable = true)
    public int getIdsettings() {
        return idsettings;
    }

    public void setIdsettings(int idsettings) {
        this.idsettings = idsettings;
    }
}
