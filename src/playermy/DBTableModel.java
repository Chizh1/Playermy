import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class DBTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private List<String> columnNames = new ArrayList<String>();
        private List<List> data = new ArrayList<List>();

        public int getRowCount() {
            return data.size();
        }

        public int getColumnCount() {
            return columnNames.size();
        }

        public Object getValueAt(int row, int col) {
            return data.get(row).get(col);
        }

        public String getColumnName(int col) {
            return columnNames.get(col);
        }

        public boolean isEditable() {
            return true;
        }

        public void setValueAt(Object obj, int row, int col) {
            data.get(row).set(col, obj);
        }

        public void setResultSet(ResultSet rs) throws SQLException {
            columnNames.clear();
            data.clear();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                List rowData = new ArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getObject(i));
                }
                data.add(rowData);
                this.fireTableStructureChanged();
                this.fireTableDataChanged();
            }
        }
    }