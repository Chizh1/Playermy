import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.MediaPlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideoPanel extends JDialog {

    private static final long serialVersionUID = 1L;

    private final MediaPlayer mediaPlayer;

    private JCheckBox enableVideoAdjustCheckBox;

    private JLabel contrastLabel;
    private JSlider contrastSlider;

    private JLabel brightnessLabel;
    private JSlider brightnessSlider;

    private JLabel hueLabel;
    private JSlider hueSlider;

    private JLabel saturationLabel;
    private JSlider saturationSlider;

    private JLabel gammaLabel;
    private JSlider gammaSlider;

    public VideoPanel(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

        createUI();
    }

    private void createUI() {
        createControls();
        layoutControls();
        registerListeners();
    }

    private void createControls() {

        setSize(150,300);
        setTitle("Video control");
        enableVideoAdjustCheckBox = new JCheckBox("Video Adjust");

        contrastLabel = new JLabel("Contrast");
        contrastSlider = new JSlider();
        contrastSlider.setOrientation(JSlider.HORIZONTAL);
        contrastSlider.setMinimum(Math.round(LibVlcConst.MIN_CONTRAST * 100.0f));
        contrastSlider.setMaximum(Math.round(LibVlcConst.MAX_CONTRAST * 100.0f));
        contrastSlider.setPreferredSize(new Dimension(100, 40));
        contrastSlider.setToolTipText("Change Contrast");
        contrastSlider.setEnabled(false);
        contrastSlider.setPaintLabels(true);
        contrastSlider.setPaintTicks(true);

        brightnessLabel = new JLabel("Brightness");
        brightnessSlider = new JSlider();
        brightnessSlider.setOrientation(JSlider.HORIZONTAL);
        brightnessSlider.setMinimum(Math.round(LibVlcConst.MIN_BRIGHTNESS * 100.0f));
        brightnessSlider.setMaximum(Math.round(LibVlcConst.MAX_BRIGHTNESS * 100.0f));
        brightnessSlider.setPreferredSize(new Dimension(100, 40));
        brightnessSlider.setToolTipText("Change Brightness");
        brightnessSlider.setEnabled(false);

        hueLabel = new JLabel("Hue");
        hueSlider = new JSlider();
        hueSlider.setOrientation(JSlider.HORIZONTAL);
        hueSlider.setMinimum(LibVlcConst.MIN_HUE);
        hueSlider.setMaximum(LibVlcConst.MAX_HUE);
        hueSlider.setPreferredSize(new Dimension(100, 40));
        hueSlider.setToolTipText("Change ");
        hueSlider.setEnabled(false);

        saturationLabel = new JLabel("Saturation");
        saturationSlider = new JSlider();
        saturationSlider.setOrientation(JSlider.HORIZONTAL);
        saturationSlider.setMinimum(Math.round(LibVlcConst.MIN_SATURATION * 100.0f));
        saturationSlider.setMaximum(Math.round(LibVlcConst.MAX_SATURATION * 100.0f));
        saturationSlider.setPreferredSize(new Dimension(100, 40));
        saturationSlider.setToolTipText("Change ");
        saturationSlider.setEnabled(false);

        gammaLabel = new JLabel("Gamma");
        gammaSlider = new JSlider();
        gammaSlider.setOrientation(JSlider.HORIZONTAL);
        gammaSlider.setMinimum(Math.round(LibVlcConst.MIN_GAMMA * 100.0f));
        gammaSlider.setMaximum(Math.round(LibVlcConst.MAX_GAMMA * 100.0f));
        gammaSlider.setPreferredSize(new Dimension(100, 40));
        gammaSlider.setToolTipText("Change ");
        gammaSlider.setEnabled(false);

        contrastSlider.setValue(Math.round(mediaPlayer.getBrightness() * 100.0f));
        brightnessSlider.setValue(Math.round(mediaPlayer.getContrast() * 100.0f));
        hueSlider.setValue(mediaPlayer.getHue());
        saturationSlider.setValue(Math.round(mediaPlayer.getSaturation() * 100.0f));
        gammaSlider.setValue(Math.round(mediaPlayer.getGamma() * 100.0f));
    }

    private void layoutControls() {

        setLayout(new BorderLayout());

        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
        slidersPanel.add(enableVideoAdjustCheckBox);
        slidersPanel.add(contrastLabel);
        slidersPanel.add(contrastSlider);
        slidersPanel.add(brightnessLabel);
        slidersPanel.add(brightnessSlider);
        slidersPanel.add(hueLabel);
        slidersPanel.add(hueSlider);
        slidersPanel.add(saturationLabel);
        slidersPanel.add(saturationSlider);
        slidersPanel.add(gammaLabel);
        slidersPanel.add(gammaSlider);

        add(slidersPanel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        enableVideoAdjustCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enabled = enableVideoAdjustCheckBox.isSelected();
                contrastSlider.setEnabled(enabled);
                brightnessSlider.setEnabled(enabled);
                hueSlider.setEnabled(enabled);
                saturationSlider.setEnabled(enabled);
                gammaSlider.setEnabled(enabled);
                mediaPlayer.setAdjustVideo(enabled);
            }
        });

        contrastSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setContrast(source.getValue() / 100.0f);
                // }
            }
        });

        brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setBrightness(source.getValue() / 100.0f);
                // }
            }
        });

        hueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setHue(source.getValue());
                // }
            }
        });

        saturationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setSaturation(source.getValue() / 100.0f);
                // }
            }
        });

        gammaSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setGamma(source.getValue() / 100.0f);
                // }
            }
        });
    }
}

