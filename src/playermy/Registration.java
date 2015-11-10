import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

///**
// * Created by Adminh  */
public class Registration  extends JDialog {

    private MainWindow mainWindow;
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JOptionPane jOptionPane1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private Connection conn ;
    private Connect connect;

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JButton getjButton2() {
        return jButton2;
    }

    public void setjButton2(JButton jButton2) {
        this.jButton2 = jButton2;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JTextField getjTextField1() {
        return jTextField1;
    }

    public void setjTextField1(JTextField jTextField1) {
        this.jTextField1 = jTextField1;
    }

    public JTextField getjTextField2() {
        return jTextField2;
    }

    public void setjTextField2(JTextField jTextField2) {
        this.jTextField2 = jTextField2;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JOptionPane getjOptionPane1() {
        return jOptionPane1;
    }

    public void setjOptionPane1(JOptionPane jOptionPane1) {
        this.jOptionPane1 = jOptionPane1;
    }

    public Registration(JDialog parent, boolean modal ) {
        super(parent, modal);

        initComponents();
    }

    private void initComponents() {
        ButtonHandler handler=new ButtonHandler();
        connect=new Connect();
        jOptionPane1 = new JOptionPane();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTextField1 = new JTextField();
        jLabel2 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jTextField2 = new JTextField();
        jLabel3 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();


        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try{
                    if(conn!=null)
                        conn.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                closeDialog(evt);
            }
        });

        jLabel1.setFont(new Font("Cambria", 3, 12)); // NOI18N
        jLabel1.setText("Имя");

        jScrollPane1.setViewportView(jTextField1);

        jLabel2.setFont(new Font("Cambria", 3, 12)); // NOI18N
        jLabel2.setText("Пароль");

        jScrollPane2.setViewportView(jTextField2);

        jLabel3.setFont(new Font("Century", 1, 14)); // NOI18N
        jLabel3.setText("Введите Ваши данные");

        jButton1.setText("ОК");
        jButton1.addActionListener(handler);

        jButton2.setText("Отмена");
        jButton2.addActionListener(handler);

        setBackground(new Color(74, 175, 252));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(jLabel3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(jButton1)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton2)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1() {
        String log=getjTextField1().getText();
        String pas=getjTextField2().getText();
        ResultSet rs = null;
        Statement st = null;
        conn=connect.getConnect();
        try {
            st =conn.createStatement();
            String query = "SELECT iduser from Users where Users.Login= '"+log+"';";
            rs = st.executeQuery(query);
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Пользователь с даным именем" +
                        " уже зарегистрирован!","Ошибка",JOptionPane.ERROR_MESSAGE);
                st.close();
            }
            else {
                st.close();
                st = conn.createStatement();
                String query2 = "INSERT into Users (login, pass) VALUES ( '" + log + "','" + pas + "')";
                st.executeUpdate(query2);

                st.close();
                JOptionPane.showMessageDialog(null, "Вы зарегистрировались");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        doClose(RET_OK);
    }

    private void jButton2() {
        doClose(RET_CANCEL);
    }

    private void closeDialog(WindowEvent evt) {
        doClose(RET_CANCEL);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton1)
                jButton1();
            else if (e.getSource() == jButton2)
                jButton2();
        }
    }
}
