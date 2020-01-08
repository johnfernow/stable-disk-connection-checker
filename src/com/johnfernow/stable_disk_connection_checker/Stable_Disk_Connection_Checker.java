package com.johnfernow.stable_disk_connection_checker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class Stable_Disk_Connection_Checker {

	private JFrame frmStableDiskConnection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stable_Disk_Connection_Checker window = new Stable_Disk_Connection_Checker();
					window.frmStableDiskConnection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stable_Disk_Connection_Checker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStableDiskConnection = new JFrame();
		frmStableDiskConnection.setTitle("Stable Disk Connection Checker");
		frmStableDiskConnection.setBounds(100, 100, 450, 300);
		frmStableDiskConnection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStableDiskConnection.getContentPane().setLayout(null);
		
		JLabel lblInstructions = new JLabel("Choose a drive to test.");
		lblInstructions.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblInstructions.setBounds(28, 21, 212, 16);
		frmStableDiskConnection.getContentPane().add(lblInstructions);
		
		JComboBox comboBoxDriveSelector = new JComboBox(getDrivesList().toArray());
		comboBoxDriveSelector.setMaximumRowCount(100);
		comboBoxDriveSelector.setBounds(38, 49, 395, 50);
		frmStableDiskConnection.getContentPane().add(comboBoxDriveSelector);
		
		JButton btnStartTest = new JButton("Start test");
		btnStartTest.setBounds(171, 224, 117, 29);
		frmStableDiskConnection.getContentPane().add(btnStartTest);
		
		JLabel lblLengthOfTest = new JLabel("Choose length of test.");
		lblLengthOfTest.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblLengthOfTest.setBounds(28, 111, 212, 29);
		frmStableDiskConnection.getContentPane().add(lblLengthOfTest);
		
		JComboBox comboBoxLengthOfTest = new JComboBox();
		comboBoxLengthOfTest.setBounds(38, 139, 395, 50);
		frmStableDiskConnection.getContentPane().add(comboBoxLengthOfTest);
	}
	
	/**
	 * Fetches a list of all drives and their corresponding drive type 
	 * @return 
	 */
	private List<String> getDrivesList() {
		// TODO: may need to implement this code for support for Windows - https://stackoverflow.com/a/15608620/11870521
		
		List<String> availableDrives = new ArrayList<String>();
		
		
		for (FileStore store : FileSystems.getDefault().getFileStores()) {
			// for debugging drives list, check https://stackoverflow.com/a/26650362/11870521
			availableDrives.add(store.toString());
	    }
		return availableDrives;
	}
}
