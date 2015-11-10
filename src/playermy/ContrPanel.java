

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Adminh
 */

public class ContrPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int SKIP_TIME_MS = 10 * 1000;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final EmbeddedMediaPlayer mediaPlayer;

    private JLabel timeLabel;
    //    private JProgressBar positionProgressBar;
    private JSlider positionSlider;
    private JLabel chapterLabel;

    private JButton previousChapterButton;
    private JButton rewindButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton fastForwardButton;
    private JButton nextChapterButton;

    private JButton toggleMuteButton;
    private JSlider volumeSlider;

    private JButton videopanel;

   // private JButton equalizer;


    private boolean mousePressedPlaying = false;

    public ContrPanel(EmbeddedMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

        createUI();

        executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer), 0L, 1L, TimeUnit.SECONDS);
    }

    private void createUI() {
        createControls();
        layoutControls();
        registerListeners();
    }

    private void createControls() {
        timeLabel = new JLabel("hh:mm:ss");

        // positionProgressBar = new JProgressBar();
        // positionProgressBar.setMinimum(0);
        // positionProgressBar.setMaximum(1000);
        // positionProgressBar.setValue(0);
        // positionProgressBar.setToolTipText("Time");

        positionSlider = new JSlider();
        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);
        positionSlider.setToolTipText("Position");

        chapterLabel = new JLabel("00/00");

        previousChapterButton = new JButton();
        URL url = this.getClass().getResource("/resource/control_start_blue.png");
        previousChapterButton.setIcon(new ImageIcon(url));
        previousChapterButton.setToolTipText("Go to previous chapter");

        rewindButton = new JButton();
        url= this.getClass().getResource("/resource/control_rewind_blue.png");
        rewindButton.setIcon(new ImageIcon(url));
        rewindButton.setToolTipText("Skip back");

        stopButton = new JButton();
        url= this.getClass().getResource("/resource/control_stop_blue.png" );
        stopButton.setIcon(new ImageIcon(url));
        stopButton.setToolTipText("Stop");

        pauseButton = new JButton();
        url= this.getClass().getResource("/resource/control_pause_blue.png");
        pauseButton.setIcon(new ImageIcon(url));
        pauseButton.setToolTipText("Play/pause");

        playButton = new JButton();
        url= this.getClass().getResource("/resource/control_play_blue.png");
        playButton.setIcon(new ImageIcon(url));
        playButton.setToolTipText("Play");

        fastForwardButton = new JButton();
        url= this.getClass().getResource("/resource/control_fastforward_blue.png");
        fastForwardButton.setIcon(new ImageIcon(url));
        fastForwardButton.setToolTipText("Skip forward");

        nextChapterButton = new JButton();
        url= this.getClass().getResource("/resource/control_end_blue.png");
        nextChapterButton.setIcon(new ImageIcon(url));
        nextChapterButton.setToolTipText("Go to next chapter");

        toggleMuteButton = new JButton();
        url= this.getClass().getResource("/resource/sound_mute.png");
        toggleMuteButton.setIcon(new ImageIcon(url));
        toggleMuteButton.setToolTipText("Toggle Mute");

        volumeSlider = new JSlider();
        volumeSlider.setOrientation(JSlider.HORIZONTAL);
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
        volumeSlider.setPreferredSize(new Dimension(100, 40));
        volumeSlider.setToolTipText("Change volume");

        videopanel = new JButton();
        url= this.getClass().getResource("/resource/control_equalizer_blue.png");
        videopanel.setIcon(new ImageIcon(url));
        videopanel.setToolTipText("Vidio equalizer");

       // equalizer = new JButton();
       // url= this.getClass().getResource("/resource/control_equalizer.png");
       // equalizer.setIcon(new ImageIcon(url));
       // equalizer.setToolTipText("Audio equalizer");
    }

    private void layoutControls() {

        setLayout(new BorderLayout());

        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new GridLayout(1, 1));
        // positionPanel.add(positionProgressBar);
        positionPanel.add(positionSlider);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(8, 0));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(positionPanel, BorderLayout.CENTER);
        topPanel.add(chapterLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(previousChapterButton);
        bottomPanel.add(rewindButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(playButton);
        bottomPanel.add(fastForwardButton);
        bottomPanel.add(nextChapterButton);

        bottomPanel.add(volumeSlider);
        bottomPanel.add(toggleMuteButton);

        bottomPanel.add(videopanel);

       // bottomPanel.add(equalizer);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setSliderBasedPosition() {
        if(!mediaPlayer.isSeekable()) {
            return;
        }
        float positionValue = positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        mediaPlayer.setPosition(positionValue);
    }

    private void updateUIState() {
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.play();
            if(!mousePressedPlaying) {
                try {
                    Thread.sleep(500);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                mediaPlayer.pause();
            }
        }
        long time = mediaPlayer.getTime();
        int position = (int)(mediaPlayer.getPosition() * 1000.0f);
        int chapter = mediaPlayer.getChapter();
        int chapterCount = mediaPlayer.getChapterCount();
        updateTime(time);
        updatePosition(position);
        updateChapter(chapter, chapterCount);
    }

    private void skip(int skipTime) {
        if(mediaPlayer.getLength() > 0) {
            mediaPlayer.skip(skipTime);
            updateUIState();
        }
    }

    private void registerListeners() {
        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
//                updateVolume(mediaPlayer.getVolume());
            }
        });

        positionSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mediaPlayer.isPlaying()) {
                    mousePressedPlaying = true;
                    mediaPlayer.pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });

        previousChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.previousChapter();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(-SKIP_TIME_MS);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.stop();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.pause();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.play();
            }
        });

        fastForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(SKIP_TIME_MS);
            }
        });

        nextChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.nextChapter();
            }
        });

        toggleMuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.mute();
            }
        });

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setVolume(source.getValue());
                // }
            }
        });



        videopanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VideoPanel videoPanel = new VideoPanel(mediaPlayer);
                videoPanel.setVisible(true);
            }
        });

       /* equalizer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(LibVlc.SYNC_INSTANCE);
                Equalizer equalizerpanel;
                final JFrame equalizerFrame;
                if(mediaPlayerFactory.isEqualizerAvailable()) {
                    equalizerpanel = mediaPlayerFactory.newEqualizer();
                    equalizerFrame = new EqualizerFrame(mediaPlayerFactory.getEqualizerBandFrequencies(),
                            mediaPlayerFactory.getEqualizerPresetNames(), mediaPlayerFactory, mediaPlayer, equalizerpanel);
                    equalizerFrame.setVisible(true);
                }
                else {
                    equalizerFrame = null;
                }
            }
        });*/
    }

    private final class UpdateRunnable implements Runnable {

        private final MediaPlayer mediaPlayer;

        private UpdateRunnable(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        @Override
        public void run() {

            final long time = mediaPlayer.getTime();
            final int position = (int)(mediaPlayer.getPosition() * 1000.0f);
            final int chapter = mediaPlayer.getChapter();
            final int chapterCount = mediaPlayer.getChapterCount();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer.isPlaying()) {
                        updateTime(time);
                        updatePosition(position);
                        updateChapter(chapter, chapterCount);
                    }
                }
            });
        }

    }

    private void updateTime(long millis) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timeLabel.setText(s);
    }

    private void updatePosition(int value) {
        // positionProgressBar.setValue(value);
        positionSlider.setValue(value);
    }

    private void updateChapter(int chapter, int chapterCount) {
        String s = chapterCount != -1 ? (chapter + 1) + "/" + chapterCount : "-";
        chapterLabel.setText(s);
        chapterLabel.invalidate();
        validate();
    }

    private void updateVolume(int value) {
        volumeSlider.setValue(value);
    }

}
