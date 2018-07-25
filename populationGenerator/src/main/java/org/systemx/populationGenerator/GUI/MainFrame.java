package org.systemx.populationGenerator.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.PlainDocument;

import org.systemx.populationGenerator.App;
import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.config.ConfigGroup;

import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private JTextField cantDepRegTextField;
	private JTextField outputFileTextField;
	private JTextField revenueFileTextField;

	private JCheckBoxList checkBoxDepsList;
	private JTextField threadsTextField;
	private JButton saveConfig;
	private JButton loadConfig;
	private LogarithmicJSlider finesseSlider;
	private JTextField finesseTextField;
	private JTextField pumsTextField;
	private JTextField marginalDataTextField;
	private JButton pumsButton;
	private JButton marginalDataButton;
	private JButton revenutFileButton;
	private JButton outputFileButton;
	private JButton cantDepRegButton;
	private JCheckBox chckbxHouseholdsNumber;
	private JCheckBox chckbxNumberFemmesHommesNumber;
	private JCheckBox chckbxNumberAgeGroupds;
	private JCheckBox chckbxNumberSPC;
	private JButton btnStartGenerator;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JLabel labelFuntionName;
	private JToggleButton tglbtnAddRevenue;

	private Config config;

	/**
	 * Launch the application.
	 */
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 655);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("SPGF²: Synthetic Population Generator For France");
		JLabel SPGF2logo = new JLabel("New label");
		SPGF2logo.setBounds(20, 11, 126 / 2, 150 / 2);

		
		URL SPGF2imgURL = getClass().getClassLoader().getResource("logo-spgf2.png");
		
		BufferedImage SPGF2img = null;
		try {
			SPGF2img = ImageIO.read(SPGF2imgURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image SPGF2dimg = SPGF2img.getScaledInstance(SPGF2logo.getWidth(), SPGF2logo.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon SPGF2imageIcon = new ImageIcon(SPGF2dimg);
		SPGF2logo.setIcon(SPGF2imageIcon);
		contentPane.add(SPGF2logo);

		JLabel CAlogo = new JLabel("New label");
		CAlogo.setBounds(103, 11, 735 / 2, 150 / 2);
		
		
		URL CAimgURL = getClass().getClassLoader().getResource("logo-ca.png");
		
		BufferedImage CAimg = null;
		try {
			CAimg = ImageIO.read(CAimgURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image CAdimg = CAimg.getScaledInstance(CAlogo.getWidth(), CAlogo.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon CAimageIcon = new ImageIcon(CAdimg);
		CAlogo.setIcon(CAimageIcon);
		contentPane.add(CAlogo);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 120, 577, 2);
		contentPane.add(separator);

		JLabel lblConfigManager = new JLabel("Config Manager");
		lblConfigManager.setBounds(20, 97, 102, 14);
		contentPane.add(lblConfigManager);

		JLabel lblJosephKamel = new JLabel("<html>Created by: <br> Joseph KAMEL<br> Reza VOSOOGHI</html>");
		lblJosephKamel.setBounds(480, 11, 107, 63);
		contentPane.add(lblJosephKamel);

		JLabel lblDepartments = new JLabel("Departments:");
		lblDepartments.setBounds(20, 133, 83, 14);
		contentPane.add(lblDepartments);

		String deps[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "2A", "2B", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
				"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
				"51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
				"68", "69", "69M", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83",
				"84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "971", "972", "973", "974",
				"976" };

		JPanel jpnl = new JPanel();
		// jpnl.setBounds(103, 130, 78, 20);
		jpnl.setLayout(new GridLayout(1, 0, 0, 0));

		DefaultListModel<JCheckBox> model = new DefaultListModel<JCheckBox>();
		checkBoxDepsList = new JCheckBoxList(model);

		for (int i = 0; i < deps.length; i++) {
			JCheckBox checkBox = new JCheckBox(deps[i]);
			checkBox.setName(deps[i]);
			model.addElement(checkBox);
		}

		jpnl.add(checkBoxDepsList);

		JScrollPane scrollPane = new JScrollPane(jpnl);
		scrollPane.setBounds(103, 130, 78, 86);

		contentPane.add(scrollPane);

		JLabel lblFinesse = new JLabel("Finesse:");
		lblFinesse.setBounds(196, 183, 63, 14);
		contentPane.add(lblFinesse);

		JLabel lblThreads = new JLabel("Threads:");
		lblThreads.setBounds(196, 138, 57, 14);
		contentPane.add(lblThreads);

		threadsTextField = new JTextField();
		threadsTextField.setBounds(251, 137, 78, 17);
		contentPane.add(threadsTextField);

		threadsTextField.setText("1");

		PlainDocument doc = (PlainDocument) threadsTextField.getDocument();
		doc.setDocumentFilter(new IntFilter(1, 1024));

		JLabel lblPums = new JLabel("PUMS:");
		lblPums.setBounds(20, 236, 102, 14);
		contentPane.add(lblPums);

		pumsTextField = new JTextField();
		pumsTextField.setBounds(103, 233, 207, 20);
		contentPane.add(pumsTextField);
		pumsTextField.setColumns(10);

		pumsButton = new JButton("FC");
		pumsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pumsButton.setBounds(320, 230, 46, 23);
		contentPane.add(pumsButton);
		pumsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					pumsTextField.setText(path);
				}
			}
		});

		marginalDataButton = new JButton("FC");
		marginalDataButton.setBounds(320, 262, 46, 23);
		contentPane.add(marginalDataButton);
		marginalDataButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					marginalDataTextField.setText(path);
				}
			}
		});

		marginalDataTextField = new JTextField();
		marginalDataTextField.setColumns(10);
		marginalDataTextField.setBounds(103, 263, 207, 20);
		contentPane.add(marginalDataTextField);

		JLabel lblMarginaldata = new JLabel("Marginal Data:");
		lblMarginaldata.setBounds(20, 266, 102, 14);
		contentPane.add(lblMarginaldata);

		cantDepRegButton = new JButton("FC");
		cantDepRegButton.setBounds(320, 291, 46, 23);
		contentPane.add(cantDepRegButton);
		cantDepRegButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					cantDepRegTextField.setText(path);
				}
			}
		});

		cantDepRegTextField = new JTextField();
		cantDepRegTextField.setColumns(10);
		cantDepRegTextField.setBounds(103, 292, 207, 20);
		contentPane.add(cantDepRegTextField);

		JLabel lblCantdepreg = new JLabel("CantDepReg:");
		lblCantdepreg.setBounds(20, 296, 102, 14);
		contentPane.add(lblCantdepreg);

		outputFileButton = new JButton("FC");
		outputFileButton.setBounds(320, 322, 46, 23);
		contentPane.add(outputFileButton);
		outputFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					outputFileTextField.setText(path);
				}
			}
		});

		outputFileTextField = new JTextField();
		outputFileTextField.setColumns(10);
		outputFileTextField.setBounds(103, 323, 207, 20);
		contentPane.add(outputFileTextField);

		JLabel lblOutputfile = new JLabel("Output Path:");
		lblOutputfile.setBounds(20, 326, 102, 14);
		contentPane.add(lblOutputfile);

		JPanel panel = new JPanel();
		panel.setBounds(400, 230, 190, 140);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(5, 0, 0, 0));

		JLabel lblRepectedMarginalData = new JLabel("Respected Marginal Data:");
		panel.add(lblRepectedMarginalData);
		chckbxHouseholdsNumber = new JCheckBox("Households Number");
		panel.add(chckbxHouseholdsNumber);
		chckbxNumberFemmesHommesNumber = new JCheckBox("Gender");
		panel.add(chckbxNumberFemmesHommesNumber);
		chckbxNumberAgeGroupds = new JCheckBox("Age Groups");
		panel.add(chckbxNumberAgeGroupds);
		chckbxNumberSPC = new JCheckBox("SocioPro Categories");
		panel.add(chckbxNumberSPC);

		saveConfig = new JButton("Save Config");
		saveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String configPath = fileChooser.getSelectedFile().getAbsolutePath();

					config = getConfigfromGUI();

					try {
						ConfigGroup.write(config, configPath);
						System.out.println("Config written to: " + configPath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		saveConfig.setBounds(350, 133, 113, 23);
		contentPane.add(saveConfig);

		loadConfig = new JButton("Load Config");
		loadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String configPath = fileChooser.getSelectedFile().getAbsolutePath();
					try {
						config = ConfigGroup.read(configPath);
						setConfigToGUI(config);
						System.out.println("Config read from: " + configPath);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		loadConfig.setBounds(473, 133, 113, 23);
		contentPane.add(loadConfig);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 417, 577, 2);
		contentPane.add(separator_1);

		textArea = new JTextArea();
		JScrollPane jScrollPane = new JScrollPane(textArea);
		jScrollPane.setBounds(10, 485, 577, 125);
		contentPane.add(jScrollPane);
		
		MessageConsole mc = new MessageConsole(textArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 454, 577, 20);
		contentPane.add(progressBar);

		labelFuntionName = new JLabel("Not Started");
		labelFuntionName.setBounds(10, 429, 200, 14);
		contentPane.add(labelFuntionName);

		btnStartGenerator = new JButton("Start Generator");
		btnStartGenerator.setBounds(454, 425, 133, 23);
		contentPane.add(btnStartGenerator);
		btnStartGenerator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				config = getConfigfromGUI();
				System.out.println("Starting Generator");
				labelFuntionName.setText("Starting Generator");
				contentPane.repaint();
				App.run(config, progressBar, labelFuntionName, contentPane);
			}
		});

		finesseTextField = new JTextField();
		finesseTextField.setBounds(509, 183, 78, 17);
		contentPane.add(finesseTextField);
		finesseTextField.setText("100");

		finesseSlider = new LogarithmicJSlider(1, 100000, 100);
		finesseSlider.setBounds(249, 180, 250, 23);
		finesseSlider.setPaintTicks(true);
		finesseSlider.setPaintTrack(true);
		contentPane.add(finesseSlider);

		PlainDocument docF = (PlainDocument) finesseTextField.getDocument();
		docF.setDocumentFilter(new IntFilter(1, 100000));

		finesseTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				JTextField source = (JTextField) e.getSource();
				finesseSlider.setValue(Integer.parseInt(source.getText()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		finesseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				// update text field when the slider value changes
				JSlider source = (JSlider) event.getSource();
				finesseTextField.setText("" + source.getValue());
			}
		});

		tglbtnAddRevenue = new JToggleButton("Add Revenue");
		tglbtnAddRevenue.setBounds(20, 354, 121, 23);
		contentPane.add(tglbtnAddRevenue);

		tglbtnAddRevenue.setSelected(false);

		JLabel lblRevenuefile = new JLabel("RevenueFile:");
		lblRevenuefile.setBounds(20, 387, 102, 14);
		contentPane.add(lblRevenuefile);
		

		revenutFileButton = new JButton("FC");
		revenutFileButton.setBounds(320, 383, 46, 23);
		contentPane.add(revenutFileButton);

		revenutFileButton.setEnabled(false);
		
		revenutFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					revenueFileTextField.setText(path);
				}
			}
		});

		revenueFileTextField = new JTextField();
		revenueFileTextField.setColumns(10);
		revenueFileTextField.setBounds(103, 384, 207, 20);
		contentPane.add(revenueFileTextField);

		revenueFileTextField.setEnabled(false);

		tglbtnAddRevenue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JToggleButton source = (JToggleButton) e.getSource();
				if (source.isSelected()) {
					revenueFileTextField.setEnabled(true);
					revenutFileButton.setEnabled(true);
				} else {
					revenueFileTextField.setEnabled(false);
					revenutFileButton.setEnabled(false);
				}

			}
		});
	}

	void setConfigToGUI(Config config) {
		String deps = config.getDepString();
		List<String> depList = Arrays.asList(deps.split("-"));
		for (int i = 0; i < checkBoxDepsList.getModel().getSize(); i++) {
			JCheckBox checkBox = checkBoxDepsList.getModel().getElementAt(i);
						
			if (depList.contains(checkBox.getName())) {
				checkBox.setSelected(true);
			} else {
				checkBox.setSelected(false);
			}
		}

		threadsTextField.setText(String.valueOf(config.getNbrThreads()));
		finesseTextField.setText(String.valueOf(config.getFinesse()));
		finesseSlider.setValue(config.getFinesse());

		pumsTextField.setText(config.getPumsPath());
		marginalDataTextField.setText(config.getMarginalDataPath());
		cantDepRegTextField.setText(config.getCantDepRegPath());
		outputFileTextField.setText(config.getOutputPath());

		if (config.isAddRevenue()) {
			tglbtnAddRevenue.setSelected(true);
			revenueFileTextField.setText(config.RevenuePath);
			revenueFileTextField.setEnabled(true);
			revenutFileButton.setEnabled(true);
		} else {
			tglbtnAddRevenue.setSelected(false);
			revenueFileTextField.setEnabled(false);
			revenutFileButton.setEnabled(false);
		}

		if (config.isMarginalErrorMenages()) {
			chckbxHouseholdsNumber.setSelected(true);
		} else {
			chckbxHouseholdsNumber.setSelected(false);
		}
		if (config.isMarginalErrorHommesFemmes()) {
			chckbxNumberFemmesHommesNumber.setSelected(true);
		} else {
			chckbxNumberFemmesHommesNumber.setSelected(false);
		}
		if (config.isMarginalErrorAgeGroup()) {
			chckbxNumberAgeGroupds.setSelected(true);
		} else {
			chckbxNumberAgeGroupds.setSelected(false);
		}
		if (config.isMarginalErrorSocioProfessionalCategory()) {
			chckbxNumberSPC.setSelected(true);
		} else {
			chckbxNumberSPC.setSelected(false);
		}
	}

	Config getConfigfromGUI() {
		Config config = new Config();
		List<String> depsList = new ArrayList<String>();
		for (int i = 0; i < checkBoxDepsList.getModel().getSize(); i++) {
			JCheckBox checkBox = checkBoxDepsList.getModel().getElementAt(i);

			if (checkBox.isSelected()) {
				depsList.add(checkBox.getName());
			}
		}
		String deps = "";
		
		for (int i = 0; i < depsList.size(); i++) {
			if(i>0){
				deps = deps + "-" + depsList.get(i);
			}else{
				deps = depsList.get(i);
			}

		}
		
		config.setDepString(deps);

		config.setNbrThreads(Integer.parseInt(threadsTextField.getText()));
		config.setFinesse(Integer.parseInt(finesseTextField.getText()));

		config.setPumsPath(pumsTextField.getText());
		config.setPumsFilteredPath(pumsTextField.getText() + ".filtered");
		config.setMarginalDataPath(marginalDataTextField.getText());
		config.setCantDepRegPath(cantDepRegTextField.getText());
		config.setOutputPath(outputFileTextField.getText());

		if (tglbtnAddRevenue.isSelected()) {
			config.setAddRevenue(true);
			config.setRevenuePath(revenueFileTextField.getText());
		} else {
			config.setAddRevenue(false);
		}

		if (chckbxHouseholdsNumber.isSelected()) {
			config.setMarginalErrorMenages(true);
		} else {
			config.setMarginalErrorMenages(false);
		}

		if (chckbxNumberFemmesHommesNumber.isSelected()) {
			config.setMarginalErrorHommesFemmes(true);
		} else {
			config.setMarginalErrorHommesFemmes(false);
		}

		if (chckbxNumberAgeGroupds.isSelected()) {
			config.setMarginalErrorAgeGroup(true);
		} else {
			config.setMarginalErrorAgeGroup(false);
		}

		if (chckbxNumberSPC.isSelected()) {
			config.setMarginalErrorSocioProfessionalCategory(true);
		} else {
			config.setMarginalErrorSocioProfessionalCategory(false);
		}

		return config;
	}
}
