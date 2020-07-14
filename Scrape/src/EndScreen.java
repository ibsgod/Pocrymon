import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javazoom.jlgui.basicplayer.BasicPlayer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndScreen extends JFrame {

	private JPanel contentPane;
	StartScreen s;
	GuessGame g;
	int score;
	public EndScreen(int score) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		BasicPlayer player = new BasicPlayer();
	    try {
			player.open(getClass().getResource("win.mp3"));
			player.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton play = new JButton("Again!");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g = new GuessGame(s);
				g.setVisible(true);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		play.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 30));
		play.setBounds(126, 280, 134, 70);
		contentPane.add(play);
		
		JButton quitbtn = new JButton("Menu");
		quitbtn.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		quitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				StartScreen ss = null;
				try {
					ss = new StartScreen();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ss.names = s.names;
				ss.images = s.images;
				ss.urls = s.urls;
				ss.types = s.types;
				g.setVisible(true);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		quitbtn.setBounds(27, 290, 89, 49);
		contentPane.add(quitbtn);
		
		JButton btnResearch = new JButton("Find");
		btnResearch.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		btnResearch.setBounds(270, 290, 89, 49);
		contentPane.add(btnResearch);
		
		JLabel lblNewLabel = new JLabel("You scored " + score + "/10!");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(13, 0, 357, 59);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(67, 70, 250, 200);
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("picasso.png")));
		contentPane.add(lblNewLabel_1);
		
		btnResearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Lookup look = new Lookup(s.names, s.images, s.types, s.urls);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
	}

}
