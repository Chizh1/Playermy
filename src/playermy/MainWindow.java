import entity.UsersEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * @author Adminh
 */
public class MainWindow  extends JFrame {

    private final static String LOG_TAG = "myLogs";
    private final static Logger log=Logger.getLogger(LOG_TAG);

    private static Handler handler;
    private UsersEntity User;
    private JButton addButton;
    private JButton delButton;
    private JButton libraryButton;
    private JFileChooser jfc;
    private JTable visualtable;
    private DBTableModel dbm;
    private Connection connectDB;
    private PreparedStatement pst;
    private ResultSet rs;
    private JButton musicButton;
    private JButton videoButton;
    private JButton imageButton;
    private JLabel selectbuttonLabel;
    private JButton exitButton;
    private JLabel selectfileLabel;
    private File fileselect;
    private Video videowindow;
    private Connect connect;
    private Autorization autorizwindow;
    private JButton accessButton;

    public MainWindow() {
        super();
        initComponents();
    }

    private void initComponents() {
        try {
            handler = new FileHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.addHandler(handler);
        log.setLevel(Level.ALL);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setSize(800, 640);
        getContentPane().setBackground(new Color(252, 244, 224));
        setDefaultLookAndFeelDecorated(true);

        User = new UsersEntity();
        setTitle("Файлы пользователя");
        autorizwindow =new Autorization(this,"Авторизация",true);
        User.setLogin(autorizwindow.getLoginField().getText());
        char [] p = autorizwindow.getPasswordField().getPassword();
        String str=new String(p);
        User.setPass(str);
        dbm = new DBTableModel();
        libraryButton = new JButton();
        JLabel helloLabel = new JLabel();
        addButton = new JButton();
        jfc=new JFileChooser();
        musicButton = new JButton();
        videoButton = new JButton();
        imageButton = new JButton();
        JSeparator jSeparator1 = new JSeparator();
        JSeparator jSeparator2 = new JSeparator();
        delButton = new JButton();
        exitButton = new JButton();
        selectfileLabel =new JLabel();
        accessButton = new JButton() ;
        connect=new Connect();
        connectDB = connect.getConnect();
        ButtonHandler buttonHandler = new ButtonHandler();


        try {
            pst= connectDB.prepareStatement("SELECT iduser from Users where login= '"+User.getLogin()+"';");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
            String Idstr =  dbm.getValueAt(0,0).toString();
            int IdUser = Integer.parseInt (Idstr);
            User.setIdUser(IdUser);
            pst.close();
        } catch ( Exception e) {
            log.log(Level.WARNING, "problems with data output\n", e);
        }

        addButton.setText("Добавить");
        addButton.addActionListener(buttonHandler);

        libraryButton.setText("Библиотека");
        libraryButton.addActionListener(buttonHandler);

        rs= Visualdata ();
        try {
            dbm.setResultSet(rs);
        } catch (Exception e) {
            log.log(Level.WARNING, "problems with data output\n", e);
        }

        visualtable = new JTable(dbm);
        visualtable.setFont(new Font("Times New Roman", 0, 14));
        visualtable.setShowHorizontalLines(true);
        visualtable.setShowVerticalLines(true);
        visualtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        visualtable.setRowSelectionAllowed(true);
        visualtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        visualtable.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    SelectPlay(visualtable.getSelectedRow());
                }
            }
        }));
        visualtable.setBackground(Color.white);

        JScrollPane jScrollPane1 = new JScrollPane(visualtable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportBorder(BorderFactory.createLineBorder(Color.blue));
        jScrollPane1.setWheelScrollingEnabled(true);
        jScrollPane1.setViewportView(visualtable);

        helloLabel.setText("Привет, " + User.getLogin());

        musicButton.setText("Музыка");
        musicButton.addActionListener(buttonHandler);

        videoButton.setText("Видео");
        videoButton.addActionListener(buttonHandler);

        imageButton.setText("Фото");
        imageButton.addActionListener(buttonHandler);

        ListSelectionModel selrow = visualtable.getSelectionModel();
        selrow.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                    return;
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    selectfileLabel.setText("Файл не выбран");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    //int index = e.getFirstIndex();
                    Object value = visualtable.getValueAt(selectedRow, 0);
                    String name = value.toString();
                    selectfileLabel.setText(name);
                    repaint();
                }

            }
        });

        jSeparator2.setOrientation(SwingConstants.VERTICAL);

        selectbuttonLabel = new JLabel();
        selectbuttonLabel.setText("Библиотека");

        Box.Filler filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 0));

        delButton.setText("Удалить");
        delButton.addActionListener(buttonHandler);

        exitButton.setText("Выйти");
        exitButton.addActionListener(buttonHandler);

        accessButton.setText("Доступ");
        accessButton.addActionListener(buttonHandler);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(helloLabel)
                                                .addGap(76, 76, 76)
                                                .addComponent(exitButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(libraryButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(addButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(delButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(selectfileLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)


                                                .addComponent(accessButton)
                                                .addGap(10, 10, 10))
                                        .addComponent(jSeparator1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(selectbuttonLabel)
                                                        .addComponent(musicButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(videoButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(imageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                                                .addContainerGap())))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(libraryButton)
                                                                .addComponent(addButton)
                                                                .addComponent(delButton))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(helloLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(selectbuttonLabel)
                                                .addGap(18, 18, 18)
                                                .addComponent(videoButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(musicButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(imageButton)
                                                .addGap(0, 108, Short.MAX_VALUE))
                                        .addComponent(jSeparator2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(selectfileLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(accessButton)                                                        )
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(1, 1, 1))
        );
        pack();
        setVisible(true);

    }

    private String initdirectory() {
        String directory= ".\\media\\"+User.getLogin();
        fileselect = new File(directory);
        String dir;
        if  (fileselect.exists()){
            dir=fileselect.getPath();
        }
        else {
            boolean res = fileselect.mkdir();
            if(!res)
                JOptionPane.showMessageDialog(null,"Создание директории", "Не удалось создать каталог", JOptionPane.ERROR_MESSAGE);
            dir=fileselect.getPath();
        }
        return dir;
    }

    private String Selectrow(int index) {
        String extension,path,pathfile;
        Object value = visualtable.getValueAt(index,3);
        path=value.toString();
        path=path.trim();
        value= visualtable.getValueAt(index, 2);
        extension=value.toString();
        extension=extension.trim();
        pathfile=path+extension;
        pathfile=pathfile.replace('/','\\');
        return pathfile;
    }

    private void SelectPlay(int index) {

        fileselect = new File(Selectrow(index));
        String filepath=fileselect.getAbsolutePath();
        String name = visualtable.getValueAt(index,0).toString();
        String type= visualtable.getValueAt(index,1).toString();
        String ex = visualtable.getValueAt(index,2).toString();
        System.out.println(ex);
        if (type.contains("графика")) {
            log.log(Level.INFO, "open image\n");
            Images image = new Images(this, name, true, filepath);
            image.setVisible(true);
        }
        else if (type.contains("не определен")) {
            log.log(Level.INFO, "This file type is not supported!\n");
            JOptionPane.showMessageDialog(null, "Даный тип файлов не поддерживается!", "Ошибка ", JOptionPane.ERROR_MESSAGE);

        }
        else {   log.log(Level.INFO, "open media");
                videowindow =new Video(fileselect);
                videowindow.run();
             }
        }

    public ResultSet Visualdata() {
        rs = null;
        pst = null;
        connectDB = connect.getConnect();
        try {
            pst = connectDB.prepareStatement("SELECT Files.Title ," +
                    " f1.Types , f1.Extensions ,  Files.Path , u2.Login , s1.Actions \n" +
                    "from Files, Users u1, Users u2 , Filetypes f1, Settings s1, users_files\n" +
                    "WHERE users_files.idUsers_from_users = u1.IdUser and Files.FiletypeFK=f1.idFiletypes and Files.SetFK=s1.idSettings and\n" +
                    " users_files.idFiles_from_files=Files.idFiles and u1.Login = '"+User.getLogin()+"' and Files.UsersFK = u2.IdUser;");
            rs = pst.executeQuery();
        }catch (SQLException e1) {
            log.log(Level.WARNING, "problems connecting to the database\n", e1);
            JOptionPane.showMessageDialog(null, "Ошибка соединения с БД", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return rs;
    }

    private void Addfile() {
        String type, actions,start1, start2, temp, start3, fileselectname, fileselectpath, pathnewfile, filename, extentsion;
        String filetypestr, pathfileend;
        Object value;
        int idfiletype, idSet;
        jfc.setDialogTitle("Сохранить файл в базу");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res=jfc.showSaveDialog(MainWindow.this);
        connectDB =null; pst=null; rs=null;
        if (res==JFileChooser.APPROVE_OPTION){
            visualtable.setVisible(false);
            fileselect = jfc.getSelectedFile();
            fileselectname = fileselect.getName();
            fileselectpath=fileselect.getPath();
            pathfileend = fileselectpath.replace("\\","/");
            int j=pathfileend.lastIndexOf('.');
            pathnewfile=pathfileend.substring(0,j);
            int i=fileselectname.lastIndexOf('.');
            int len=fileselectname.length();
            filename=fileselectname.substring(0,i) ;
            filetypestr=fileselectname.substring(i,len);
            extentsion = jfc.getTypeDescription(fileselect);
            start1 = "вук";
            start2 = "идео";
            start3 = "исунок";
            if (extentsion.contains(start2))
            {
                type = "видео";
                actions = "воспроизвести";
            }
            else if(extentsion.contains(start1)){
                type = "аудио";
                actions = "воспроизвести";
            }
            else if (extentsion.contains(start3)){
                type = "графика";
                actions = "просмотреть";
            }
            else {
                type = "не определен";
                actions = "не открывается";
            }
            connectDB =connect.getConnect();
            try{
                pst = connectDB.prepareStatement("SELECT idFiletypes from Filetypes where filetypes.Extensions = '"+filetypestr+"';");
                rs = pst.executeQuery();
                dbm.setResultSet(rs);
                value = dbm.getValueAt(0,0);
                String valstr = value.toString();
                if(valstr.isEmpty()){
                    idfiletype = Insertfiletype(filetypestr, type);
                }
                else  {
                    idfiletype= parseInt(valstr);
                }
                pst.close();
                pst= connectDB.prepareStatement("SELECT idSettings from Settings where Settings.actions = '"+actions+"'; ");
                rs = pst.executeQuery();
                dbm.setResultSet(rs);
                if(!rs.wasNull()){
                    idSet = Integer.parseInt(dbm.getValueAt(0, 0).toString()) ;
                }
                else
                    idSet= Insertsettings(actions);
                pst.close();
                pst= connectDB.prepareStatement("INSERT into Files (title, path, usersfk, filetypefk, " +
                        "setfk) values ('"+filename +"', '"+ pathnewfile+"', "+User.getIdUser() +", "+
                        idfiletype +", "+idSet+");");
                pst.execute();
                dbm.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, "Файл добавлен");
                InsertUserFile(filename);
                rs= Visualdata();
                dbm.setResultSet(rs);
                pst.close();
                selectbuttonLabel.setText("Библиотека");
                visualtable.repaint();
                visualtable.setVisible(true);

            } catch (Exception e){
                log.log(Level.WARNING, e.toString(), e);
            } finally {
                try {
                    if(pst!=null)
                        pst.close();
                    if (connectDB !=null)
                        connectDB.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Файл не добавлен");
            jfc.setVisible(false);
        }

    }

    private void InsertUserFile(String filename) {
        rs=null; pst= null;
        connectDB=connect.getConnect();
        try {
            pst=connectDB.prepareStatement("SELECT FILES.idfiles from FILES where title ='"+filename+"';");
            rs= pst.executeQuery();
            dbm.setResultSet(rs);
            int idFile = (int)dbm.getValueAt(0,0);
            pst.close();
            pst=connectDB.prepareStatement("INSERT into USERS_FILES (idUsers_from_users,idFiles_from_files) values ("+User.getIdUser()+", "+idFile+");");
            pst.executeUpdate();

        } catch (Exception e) {
            log.log(Level.WARNING, e.toString(), e);
            e.printStackTrace();
        }
    }

    private int Insertsettings(String actions) {
        int idSet;
        pst=null;
        try {
            pst= connectDB.prepareStatement("INSERT into Settings " +
                    "(actions) value ('"+actions+"');");
            pst.execute();
            dbm.fireTableDataChanged();
            pst.close();
            pst= connectDB.prepareStatement("SELECT idSettings from Settings where Settings.actions = '"+actions+"'; ");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
        }  catch (Exception e) {
            log.log(Level.WARNING, "problems with database\n", e);
        }
        idSet = Integer.parseInt(dbm.getValueAt(0,0).toString()) ;
        dbm.fireTableRowsInserted(idSet,idSet);
        return idSet;
    }

    private int Insertfiletype(String filetypestr, String type){
        int idfiletype;
        pst=null;
        try {
            pst= connectDB.prepareStatement("INSERT into Filetypes " +"(extensions, types) values" +
                    " ('"+filetypestr+"', '"+type+"');");
            pst.execute();
            dbm.fireTableDataChanged();
            pst.close();
            pst = connectDB.prepareStatement("SELECT idFiletypes from Filetypes where filetypes.extensions = '"+filetypestr+"';");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
        } catch (Exception e) {
            log.log(Level.WARNING, e.toString(), e);
        }
        idfiletype = Integer.parseInt(dbm.getValueAt(0,0).toString()) ;
        dbm.fireTableRowsInserted(idfiletype,idfiletype);
        return idfiletype;
    }

    private void Libraryvisual() {
        rs= Visualdata();
        try {
            dbm.setResultSet(rs);

        } catch (Exception e) {
            log.log(Level.WARNING, e.toString(), e);
        }finally {
            try {
                if(pst!=null)
                    pst.close();
                if (connectDB !=null)
                    connectDB.close();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        selectbuttonLabel.setText("Библиотека");
        visualtable.repaint();

    }

    private void Musicvisual() {
        rs = null;    pst = null;     connectDB = connect.getConnect();
        try {
            pst = connectDB.prepareStatement("SELECT Files.Title , f1.Types , f1.Extensions ,  Files.Path , u2.Login , s1.Actions " +
                    "from Files, Users u1, Users u2 , Filetypes f1, Settings s1, users_files " +
                    "WHERE users_files.idUsers_from_users = u1.IdUser and Files.FiletypeFK=f1.idFiletypes and Files.SetFK=s1.idSettings and " +
                    "users_files.idFiles_from_files=Files.idFiles and u1.Login = '"+User.getLogin()+" '"+
                    "and Files.UsersFK = u2.IdUser and f1.Types='аудио';");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
            selectbuttonLabel.setText("Музыка");
            visualtable.repaint();
        } catch (SQLException e1) {
            e1.printStackTrace();
            log.log(Level.WARNING, e1.toString(), e1);
            JOptionPane.showMessageDialog(null, "Ошибка соединения с БД", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                if(pst!=null)
                    pst.close();
                if (connectDB !=null)
                    connectDB.close();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void Videovisual()  {
        rs = null;    pst = null;     connectDB = connect.getConnect();
        try {
            pst = connectDB.prepareStatement("SELECT Files.Title , f1.Types , f1.Extensions ,  Files.Path , u2.Login , s1.Actions " +
                    "from Files, Users u1, Users u2 , Filetypes f1, Settings s1, users_files " +
                    "WHERE users_files.idUsers_from_users = u1.IdUser and Files.FiletypeFK=f1.idFiletypes and Files.SetFK=s1.idSettings and " +
                    "users_files.idFiles_from_files=Files.idFiles and u1.Login = '"+User.getLogin()+" '"+
                    "and Files.UsersFK = u2.IdUser and f1.Types='видео';");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
            selectbuttonLabel.setText("Видео");
            visualtable.repaint();
        } catch (SQLException e1) {
            e1.printStackTrace();
            log.log(Level.WARNING, e1.toString(), e1);
            JOptionPane.showMessageDialog(null, "Ошибка соединения с БД", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        } catch (Exception e1) {
            log.log(Level.WARNING, e1.toString(), e1);
            e1.printStackTrace();
        } finally {
            try {
                if(pst!=null)
                    pst.close();
                if (connectDB !=null)
                    connectDB.close();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void Imagevisual ()  {
        rs = null;    pst = null;     connectDB = connect.getConnect();
        try {
            pst = connectDB.prepareStatement("SELECT Files.Title , f1.Types , f1.Extensions ,  Files.Path , u2.Login , s1.Actions " +
                    "from Files, Users u1, Users u2 , Filetypes f1, Settings s1, users_files " +
                    "WHERE users_files.idUsers_from_users = u1.IdUser and Files.FiletypeFK=f1.idFiletypes and Files.SetFK=s1.idSettings and " +
                    "users_files.idFiles_from_files=Files.idFiles and u1.Login = '"+User.getLogin()+" '"+
                    "and Files.UsersFK = u2.IdUser and f1.Types='графика';");
            rs = pst.executeQuery();
            dbm.setResultSet(rs);
            selectbuttonLabel.setText("Фото");
            visualtable.repaint();
        } catch (SQLException e1) {
            log.log(Level.WARNING, e1.toString(), e1);
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка соединения с БД", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception e1) {
            log.log(Level.WARNING, e1.toString(), e1);
            e1.printStackTrace();
        } finally {
            try {
                if(pst!=null)
                    pst.close();
                if (connectDB !=null)
                    connectDB.close();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void Delfile() {
        Object value;
        if(selectfileLabel.getText().equals("Файл не выбран")){
            log.log(Level.WARNING, "File is not selected\n");
            JOptionPane.showMessageDialog(null, "Не выбран файл для удаления", "Удаление", JOptionPane.INFORMATION_MESSAGE);
        }
        else  {
            int index = visualtable.getSelectedRow();
            //delfile=Selectrow(index);
            //fileselect = new File(delfile);
            // boolean res= fileselect.delete();
            //if (!res){
            //    JOptionPane.showMessageDialog(null,"Результат удаления", "Файл не удалось удалить!", JOptionPane.INFORMATION_MESSAGE);
            //}
            value= visualtable.getValueAt(index,3);
            String path = value.toString();
            pst=null;
            connectDB =connect.getConnect();
            try {
                pst = connectDB.prepareStatement("DELETE from Files where path='" + path +
                        "'AND UsersFK = " + User.getIdUser() + ";");
                pst.executeUpdate();
                dbm.fireTableDataChanged();
                String namesetButton = selectbuttonLabel.getText();
                if (namesetButton.contains("Библиотека")){
                        rs= Visualdata();
                        dbm.setResultSet(rs);
                        visualtable.repaint();
                }
                if(namesetButton.contains("Музыка")){
                        Musicvisual();
                }
                if(namesetButton.contains("Видео")){
                        Videovisual();
                }
                if(namesetButton.contains( "Фото")){
                        Imagevisual();
                }


        } catch (Exception e) {
                log.log(Level.WARNING, e.toString(), e);
            }finally {
                try {
                    if(pst!=null)
                        pst.close();
                    if (connectDB !=null)
                        connectDB.close();
                }
                catch (SQLException e) {
                    log.log(Level.WARNING, "problems with close \n", e);
                }
            }

        }
    }

    private void Exituser() {
        Object[] options = {"Да", "Нет"};
        int answer = JOptionPane.showOptionDialog(null,"Вы хотите завершить работу?","Выход",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options);
        if (answer==0){
            setVisible(false);
            new MainWindow();
        }


    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             if ( e.getSource() == addButton )
                Addfile();
            else if (e.getSource()==delButton)
                Delfile();
            else if (e.getSource() == imageButton)
                Imagevisual();
            else if (e.getSource()== musicButton)
                Musicvisual();
            else if (e.getSource()== videoButton)
                Videovisual();
            else if (e.getSource()==exitButton)
                Exituser();
            else if(e.getSource()== libraryButton)
                Libraryvisual();
            else if(e.getSource()==accessButton)
                 Savegrope(e);

            }
        }

    private void Savegrope(ActionEvent e) {

        if((!selectfileLabel.getText().contains("Файл не выбран"))||(!selectfileLabel.getText().isEmpty())){
        Object[] options = {"Да", "Нет"};
        int answer = JOptionPane.showOptionDialog(null,"Разрешить доступ другим пользователям?","Права доступа",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options);
        if (answer==0){
            int index;
            String tmp=selectfileLabel.getText();
            rs=null; pst=null;
            connectDB=connect.getConnect();
            try{
                pst=connectDB.prepareStatement("SELECT idFILES from FILES where TITLE ='"+tmp+"';") ;
                rs=pst.executeQuery();
                dbm.setResultSet(rs);
                pst.close();
            } catch (SQLException e1) {
                log.log(Level.WARNING, e1.toString(), e1);
                e1.printStackTrace();
            }
            Object value=dbm.getValueAt(0,0);
            index=Integer.parseInt(value.toString());
            switch (selectbuttonLabel.getText()){
                case "Библиотека":{
                    Libraryvisual();
                    break;
                }
                case "Фото":{
                    Imagevisual();
                    break;
                }
                case "Музыка":{
                    Musicvisual();
                    break;
                }
                case "Видео":{
                    Videovisual();
                    break;
                }
            }
            repaint();
            Access access = new Access(this, true,index);
            access.setVisible(true);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Не выбран файл", "Ошибка ", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new MainWindow();
            }
        });
    }
}

