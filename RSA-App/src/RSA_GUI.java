//----------------------------
// @author Kayra POLAT
// RSA_GUI.java
// Class implementation of User Interface of RSA Program
//----------------------------

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ScrollPaneConstants;

public class RSA_GUI {

	private JFrame frame;
	private JTextField message;
	private ArrayList<KeyGen> keys = new ArrayList<KeyGen>(); // Arraylist for keep created key value pair
	private Encryption encryption;
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	private JPanel EncryptionPanel;
	private JLabel instruction1;
	private JLabel ciphertextOutput;
	private JScrollPane encryptedM_area;
	private JTextArea encryptedMessage;
	private JPanel DecryptionPanel;
	private JLabel instruction2;
	private JSplitPane KeyGenPane;
	private JScrollPane scrollPane;
	private JScrollPane decryptedM_area;
	private JTextArea decryptedMessage;
	public static int selected_index;
	public static int flag;
	private ArrayList<BigInteger> cipher_block = new ArrayList<BigInteger>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RSA_GUI window = new RSA_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RSA_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("RSA ENCRYPTION");
		frame.setBounds(100, 100, 950, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		EncryptionPanel = new JPanel();
		splitPane_1.setLeftComponent(EncryptionPanel);
		GridBagLayout gbl_EncryptionPanel = new GridBagLayout();
		gbl_EncryptionPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_EncryptionPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_EncryptionPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_EncryptionPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		EncryptionPanel.setLayout(gbl_EncryptionPanel);

		instruction1 = new JLabel("Write the message you want to encrypt:");
		instruction1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		EncryptionPanel.add(instruction1, gbc_lblNewLabel);

		message = new JTextField();
		GridBagConstraints gbc_message = new GridBagConstraints();
		gbc_message.insets = new Insets(0, 0, 5, 0);
		gbc_message.fill = GridBagConstraints.HORIZONTAL;
		gbc_message.gridx = 1;
		gbc_message.gridy = 0;
		EncryptionPanel.add(message, gbc_message);
		message.setColumns(10);

		ciphertextOutput = new JLabel("Ciphertext:");
		ciphertextOutput.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		EncryptionPanel.add(ciphertextOutput, gbc_lblNewLabel_1);

		encryptedM_area = new JScrollPane();
		encryptedM_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_encryptedM_area = new GridBagConstraints();
		gbc_encryptedM_area.fill = GridBagConstraints.BOTH;
		gbc_encryptedM_area.gridx = 1;
		gbc_encryptedM_area.gridy = 2;
		EncryptionPanel.add(encryptedM_area, gbc_encryptedM_area);

		encryptedMessage = new JTextArea();
		encryptedM_area.setViewportView(encryptedMessage);
		encryptedMessage.setLineWrap(true);
		encryptedMessage.setWrapStyleWord(true);
		encryptedMessage.setEditable(false);

		DecryptionPanel = new JPanel();
		splitPane_1.setRightComponent(DecryptionPanel);
		GridBagLayout gbl_DecryptionPanel = new GridBagLayout();
		gbl_DecryptionPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_DecryptionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_DecryptionPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_DecryptionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		DecryptionPanel.setLayout(gbl_DecryptionPanel);

		instruction2 = new JLabel("Please press the button for decrypted the message:  ");
		instruction2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		DecryptionPanel.add(instruction2, gbc_lblNewLabel_2);

		KeyGenPane = new JSplitPane();
		KeyGenPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(KeyGenPane);

		scrollPane = new JScrollPane();
		KeyGenPane.setRightComponent(scrollPane);

		JList<String> KeyPairList = new JList<String>();
		KeyPairList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				KeyPairList.getSelectedIndex();
			}
		});
		KeyPairList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(KeyPairList);

		JButton keyGeneration = new JButton("Key Generation");
		keyGeneration.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				KeyGen keyGen = new KeyGen();
				keys.add(keyGen);
				ArrayList<String> keyStrings = new ArrayList<String>();
				int i = 1;
				for (KeyGen key : keys) {
					keyStrings.add(i + "=> Public: " + "(" + key.getE() + "," + key.getN() + ")" + "Private: ("
							+ key.getD() + "," + key.getN() + ")");
					i++;
				}

				KeyPairList.setModel(new AbstractListModel<String>() {
					String[] keys_String = keyStrings.toArray(String[]::new);

					public int getSize() {
						return keys_String.length;
					}

					public String getElementAt(int index) {
						return keys_String[index];
					}

				});

			}
		});
		keyGeneration.setFont(new Font("Times New Roman", Font.BOLD, 15));
		KeyGenPane.setLeftComponent(keyGeneration);

		JButton EncryptButton = new JButton("Encrypt");
		EncryptButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String input = message.getText();
				int index = KeyPairList.getSelectedIndex();
				selected_index = KeyPairList.getSelectedIndex();
				if (index < 0) {
					JOptionPane.showMessageDialog(EncryptButton,
							"Please Write a Message!!\nOR\nPlease Choose a Key Pair!!");
					return;
				}

				KeyGen key = keys.get(index);
				encryption = new Encryption(key.getE(), key.getN(), input);

				encryptedMessage.setText(encryption.getBase64EncodedString());
			}
		});
		EncryptButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GridBagConstraints gbc_EncryptButton = new GridBagConstraints();
		gbc_EncryptButton.insets = new Insets(0, 0, 5, 0);
		gbc_EncryptButton.gridx = 1;
		gbc_EncryptButton.gridy = 1;
		EncryptionPanel.add(EncryptButton, gbc_EncryptButton);

		JButton DecryptButton = new JButton("Decrypt");
		DecryptButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (encryption == null) {
					JOptionPane.showMessageDialog(DecryptButton, "Please Encrypt a Message FIRST!!");
					return;
				}

				int index = KeyPairList.getSelectedIndex();

				if (index != selected_index) {
					JOptionPane.showMessageDialog(DecryptButton, "Please Choose Appopriate Private Key!!");
					flag = 1;
					return;
				} else {
					KeyGen key = keys.get(index);
					if (flag == 1) {
						Decryption decryption = new Decryption(key.getD(), key.getN(), cipher_block,
								encryption.getCiphertext_stealing_or_not());
						decryptedMessage.setText(decryption.getReal_plaintext());
						Collections.swap(cipher_block, cipher_block.size() - 2, cipher_block.size() - 1);
						String last = cipher_block.get(cipher_block.size() - 1).toString();
						last = last.substring(0, last.length() - 8);
						BigInteger temp = new BigInteger(last);
						cipher_block.set(cipher_block.size() - 1, temp);
						flag = 0;
					} else {
						Decryption decryption = new Decryption(key.getD(), key.getN(),
								encryption.getCiphertexts_blocks(), encryption.getCiphertext_stealing_or_not());
						decryptedMessage.setText(decryption.getReal_plaintext());

						cipher_block = (ArrayList<BigInteger>) encryption.getCiphertexts_blocks().clone();
						Collections.swap(cipher_block, cipher_block.size() - 2, cipher_block.size() - 1);
						String last = cipher_block.get(cipher_block.size() - 1).toString();
						last = last.substring(0, last.length() - 8);
						BigInteger temp = new BigInteger(last);
						cipher_block.set(cipher_block.size() - 1, temp);
						encryption.getCiphertexts_blocks().clear();
					}
				}

			}
		});
		DecryptButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GridBagConstraints gbc_DecryptButton = new GridBagConstraints();
		gbc_DecryptButton.insets = new Insets(0, 0, 5, 5);
		gbc_DecryptButton.gridx = 1;
		gbc_DecryptButton.gridy = 0;
		DecryptionPanel.add(DecryptButton, gbc_DecryptButton);

		decryptedM_area = new JScrollPane();
		GridBagConstraints gbc_decryptedM_area = new GridBagConstraints();
		gbc_decryptedM_area.insets = new Insets(0, 0, 0, 5);
		gbc_decryptedM_area.fill = GridBagConstraints.BOTH;
		gbc_decryptedM_area.gridx = 0;
		gbc_decryptedM_area.gridy = 3;
		DecryptionPanel.add(decryptedM_area, gbc_decryptedM_area);

		decryptedMessage = new JTextArea();
		decryptedMessage.setWrapStyleWord(true);
		decryptedMessage.setLineWrap(true);
		decryptedMessage.setEditable(false);
		decryptedM_area.setViewportView(decryptedMessage);

	}
}