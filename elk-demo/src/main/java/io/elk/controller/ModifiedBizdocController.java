package io.elk.controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.elk.entities.ModifiedBizdoc;
import io.elk.service.ModifiedBizdocService;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

@Controller
@RequestMapping


public class ModifiedBizdocController extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ModifiedBizdocController.class);
	// Text component
				JTextArea t;
				// Frame
				JFrame f;
				JScrollPane scroll;
	ModifiedBizdocService modifiedBizdocService;

	@Autowired
	public ModifiedBizdocController(ModifiedBizdocService modifiedBizdocService) {
		this.modifiedBizdocService = modifiedBizdocService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ModifiedBizdoc> getAll() {
		try {
			
			 /* List<ModifiedBizdoc> list= new ArrayList<ModifiedBizdoc>();
			 * list=modifiedBizdocService.findAll(); System.out.println(list+"  JJJJJJ   ");
			 * 
			 * List<ModifiedBizdoc> list2= new ArrayList<ModifiedBizdoc>();
			 * list2=modifiedBizdocService.scanForLatest();
			 * System.out.println(list2+" KKKKKK ");*/
			 

			// return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// The Other app starts here

	private String upload_folder = "D:\\Software\\F\\logs\\";

	@GetMapping("/")
	public String index() {
		return "upload.html";
	}

	@GetMapping("/pathpage")
	public String pathpage() {
		return "pathpage.html";
	}

	@PostMapping("/browse")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/uploadStatus";
		}
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(upload_folder + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}

	@PostMapping("/upload")
	public String addPaths(@RequestParam("DynamicTextBox") String arr_path, RedirectAttributes redirectAttributes)
			throws IOException {

		File fileToBeModified = new File("D:\\Software\\F\\pathfilebeat.yml");
		BufferedReader br = new BufferedReader(new FileReader(fileToBeModified));

		FileWriter writer = null;
		String st = "";
		String stnew = "";
		System.out.println("======================");
		while ((st = br.readLine()) != null) {
			stnew = stnew + st + System.lineSeparator();
		}
		// System.out.println(stnew);
		// System.out.println("--------------");
		String[] values = arr_path.split(",");

		for (int i = 0; i < values.length; i++) {

			System.out.println(values[i]);
		}
		String str = "paths:" + System.lineSeparator();

		for (int i = 0; i < values.length; i++) {
			str = str + "  - " + values[i] + "//.*log" + System.lineSeparator();
			str = str.replace("\\", "/");
		}

		System.out.println(str);
		// Replacing oldString with newString in the oldContent
		String newContent = stnew.replaceAll("paths:", str);

		// Rewriting the input text file with newContent

		writer = new FileWriter(fileToBeModified);
		writer.write(newContent);
		try {
			// Closing the resources

			br.close();

			writer.close();
			redirectAttributes.addFlashAttribute("message", "You successfully updated log path location.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/pup";
	}

	@GetMapping("/pathupload")
	public String pathupload() {
		return "pathupload";
	}
	
	@PostMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	@GetMapping("/uploadStatus")
	public String uploadStatusGet() {
		return "uploadStatus";
	}

	// trigger buttons start here
	@GetMapping("/FilebeatTrigger")
	public String FilebeatTrigger() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\batchfileF.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/FilebeatTriggerPath")
	public String FilebeatTriggerPath() {
		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\batchpathF.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/ElkTrigger")
	public String ElkTrigger() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkstart.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/FilebeatKiller")
	public String FilebeatKiller() {

		try {

			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\filebeatkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/ElasticKiller")
	public String ElasticKiller() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@PostMapping("/configFB")
	public String configFB(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/settings";
		} else {
			try {

				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				Path path = Paths.get("D:\\Software\\F\\amisha.yml");
				Files.write(path, bytes);
				redirectAttributes.addFlashAttribute("message",
						"You successfully updated '" + file.getOriginalFilename() + "'");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/settings";
	}

	@GetMapping("/settings")
	public String settings() {
		return "settings";
	}
	////////////////////////////////////////////////////

	@GetMapping("/edit")
	public String editFB(@RequestParam("type") String type) throws IOException {
		boolean headless = true;
			System.out.println("meeeeeeeeeeeeeeeeeeeeeee"+type);
				f = new JFrame("editor");
				// t = new JTextArea(50, 50);
				t = new JTextArea();
				scroll = new JScrollPane(t);
				f.add(scroll);
				f.pack();
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				try {

					System.out.println("tryyyyyyyyyyyyyyyyyye"+type);
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					MetalLookAndFeel.setCurrentTheme(new OceanTheme());
				} catch (Exception e) {
				}
				JMenuBar mb = new JMenuBar();
				JMenu m1 = new JMenu("File");
				// Create menu items
				JMenuItem mi3 = new JMenuItem("Save");
				mi3.addActionListener((ActionListener) this);
				m1.add(mi3);
				JMenuItem mc = new JMenuItem("close");
				mc.addActionListener(this);
				mb.add(m1);
				mb.add(mc);
				f.setJMenuBar(mb);
				f.add(t);
				f.setSize(500, 500);
				f.show();
				File fi = null;
		if(type.equalsIgnoreCase("ES"))
		{
			fi = new File("D:\\Software\\E\\config\\elasticsearch.yml");

		}
		else if(type.equalsIgnoreCase("FB"))
		{
			fi = new File("D:\\Software\\F\\filefilebeat.yml");

		}
		if(type.equalsIgnoreCase("KB"))
		{
			fi = new File("D:\\Software\\K\\config\\kibana.yml");

		}
		if(type.equalsIgnoreCase("LS"))
		{
			fi = new File("D:\\Software\\L\\bin\\logstash.conf");

		}
		if(type.equalsIgnoreCase("KF"))
		{
			fi = new File("D:\\Software\\F\\pathfilebeat.yml");

		}
		
		try {
			String s1 = "", sl = "";
			FileReader fr = new FileReader(fi);
			BufferedReader br = new BufferedReader(fr);
			sl = br.readLine();
			while ((s1 = br.readLine()) != null) {
				sl = sl + "\n" + s1;
			}
			t.setText(sl);
		} catch (Exception evt) {
			JOptionPane.showMessageDialog(f, evt.getMessage());
		}
		return "redirect:/settings";}
	
	@PostMapping("/configES")
	public String configES() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/editES")
	
	public String editES() {
		
		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@PostMapping("/configLS")
	public String configLS() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/editLS")
	public String editLS() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@PostMapping("/configKB")
	public String configKB() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/editKB")
	public String editKB() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@PostMapping("/configKF")
	public String configKF() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@GetMapping("/editKF")
	public String editKF() {

		try {
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"D:\\elk-demo\\batchfiles\\elkkill.bat\"");
		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong. You're a horrible person, buddy. ");
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String s = e.getActionCommand();

		if (s.equals("Save")) {
			JFileChooser j = new JFileChooser("f:");
			int r = j.showSaveDialog(null);

			if (r == JFileChooser.APPROVE_OPTION) {

				// Set the label to the path of the selected directory
				File fi = new File(j.getSelectedFile().getAbsolutePath());

				try {
					FileWriter wr = new FileWriter(fi, false);
					BufferedWriter w = new BufferedWriter(wr);
					w.write(t.getText());w.flush();
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
	
		
	}}
