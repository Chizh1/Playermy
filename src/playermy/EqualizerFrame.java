import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.Equalizer;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * @author Adminh
 */

@SuppressWarnings("serial")
public class EqualizerFrame extends JFrame implements ChangeListener, ActionListener, ItemListener {

    private static final String BAND_INDEX_PROPERTY = "equalizerBandIndex";

    private final String dbFormat = "%.2fdB";

    private final MediaPlayerFactory mediaPlayerFactory;
    private final MediaPlayer mediaPlayer;
    private final Equalizer equalizer;

    private final SliderControl preampControl;
    private final SliderControl[] bandControls;

    private final JToggleButton enableButton;
    private final JComboBox presetComboBox;


    public EqualizerFrame(List<Float> list, List<String> presets, MediaPlayerFactory mediaPlayerFactory, MediaPlayer mediaPlayer, Equalizer equalizer) {
        super("Equalizer");

        this.mediaPlayerFactory = mediaPlayerFactory;
        this.mediaPlayer = mediaPlayer;
        this.equalizer = equalizer;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(100,100);
        setTitle ("Equalizer") ;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(4,  4,  4,  4));
        contentPane.setLayout(new BorderLayout(0, 4));

        JPanel bandsPane = new JPanel();
        bandsPane.setLayout(new GridLayout(1, 1 + list.size(), 2, 0));

        preampControl = new SliderControl("Preamp", (int)LibVlcConst.MIN_GAIN, (int)LibVlcConst.MAX_GAIN, 0, dbFormat);
        preampControl.getSlider().addChangeListener(this);
        bandsPane.add(preampControl);

        bandControls = new SliderControl[list.size()];
        for(int i = 0; i < list.size(); i++) {
            bandControls[i] = new SliderControl(formatFrequency(list.get(i)), (int)LibVlcConst.MIN_GAIN, (int)LibVlcConst.MAX_GAIN, 0, dbFormat);
            bandControls[i].getSlider().putClientProperty(BAND_INDEX_PROPERTY, i);
            bandControls[i].getSlider().addChangeListener(this);
            bandsPane.add(bandControls[i]);
        }

        contentPane.add(bandsPane, BorderLayout.CENTER);

        JPanel controlsPane = new JPanel();
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.X_AXIS));

        enableButton = new JToggleButton("Enable");
        enableButton.setMnemonic('e');
        controlsPane.add(enableButton);

        controlsPane.add(Box.createHorizontalGlue());

        JLabel presetLabel = new JLabel("Preset:");
        presetLabel.setDisplayedMnemonic('p');
        controlsPane.add(presetLabel);

        presetComboBox = new JComboBox();
        presetLabel.setLabelFor(presetComboBox);
        DefaultComboBoxModel comboboxModel = (DefaultComboBoxModel)presetComboBox.getModel();
        comboboxModel.addElement(null);
        for(String presetName : presets) {
            comboboxModel.addElement(presetName);
        }
        presetComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value != null) {
                    label.setText(String.valueOf(value));
                }
                else {
                    label.setText("--Select--");
                }
                return label;
            }
        });
        controlsPane.add(presetComboBox);

        contentPane.add(controlsPane, BorderLayout.SOUTH);

        setContentPane(contentPane);

        enableButton.addActionListener(this);
        presetComboBox.addItemListener(this);
    }

    private String formatFrequency(float hz) {
        if(hz < 1000.0f) {
            return String.format("%.0f Hz", hz);
        }
        else {
            return String.format("%.0f kHz", hz / 1000f);
        }
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        boolean enable = enableButton.isSelected();
        if(!enable) {
            presetComboBox.setSelectedItem(null);
        }
        mediaPlayer.setEqualizer(enable ? equalizer : null);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider)e.getSource();

            Integer index = (Integer)slider.getClientProperty(BAND_INDEX_PROPERTY);
            int value = slider.getValue();
            // Band...
            if(index != null) {
                equalizer.setAmp(index, value / 100f);
            }
            // Preamp...
            else {
                equalizer.setPreamp(value / 100f);
            }

            if(!applyingPreset) {
                presetComboBox.setSelectedItem(null);
            }
        }
    }

    boolean applyingPreset;

    @Override
    public final void itemStateChanged(ItemEvent e) {
        String presetName = (String)presetComboBox.getSelectedItem();
        if(e.getStateChange() == ItemEvent.SELECTED) {
            if(presetName != null) {
                Equalizer presetEqualizer = mediaPlayerFactory.newEqualizer(presetName);
                if(presetEqualizer != null) {
                    applyingPreset = true;
                    preampControl.getSlider().setValue((int)(presetEqualizer.getPreamp() * 100f));
                    float[] amps = presetEqualizer.getAmps();
                    for(int i = 0; i < amps.length; i++) {
                        bandControls[i].getSlider().setValue((int)(amps[i] * 100f));
                    }

                    applyingPreset = false;
                }
            }
        }
    }
}
