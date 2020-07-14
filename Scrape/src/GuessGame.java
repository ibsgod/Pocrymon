import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class GuessGame extends JFrame 
{
	ArrayList<Integer> ques = new ArrayList<Integer>();
	int curr;
	private JPanel contentPane;
	int currentIndex = -1;
	int question = 0;
	int score = 0;
	boolean guessed = false;
	int pos = -1;
	StartScreen s;
	JButton next; 
	JButton[] buttons = new JButton[4];
	JLabel scorelbl;
	BasicPlayer player = new BasicPlayer();
	public GuessGame(StartScreen s)
	{
		this.s = s;
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					player.play();
				} catch (BasicPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(157, 26, 120, 120);
		contentPane.add(btnNewButton);
		btnNewButton.setIcon(new ImageIcon(getClass().getResource("25b6.png")));
		
		JButton button_0 = new JButton("");
		button_0.setBounds(20, 187, 90, 90);
		contentPane.add(button_0);
		
		JButton button_1 = new JButton("");
		button_1.setBounds(120, 187, 90, 90);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setBounds(220, 187, 90, 90);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.setBounds(320, 186, 90, 90);
		contentPane.add(button_3);
		buttons[0] = button_0;
		buttons[1] = button_1;
		buttons[2] = button_2;
		buttons[3] = button_3;
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (question < 10)
				{
					newQuestion();
				}
				else
				{
					EndScreen ee = new EndScreen(score);
					ee.setVisible(true);
					ee.s = s;
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});
		next.setBounds(299, 288, 103, 43);
		contentPane.add(next);
		
		scorelbl = new JLabel("Score: " + score + " / " + question);
		scorelbl.setBounds(21, 288, 160, 43);
		contentPane.add(scorelbl);
		scorelbl.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 19));
		buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				curr = 0;
				guess();
			}
		});
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				curr = 1;
				guess();
			}
		});
		buttons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				curr = 2;
				guess();
			}
		});
		buttons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				curr = 3;
				guess();
			}
		});
		newQuestion();
	}
	public void newQuestion()
	{
		if (pos >= 0)
		{
			buttons[0].setBorder(new JButton().getBorder()); 
			buttons[1].setBorder(new JButton().getBorder()); 
			buttons[2].setBorder(new JButton().getBorder()); 
			buttons[3].setBorder(new JButton().getBorder()); 
		}
		guessed = false;
		question ++;
		next.setVisible(false);
		Random rand = new Random();
		do
		{
			currentIndex = rand.nextInt(s.images.size());
		}
		while (ques.contains(currentIndex));
		ques.add(currentIndex);
		pos = rand.nextInt(4);
		System.out.println(s.images.get(currentIndex));
		buttons[pos].setIcon(new ImageIcon(s.images.get(currentIndex)));
		ArrayList<Integer> opts = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++)
		{
			if (i == pos)
			{
				continue;
			}
			int randpok = rand.nextInt(s.images.size());
			if (randpok == currentIndex || opts.contains(randpok))
			{
				i --;
				continue;
			}
			opts.add(randpok);
			buttons[i].setIcon(new ImageIcon(s.images.get(randpok)));
		}
		try 
		{
			player.open(new URL("https://play.pokemonshowdown.com/audio/cries/" + s.names.get(currentIndex).replace("-",  "") + ".mp3"));
			player.play();
		} catch (Exception e) {}
		
	}
	public void guess()
	{
		if (!guessed)
		{
			System.out.println(pos);
			BasicPlayer player = new BasicPlayer();
			if (curr == pos)
			{
				score ++;
			    try {
					player.open(getClass().getResource("correct.mp3"));
					player.play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					player.open(getClass().getResource("wrong.mp3"));
					player.play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buttons[curr].setBorder(BorderFactory.createLineBorder(Color.red, 2));
			}
			scorelbl.setText("Score: " + score + " / " + question);
			buttons[pos].setBorder(BorderFactory.createLineBorder(Color.green, 2));
			next.setVisible(true);
			if (question == 10)
			{
				next.setText("Finish!");
			}
		}
		guessed = true;
	}
}
