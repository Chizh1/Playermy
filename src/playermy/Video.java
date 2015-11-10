import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Video extends JFrame {

    private EmbeddedMediaPlayer media ;
    private String filepath;
    private ContrPanel controlpanel;
    private File selectfile;


    public Video(File file) {

        selectfile=file;
        filepath=selectfile.getAbsolutePath();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(854, 480));
        setTitle(file.getName());

        File vlcInstallPath = new File(".\\lib\\libvlc.dll");
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcInstallPath.getAbsolutePath());

        EmbeddedMediaPlayerComponent mediaPlayer = new EmbeddedMediaPlayerComponent();
        media = mediaPlayer.getMediaPlayer();

        controlpanel = new ContrPanel(media);
        controlpanel.setSize(854,50);

        Path selectfilepath= file.toPath();
        try {
            String mime=Files.probeContentType(selectfilepath);
            if(mime.contains("audio")){
                setSize(854,106);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        getContentPane().add(mediaPlayer);
        getContentPane().add(controlpanel,"South");

        validate();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                media.stop();
            }
        });
    }




    public void run(){
        media.prepareMedia(filepath);
        media.parseMedia();
        media.play();
    }


}

