import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


public class Lookup extends JPanel 
{

	JFrame f;
	private JPanel contentPane;
	private JTextField textField;
	ArrayList<String> names;
	ArrayList<String> types;
	ArrayList<Image> images;
	ArrayList<String> urls;
	int currentIndex = -1;
	boolean typed = false;
	JLabel weightlbl = new JLabel();
	JLabel heightlbl = new JLabel();
	JLabel namelbl = new JLabel();
	String trueName;
	Rectangle r = new Rectangle(100, 190, 120, 120);
	int alpha = 255;
	FadeThread thready = new FadeThread();
	boolean drawing = false;
	DecimalFormat format = new DecimalFormat("000");
	public Lookup(ArrayList<String> names, ArrayList<Image> images, ArrayList<String> types, ArrayList<String> urls) 
	{
		contentPane = this;
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 400, 400);
		f.setVisible(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		f.setContentPane(contentPane);
		contentPane.setLayout(null);
		this.names = names;
		this.images = images;
		this.types = types;
		namelbl.setFont(new Font("Microsoft Yahei Light", Font.BOLD, 20));
		weightlbl.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 17));
		heightlbl.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 17));
		namelbl.setBounds(170, 120, 200, 40);
		weightlbl.setBounds(namelbl.getX(), namelbl.getY()+50, 200, 30);
		
		JButton button = new JButton(">");
		button.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 17));
		button.setBounds(328, 69, 46, 35);
		add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentIndex < names.size()-1)
				{
					currentIndex ++;
					try {
						showStats();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
				
			}
		});
		heightlbl.setBounds(namelbl.getX(), weightlbl.getY()+50, 200, 30);
		contentPane.add(heightlbl);
		contentPane.add(namelbl);
		contentPane.add(weightlbl);
		textField = new JTextField("Enter Pokemon name");
		textField.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e)
			{
				if (!typed)
				{
					textField.setText("");
					typed = true;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				String tt = textField.getText();
				if (e.getKeyCode() == KeyEvent.VK_ENTER && tt.length() > 1)
				{
					for (int i = 0; i < names.size(); i++)
					{
						if (tt.equalsIgnoreCase(names.get(i).replace('.', '-')) || tt.equalsIgnoreCase(names.get(i)) || tt.equalsIgnoreCase(names.get(i).replace('.', ' ')))
						{
							currentIndex = i;
							try {
								showStats();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							repaint();
							textField.setText("");
							break;
						}
						
					}
					
				}
			}
		});
		setLayout(null);
		textField.setBounds(104, 15, 176, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				StartScreen screen = null;
				try {
					screen = new StartScreen();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				screen.names = names;
				screen.images = images;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.dispose();
			}
		});
		btnNewButton.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 19));
		btnNewButton.setBounds(10, 288, 115, 49);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentIndex > 0)
				{
					currentIndex --;
					try {
						showStats();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 17));
		btnNewButton_1.setBounds(10, 69, 46, 35);
		add(btnNewButton_1);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (currentIndex != -1)
				{
					String home = System.getProperty("user.home");
					
					String src = urls.get(currentIndex);
					try 
					{
						URL url = new URL(urls.get(currentIndex));
						InputStream in = url.openStream();
						 int indexname = src.lastIndexOf("/");
						 
					        if (indexname == src.length()) {
					            src = src.substring(1, indexname);
					        }
					 
					        indexname = src.lastIndexOf("/");
					        String name = src.substring(indexname, src.length());
						OutputStream out = new BufferedOutputStream (new FileOutputStream(home + "/Desktop" + name));
						for (int b; (b = in.read()) != -1;) {
				            out.write(b);
				        }
						in.close();
						out.close();
						JOptionPane.showMessageDialog(contentPane, name.substring(1) + " added to desktop. Awesome!");
					}
				    catch (Exception e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnDownload.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 14));
		btnDownload.setBounds(259, 288, 115, 49);
		add(btnDownload);
		
		JButton btnCry = new JButton("Cry");
		btnCry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				BasicPlayer player = new BasicPlayer();
			    try {
					player.open(new URL("https://play.pokemonshowdown.com/audio/cries/" + names.get(currentIndex).replace("-",  "") + ".mp3"));
					player.play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			}
		});
		btnCry.setFont(new Font("Microsoft Yahei Light", Font.PLAIN, 30));
		btnCry.setBounds(129, 276, 126, 73);
		add(btnCry);
	}
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		try {
			paintTheBitches(g);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void paintTheBitches(Graphics g) throws Exception
	{
		if (currentIndex != -1)
		{
			//g.fillRect(r.x, r.y, r.width, r.height);
			g.drawImage(images.get(currentIndex), 40, 110, null);
			/*Color bg = getBackground();
			g.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));
			if (!drawing)
			{
				thready.l = this;
				drawing = true;
				thready.start();
			}*/
			
		}
		
	}
	 public void showStats() throws Exception
	 {
		 trueName = Character.toUpperCase(names.get(currentIndex).charAt(0)) + names.get(currentIndex).substring(1);
		 if (currentIndex == -1)
		 {
			 weightlbl.setVisible(false);
			 heightlbl.setVisible(false);
		 }
		 else
		 {
			
		 	String url = "https://www.pokemondb.net/pokedex/" + names.get(currentIndex);
			System.setProperty("http.agent", "Chrome");
			Document p = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 RuxitSynthetic/1.0 v5442443504 t38550").referrer("http://www.google.com/").get();
			Elements e = p.getElementsByTag("td");
			String weight = "";
			String height = "";
			for (int i = 0; i < e.size(); i++)
			{	
				if (e.get(i).toString().contains("kg"))
				{
					weight = e.get(i).toString().substring(e.get(i).toString().indexOf(">")+1, e.get(i).toString().indexOf("&"));
				}
				if (e.get(i).toString().contains("″"))
				{
					height = e.get(i).toString().substring(e.get(i).toString().indexOf(">")+1, e.get(i).toString().indexOf("&"));
				}
				
			}
			//System.out.println(height + " " + trueName);
			namelbl.setText(trueName + " #" + format.format(currentIndex+1));
			namelbl.setVisible(true);
			weightlbl.setText("Weight: " + weight + " kg");
			weightlbl.setVisible(true);
			heightlbl.setText("Height: " + height + " m");
			heightlbl.setVisible(true);
			setBackground();
		 }

	 }
	 public void setBackground()
	 {
		 if (currentIndex != -1)
		 {
			 //System.out.println(types.get(currentIndex));
			 namelbl.setForeground(Color.black);
	 		weightlbl.setForeground(Color.black);
	 		heightlbl.setForeground(Color.black);
			 switch(types.get(currentIndex))
			 {
			 	case "normal":
			 		contentPane.setBackground(null);
			 		break;
			 	case "fire":
			 		contentPane.setBackground(new Color(255, 96, 0));
			 		break;
			 	case "water":
			 		contentPane.setBackground(new Color(24, 123, 205));
			 		break;
			 	case "grass":
			 		contentPane.setBackground(new Color(61, 201, 109));
			 		break;
			 	case "rock":
			 		contentPane.setBackground(new Color(65, 43, 21));
			 		break;
			 	case "fighting":
			 		contentPane.setBackground(new Color(209, 34, 38));
			 		break;
			 	case "electric":
			 		contentPane.setBackground(new Color(252, 247, 135));
			 		break;
			 	case "dark":
			 		contentPane.setBackground(new Color(40, 40, 40));
			 		namelbl.setForeground(Color.white);
			 		weightlbl.setForeground(Color.white);
			 		heightlbl.setForeground(Color.white);
			 		break;
			 	case "ghost":
			 		contentPane.setBackground(new Color(80, 0, 80));
			 		namelbl.setForeground(Color.white);
			 		weightlbl.setForeground(Color.white);
			 		heightlbl.setForeground(Color.white);
			 		break;
			 	case "bug":
			 		contentPane.setBackground(new Color(47, 168, 89));
			 		break;
			 	case "psychic":
			 		contentPane.setBackground(new Color(252, 114, 227));
			 		break;
			 	case "steel":
			 		contentPane.setBackground(new Color(150, 150, 150));
			 		break;	
			 	case "poison":
			 		contentPane.setBackground(new Color(174, 36, 187));
			 		break;
			 	case "ice":
			 		contentPane.setBackground(new Color(113, 203, 255));
			 		break;
			 	case "dragon":
			 		contentPane.setBackground(new Color(62, 51, 162));
			 		break;	
			 	case "ground":
			 		contentPane.setBackground(new Color(209, 166, 14));
			 		break;	
			 	case "flying":
			 		contentPane.setBackground(new Color(166, 183, 255));
			 		break;	
			 	case "fairy":
			 		contentPane.setBackground(new Color(255, 168, 253));
			 		break;	
			 	
			 }
		 }
	 }

}
