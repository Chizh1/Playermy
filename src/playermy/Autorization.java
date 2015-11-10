import entity.UsersEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Autorization extends JDialog {

        private UsersEntity Users;
        private Registration reg;
        private static final int RET_CANCEL = 0;
        public static final int RET_OK = 1;
        private int returnStatus = RET_CANCEL;
        private JButton cancelButton;
        private JButton registrButton;
        private JLabel namewindowLabel;
        private JLabel loginLabel;
        private JLabel passwordLabel;
        private JPasswordField passwordField;
        private JTextField loginField;
        private JButton okButton;
        private Connection conn ;
    private Connect connect;

    public JButton getOkButton() {
            return okButton;
        }

        public void setOkButton(JButton okButton) {
            this.okButton = okButton;
        }

        public JButton getRegistrButton() {
            return registrButton;
        }

        public void setRegistrButton(JButton registrButton) {
            this.registrButton = registrButton;
        }

        public JLabel getNamewindowLabel() {
            return namewindowLabel;
        }

        public void setNamewindowLabel(JLabel namewindowLabel) {
            this.namewindowLabel = namewindowLabel;
        }

        public JLabel getLoginLabel() {
            return loginLabel;
        }

        public void setLoginLabel(JLabel loginLabel) {
            this.loginLabel = loginLabel;
        }

        public JLabel getPasswordLabel() {
            return passwordLabel;
        }

        public void setPasswordLabel(JLabel passwordLabel) {
            this.passwordLabel = passwordLabel;
        }

        public JPasswordField getPasswordField() {
            return passwordField;
        }

        public void setPasswordField(JPasswordField passwordField) {
            this.passwordField = passwordField;
        }

        public JTextField getLoginField() {
            return loginField;
        }

        public void setLoginField(JTextField loginField) {
            this.loginField = loginField;
        }

        public JButton getCancelButton() {

            return cancelButton;
        }

        public void setCancelButton(JButton cancelButton) {
            this.cancelButton = cancelButton;
        }

        Autorization(Frame parent, String name, boolean modal) {
            super(parent,name, modal);
            initComponents();
            setVisible(true);

            // Close the dialog when Esc is pressed
            String cancelName = "cancel";
            InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
            ActionMap actionMap = getRootPane().getActionMap();
            actionMap.put(cancelName, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    doClose(RET_CANCEL);
                }
            });

        }

        public int getReturnStatus() {return returnStatus;}

        private void initComponents() {

            okButton = new JButton();
            cancelButton = new JButton();
            namewindowLabel = new JLabel();
            loginLabel = new JLabel();
            passwordLabel = new JLabel();
            passwordField = new JPasswordField();
            loginField = new JTextField();
            registrButton = new JButton();
            connect= new Connect();

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    closeDialog(evt);
                }
            });

            okButton.setText("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    okButtonActionPerformed(evt);

                }
            });

            cancelButton.setText("Отмена");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });

            namewindowLabel.setFont(new Font("Tahoma", 1, 14)); // NOI18N
            namewindowLabel.setText("Авторизация");

            loginLabel.setFont(new Font("Tahoma", 3, 12)); // NOI18N
            loginLabel.setText("Логин");

            passwordLabel.setFont(new Font("Tahoma", 3, 12)); // NOI18N
            passwordLabel.setText("Пароль");

            registrButton.setText("Регистрация");
            registrButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jButton1ActionPerformed(e);

                }
            });


            getContentPane().setBackground(new Color(205, 252, 212));
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(9, 9, 9)
                                                    .addComponent(registrButton)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(cancelButton))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(loginField, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
                                                    .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(namewindowLabel)
                                    .addGap(109, 109, 109))
            );

            layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {cancelButton, okButton});

            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(namewindowLabel)
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(loginLabel)
                                            .addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(passwordLabel)
                                            .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(cancelButton)
                                            .addComponent(okButton)
                                            .addComponent(registrButton))
                                    .addContainerGap())
            );

            getRootPane().setDefaultButton(okButton);

            pack();
        }

        private void okButtonActionPerformed(ActionEvent evt) {
            String log = getLoginField().getText();
            char[] p = passwordField.getPassword();
            String pas = new String(p);
            if (!(pas.isEmpty()||(log.isEmpty()))) {

                reg = new Registration(this, true);
                ResultSet rs = null;
                Statement st = null;
                conn = connect.getConnect();
                try {
                    st = conn.createStatement();
                    String query = "SELECT Users.IdUser from Users where Users.Login ='" + log + "' and Users.pass='" + pas + "';";
                    rs = st.executeQuery(query);
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "Вы не зарегистрированы");
                        setVisible(false);
                        reg.setVisible(true);
                        new Autorization((Frame) this.getParent(), "Авторизация", true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Вы авторизованы");
                    }
                }  catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ошибка соединения с БД", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } finally {
                    try {
                        if (st != null)
                            st.close();
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                doClose(RET_OK);
            }
            else {
                JOptionPane.showMessageDialog(null, "Заполнены не все поля!", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);

            }
        }

        private void cancelButtonActionPerformed(ActionEvent evt) {
            Object[] options = {"Да", "Нет"};
            int answer = JOptionPane.showOptionDialog(null,"Вы хотите завершить работу?","Выход",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options);
            if (answer==0) {
                doClose(RET_CANCEL);
                System.exit (0);
            }
        }

        private void jButton1ActionPerformed(ActionEvent e){
            setVisible(false);
            reg = new Registration(this, true);
            reg.setVisible(true);
            new Autorization((Frame) this.getParent(),"Авторизация",true);
        }

        private void closeDialog(WindowEvent evt) {
            doClose(RET_CANCEL);
            System.exit(0);
        }

        private void doClose(int retStatus) {
            returnStatus = retStatus;
            setVisible(false);
            dispose();
        }
    }






