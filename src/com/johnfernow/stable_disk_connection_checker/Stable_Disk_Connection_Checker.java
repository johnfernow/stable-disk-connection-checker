package com.johnfernow.stable_disk_connection_checker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;

public class Stable_Disk_Connection_Checker {
	public String selectedDrive;
	public String selectedLength;

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
		
		JButton btnStartTest = new JButton("Start test");
		btnStartTest.setBounds(171, 224, 117, 29);
		btnStartTest.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try {
					testDriveConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		frmStableDiskConnection.getContentPane().add(btnStartTest);
		
		JLabel lblLengthOfTest = new JLabel("Choose length of test.");
		lblLengthOfTest.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblLengthOfTest.setBounds(28, 97, 212, 29);
		frmStableDiskConnection.getContentPane().add(lblLengthOfTest);
		
		String[] lengthOptions = { "30 seconds", "2 minutes", "10 minutes", "30 minutes", "1 hour", "3 hours", "1 day", "3 days", "1 week" };
		JComboBox comboBoxLengthOfTest = new JComboBox(lengthOptions);
		comboBoxLengthOfTest.setBounds(38, 127, 395, 50);
		comboBoxLengthOfTest.setSelectedIndex(0);
		selectedLength = String.valueOf(comboBoxLengthOfTest.getSelectedItem());
		comboBoxLengthOfTest.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        selectedLength = String.valueOf(comboBoxLengthOfTest.getSelectedItem());
		    }
		});
		frmStableDiskConnection.getContentPane().add(comboBoxLengthOfTest);
		
		JButton btnChooseDrive = new JButton("Choose a drive to test.");
		btnChooseDrive.setHorizontalAlignment(SwingConstants.LEFT);
		btnChooseDrive.setBounds(28, 37, 174, 29);
		btnChooseDrive.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser chooser = new JFileChooser();
		    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    	int returnVal = chooser.showOpenDialog(chooser);
		    	if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	   selectedDrive = chooser.getSelectedFile().getAbsolutePath();
		    	}
		    }
		});
		frmStableDiskConnection.getContentPane().add(btnChooseDrive);
	}
	
	private void testDriveConnection() throws IOException 
	{
		// TODO: WARNING, may fail to write to some directories, should implement logic to handle that
		String fileContent = "Testing drive connection by repeatedly writing to it for a set period of time.";
		
		long timeToRun = timeToSeconds(selectedLength);
		long startTime = Instant.now().getEpochSecond();
		boolean testSuccessful = true;
		while (Instant.now().getEpochSecond() - startTime < timeToRun) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(selectedDrive + File.separator + "temp-testing.txt"));
			    writer.write(fileContent);
			    writer.close();
			    Thread.sleep(5000);
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "ERROR: lost connection to " + selectedDrive + ". The connection does not appear to be stable.");
				testSuccessful = false;
				break;
			}
		}
		if (testSuccessful) {
			JOptionPane.showMessageDialog(null, "Drive " + selectedDrive + " passed the stability test for " + selectedLength);
		}
	}
	
	private long timeToSeconds(String s) 
	{
		switch(s) {
		  case "30 seconds":
		    return 30;
		  case "2 minutes":
		    return 120;
		  case "10 minutes":
			    return 600;
		  case "30 minutes":
			    return 1800;
		  case "1 hour":
			    return 3600;
		  case "3 hours":
			    return 10800;
		  case "1 day":
			    return 86400;
		  case "3 days":
			    return 259200;
		  case "1 week":
			    return 604800;
		  default:
		    return 0;
		}	
	}
}
