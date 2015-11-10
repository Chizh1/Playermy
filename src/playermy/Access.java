import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by Adminh
 */
public class Access extends JDialog {
    private final static String LOG_TAG = "myLogs";
    private final static Logger log=Logger.getLogger(LOG_TAG);
    private JList selectusers;
    private Connect connect;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection connectDB;
    private DBListModel dbListModel;
    int index;

    public Access(Frame parent, boolean modal, int selectrowindex) {
        super(parent,modal);
        index=selectrowindex;
        initComponent();
    }
    private void initComponent(){

        connect = new Connect();
        connectDB = connect.getConnect() ;
        dbListModel = new DBListModel();
        JPanel contentPane = new JPanel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JButton buttonCancel = new JButton();
        JButton buttonOK = new JButton();
        selectusers=new JList();
        JScrollPane jscrollpane = new JScrollPane();
        JPanel jPanel2= new JPanel();
        JPanel jPanel3= new JPanel();


        getContentPane().setPreferredSize(new Dimension(300, 200));

        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);

        selectusers.setModel(dbListModel);
        selectusers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectusers.setSelectionBackground(Color.green);
        selectusers.setListData(Selectusers());
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        label1.setFont(new java.awt.Font("Verdana", 0, 12));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label1.setText("Укажите пользователей");

        label2.setFont(new java.awt.Font("Verdana", 0, 12));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText("предоставляющим доступ");


        jscrollpane.setViewportView(selectusers);

        buttonOK.setText("Oк");

        buttonCancel.setText("Cencel");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(190, Short.MAX_VALUE)
                                .addComponent(buttonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCancel)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonOK)
                                        .addComponent(buttonCancel))
                                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addComponent(jscrollpane, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jscrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        add(contentPane);

    }

    private void Savenewaccess() {
        rs=null; pst=null;
        connectDB=connect.getConnect();
        Object value = selectusers.getSelectedValue();
        String tmp=value.toString();
        try{
            pst=connectDB.prepareStatement("SELECT iduser from USERS where LOGIN ='"+tmp+"';") ;
            rs=pst.executeQuery();
            dbListModel.setDataSource(rs,"iduser");
            Object v= dbListModel.getElementAt(0);
            int iduser=Integer.parseInt(v.toString()) ;
            pst.close();
            pst=connectDB.prepareStatement("SELECT idusers_files from USERS_FILES where IDUSERS_FROM_USERS ="+iduser+" and IDFILES_FROM_FILES= "+index+";") ;
            rs=pst.executeQuery();
            dbListModel.setDataSource(rs,"idusers_files");
            pst.close();
            if (dbListModel.getSize()==0){
                pst=connectDB.prepareStatement("Insert into USERS_FILES(idUsers_from_users, idFiles_from_files)values ("+iduser+", "+index+" );") ;
                pst.executeUpdate();
                pst.close();
                //JOptionPane.showMessageDialog(null,"Доступ предоставлен", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Вам уже был предоставлен доступ к даному файлу", "Доступ", JOptionPane.INFORMATION_MESSAGE);

        }
        catch (SQLException e1) {
            log.log(Level.WARNING, e1.toString(), e1);
            e1.printStackTrace();

        }
    }

    private Object[] Selectusers() {
        pst = null; rs=null;
        try{
            pst=connectDB.prepareStatement("SELECT USERS.LOGIN from USERS ;");
            rs=pst.executeQuery();
        } catch (SQLException e) {
            log.log(Level.WARNING, e.toString(), e);
            e.printStackTrace();
        }

        dbListModel.setDataSource(rs,"Login");
        Object datalist[]= new Object[dbListModel.getSize()];
        for (int i=0; i<dbListModel.getSize();i++){
            datalist[i]=dbListModel.getElementAt(i);

        }
        return datalist;
    }

    private void onOK() {
        Savenewaccess();
        dispose();
    }

    private void onCancel() {
        JOptionPane.showMessageDialog(null, "Права доступа не предоставлены", "Отмена", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }


}


