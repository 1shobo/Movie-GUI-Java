package gui;
import Internationalization.*;
import logging.AssignmentLogger;
//import logging.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MovieGui extends JFrame implements ActionListener{

	//TextField
	JTextField inputField = new JTextField(10);

	//Buttons
	JButton soundButton = new JButton("Play Movie Sound");
	JButton outputButton = new JButton("Genre Information");
	JButton translateButton = new JButton("Translate"); //butt_Tag
	

	//Panels
	JPanel panel = new JPanel();
	JPanel displayPanel = new JPanel();

	//Labels
	JLabel displayMovieLabel = new JLabel("");
	JLabel descLabel = new JLabel("");
	
	//Images
	ImageIcon cowboyImage = new ImageIcon("cowboy.jpg"); //img_Tag
	ImageIcon comedyImage = new ImageIcon("comedy.jpg");
	ImageIcon horrorImage = new ImageIcon("horror.jpg");
	
	//Locals for two languages
	Locale germanLocale = new Locale("de", "DE");
	Locale englishLocale = new Locale("en", "EN");
	Locale currentLocale = new Locale("en", "EN");


	public MovieGui() {
		Container contentPane = getContentPane();

		soundButton.addActionListener(this);
		outputButton.addActionListener((ActionListener) this);
		
		//Adding elements to panel
		panel.add(translateButton);
		panel.add(inputField);
		panel.add(outputButton);

		//Adding elements to display panel
		displayPanel.add(displayMovieLabel);
		displayPanel.add(soundButton);
		displayPanel.add(descLabel);
		
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(displayPanel, BorderLayout.CENTER);

		soundButton.setVisible(false);
		setSize(700,400);
		setVisible(true);
	}


	public static void main(String[] args) {
		MovieGui myFrame = new MovieGui();
		
	}



	public void actionPerformed(ActionEvent e) {

		String genre = inputField.getText();

		String ucowboy = "Cowboy";
		String lcowboy = "cowboy";

		String ucomedy = "Comedy";
		String lcomedy = "comedy";

		String uhorror = "Horror";
		String lhorror = "horror";


		try {

			if (genre.equals(ucowboy) || genre.equals(lcowboy)) {
				
				Thread.sleep(500);
				displayMovieLabel.setIcon(cowboyImage);
				soundButton.setVisible(true);
				
				File cowboyFile = new File("cowboy.wav");
				cowboyFile.toURI(); //Convert the file to a URL to use as URL
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(cowboyFile.toURI().toURL());  
				Clip clip = AudioSystem.getClip();
				
				if(e.getSource ()==soundButton) {
					clip.open(audioIn);
					clip.start();
				}
				
				descLabel.setText("Cowboy movies are  movies based on when cowboys used to ride horses and have duel with their guns.");
				
			}
			
			else if(genre.equals(ucomedy) || genre.equals(lcomedy)) {
				Thread.sleep(500);
				displayMovieLabel.setIcon(comedyImage);
				soundButton.setVisible(true);
				
				File comedyFile = new File("comedy.wav");
				comedyFile.toURI(); //Convert the file to a URL to use as URL
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(comedyFile.toURI().toURL());  
				Clip clip = AudioSystem.getClip();
				
				if(e.getSource ()==soundButton) {
					clip.open(audioIn);
					clip.start();
				}
				
				descLabel.setText("Comedy movies will make you laugh throughout");
			}
			
			else if(genre.equals(uhorror) || genre.equals(lhorror)) {
				Thread.sleep(500);
				displayMovieLabel.setIcon(horrorImage);
				soundButton.setVisible(true);
				
				File horrorFile = new File("horror.wav");
				horrorFile.toURI(); //Convert the file to a URL to use as URL
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(horrorFile.toURI().toURL());  
				Clip clip = AudioSystem.getClip();
				
				if(e.getSource ()==soundButton) {
					clip.open(audioIn);
					clip.start();
				}
				
				descLabel.setText("Horror movies are made to keep you on the edge of your chair");
			}

		}
		 catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {

		}



	}


}
