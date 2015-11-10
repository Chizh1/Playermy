import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Images  extends JDialog{
    private BufferedImage [] images;
    private JPanel scalejPanel;
    private JTextField jTextField1;
    private double scaleValue = 1.0;
    private int count=0;
    private Box box;
    private File file;
    private DrawJPanel Draw;
    private String fileabsolpath;

    public Images(Frame parant, String name, boolean r, String  filepath  ) {
        super(parant,name,r);
        fileabsolpath = filepath;
        file=new File(fileabsolpath);
        initComponent();

    }

    private void initComponent() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setSize(640, 480);
        box=Box.createVerticalBox();
        Draw = new DrawJPanel() ;
        Draw.setBackground(Color.getHSBColor(217,237,255));
        JSeparator jSeparator1 = new JSeparator();
        scalejPanel = new JPanel();
        JLabel jLabel1 = new JLabel("Размер рисунка");
        jTextField1 = new JTextField();
        jTextField1.setPreferredSize(new Dimension(90,25));
        JButton jButton1 = new JButton("Изменить размер");

        scalejPanel.add(jLabel1);
        scalejPanel.add(jTextField1);
        scalejPanel.add(jButton1);
        scalejPanel.add(jSeparator1);

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleValue = Double.parseDouble(jTextField1.getText()) / 100.0;

                repaint();
            }
        });

        setPreferredSize(new Dimension(505, 510));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        scalejPanel.setOpaque(false);
        scalejPanel.setPreferredSize(new Dimension(480, 44));

        GroupLayout scalejPanel1Layout = new GroupLayout(scalejPanel);
        scalejPanel.setLayout(scalejPanel1Layout);
        scalejPanel1Layout.setHorizontalGroup(
                scalejPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(scalejPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(14, 14, 14)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(scalejPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(scalejPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jSeparator1)
                                        .addContainerGap()))
        );
        scalejPanel1Layout.setVerticalGroup(
                scalejPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(scalejPanel1Layout.createSequentialGroup()
                                .addGroup(scalejPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(scalejPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(scalejPanel1Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Draw.setPreferredSize(new Dimension(640, 460));
        Showimage(file);
        pack();
    }

    private void Showimage(File file){

        try {
            String name = file.getName();
            String type = name.substring(name.lastIndexOf('.')+1);
            Iterator<ImageReader> iter=ImageIO.getImageReadersBySuffix(type);
            ImageReader reader = iter.next();
            ImageInputStream imagein = ImageIO.createImageInputStream(file);
            reader.setInput(imagein);
            count = reader.getNumImages(true);
            images = new BufferedImage[count];
            for(int i= 0; i<count; i++){
                box.add(scalejPanel, BorderLayout.NORTH);
                images[i]=reader.read(i);
                box.add(Draw, BorderLayout.NORTH);

        }

            setContentPane(new JScrollPane(box));
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    private class DrawJPanel extends JPanel{

		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int i = 0; i<count;i++){
                double spareWidth = getWidth() - scaleValue * images[i].getWidth();
                double spareHeight = getHeight() - scaleValue * images[i].getHeight();
                g.drawImage( images[i],
                        (int) ( spareWidth ) / 2, (int) ( spareHeight ) / 2,
                        (int) ( images[i].getWidth() * scaleValue ),
                        (int) ( images[i].getHeight() * scaleValue ), this );
            }
        }
    }
}



