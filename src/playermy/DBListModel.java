import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DBListModel extends AbstractListModel {

    private ArrayList data = new ArrayList();

    public void setDataSource(ResultSet rs, String column) {
        try {
// получаем данные
            data.clear();
            while ( rs.next() ) {
                synchronized ( data ) {
                    data.add(rs.getString(column));
                }
// оповещаем виды (если они есть)
                fireIntervalAdded(this, 0, data.size());
            }
            rs.close();
            fireContentsChanged(this, 0, data.size());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getSize() {
        synchronized ( data ) {
            return data.size();
        }
    }

    public Object getElementAt(int idx) {
        synchronized ( data ) {
            return data.get(idx);
        }
    }
}
