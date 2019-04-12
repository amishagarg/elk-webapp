// Java Program to create a text editor using java 
package io.elk.service;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

class editor extends JFrame implements ActionListener {
	// Text component
	JTextArea t;
	// Frame
	JFrame f;
	JScrollPane scroll;
	editor() {
		// Create a frame
		f = new JFrame("editor");
		// t = new JTextArea(50, 50);
		t = new JTextArea();
		scroll = new JScrollPane(t);
		f.add(scroll);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			// Set metl look and feel
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// Set theme to ocean
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());
		} catch (Exception e) {
		}
		// Create a menubar
		JMenuBar mb = new JMenuBar();
		// Create amenu for menu
		JMenu m1 = new JMenu("File");
		// Create menu items
		JMenuItem mi3 = new JMenuItem("Save");
		mi3.addActionListener(this);
		m1.add(mi3);
		JMenuItem mc = new JMenuItem("close");
		mc.addActionListener(this);
		mb.add(m1);
		mb.add(mc);
		f.setJMenuBar(mb);
		f.add(t);
		f.setSize(500, 500);
		f.show();
		File fi = new File("D:\\Software\\F\\pathfilebeat.yml");

		try {
			// String
			String s1 = "", sl = "";

			// File reader
			FileReader fr = new FileReader(fi);

			// Buffered reader
			BufferedReader br = new BufferedReader(fr);

			// Initilize sl
			sl = br.readLine();

			// Take the input from the file
			while ((s1 = br.readLine()) != null) {
				sl = sl + "\n" + s1;
			}

			// Set the text
			t.setText(sl);
		} catch (Exception evt) {
			JOptionPane.showMessageDialog(f, evt.getMessage());
		}
	}

	// If a button is pressed
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		if (s.equals("Save")) {
			// Create an object of JFileChooser class
			JFileChooser j = new JFileChooser("f:");

			// Invoke the showsSaveDialog function to show the save dialog
			int r = j.showSaveDialog(null);

			if (r == JFileChooser.APPROVE_OPTION) {

				// Set the label to the path of the selected directory
				File fi = new File(j.getSelectedFile().getAbsolutePath());

				try {
					// Create a file writer
					FileWriter wr = new FileWriter(fi, false);

					// Create buffered writer to write
					BufferedWriter w = new BufferedWriter(wr);

					// Write
					w.write(t.getText());

					w.flush();
					w.close();
				} catch (Exception evt) {
					JOptionPane.showMessageDialog(f, evt.getMessage());
				}
			}
			// If the user cancelled the operation
			else
				JOptionPane.showMessageDialog(f, "the user cancelled the operation");
		}

		else if (s.equals("close")) {
			f.setVisible(false);
		}
	}

	// Main class
	public static void main(String args[]) {
		editor e = new editor();
	}
}
