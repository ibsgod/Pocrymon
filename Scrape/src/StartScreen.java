import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class StartScreen extends JFrame {

	StartScreen s = this;
	GuessGame g = null;
	private JPanel contentPane;
	ArrayList<Image> images = new ArrayList<Image>();
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> types = new ArrayList<String>();
	ArrayList<String> urls = new ArrayList<String>();
	int y = 0;
	int x = 0;
	boolean loading = true;
	String loadText = "Loading";
	JLabel lblLoading;
	JButton play;
	JButton quitbtn;
	JButton btnResearch;
	boolean typed = false;
	
	public StartScreen() throws Exception 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		play = new JButton("Play");
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
		lblLoading = new JLabel(loadText);
		lblLoading.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 30));
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoading.setBounds(100, 200, 200, 200);
		contentPane.add(lblLoading);
		
		quitbtn = new JButton("Quit");
		quitbtn.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		quitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		quitbtn.setBounds(27, 290, 89, 49);
		contentPane.add(quitbtn);
		
		btnResearch = new JButton("Find");
		btnResearch.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		btnResearch.setBounds(270, 290, 89, 49);
		contentPane.add(btnResearch);
		
		JLabel lblFgh = new JLabel("");
		Random r = new Random(); 
		int e = r.nextInt(6) + 1;
		ImageIcon i = new ImageIcon(getClass().getResource("pkmeme" + e + ".jpg"));
		lblFgh.setBounds(27 + (330 - i.getIconWidth())/2, 11 + (250 - i.getIconHeight())/2, i.getIconWidth(), i.getIconHeight());
		lblFgh.setIcon(i);
		
		lblFgh.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		contentPane.add(lblFgh);
		repaint();
		btnResearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Lookup look = new Lookup(names, images, types, urls);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		contentPane.revalidate();
		play.setVisible(false);
		btnResearch.setVisible(false);
		quitbtn.setVisible(false);
		loadImages();
	}
	public void changeText(JLabel j, String text)
	{
		j.setText(text);
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
	}
	public void loadImages() throws Exception
	{
		if (names.size() == 0)
		{
			String url = "https://www.pokemondb.net/pokedex/national";
	//		String url = "https://www.pokemon.com/us/pokedex/";
			System.setProperty("http.agent", "Chrome");
			Document p = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 RuxitSynthetic/1.0 v5442443504 t38550").referrer("http://www.google.com/").get();
			Elements elements = p.getElementsByAttribute("class");
			Elements elementss = p.getElementsByTag("div");
			//System.out.println(elementss.size());
			for (int i = 0; names.size() < 721; i++)
			{
				//System.out.println(elements.get(i) + "\n");
				String s = elements.get(i).toString();
				if (s.contains("infocard-lg-data text-muted") && s.contains(".png")) 
				{
					int a = s.indexOf(".png");
					int b = s.lastIndexOf("/", a);
					String ss = s.substring(b+1, a);
					if (names.size() == 0 || !names.get(names.size()-1).equals(ss))
					{
						names.add(ss);
						typed = false;
					}
					if (s.contains("/type/") && !typed)
					{
						types.add(s.substring(s.indexOf("itype ")+6, s.indexOf("\">", s.indexOf("itype ")+6)));
						typed = true;
						//System.out.println(types.get(types.size()-1));
					}
				}
				//System.out.println(names.size());
				//System.out.println(s);
			}
			int j = 0;
			for (int i = 0; i < names.size() && j+5 < elementss.size(); i++)
			{
				//System.out.println(elementss.get(i));
				//String src = elementss.get(i).absUrl("src");
				if (!getImages(elementss.get(j+5).toString(), i))
				{
					i --;
				}
				//System.out.println(i);
				if ((i - 1) % 100 == 0)
				{
					loadText += ".";
					changeText(lblLoading, loadText);
				}
				j++;
			}
		}
		for  (int i = 0; i < names.size(); i++)
		{
			if (names.get(i).contains("-") && !(names.get(i).split("-")[1].equals("mime") || names.get(i).split("-")[1].equals("m") || names.get(i).split("-")[1].equals("f") || names.get(i).split("-")[1].equals("oh")))
		 	{
		 		names.set(i, names.get(i).split("-")[0]);
		 	}
		}
		loading = false;
		lblLoading.setVisible(false);
		play.setVisible(true);
		quitbtn.setVisible(true);
		btnResearch.setVisible(true);
	}
	public boolean getImages(String src, int i) throws Exception
	{
		if (src.length() == 0)
		{
			//System.out.println("bruh");
			return false;
		}
		if (!src.contains("sprite"))
		{
			return false;
		}
		src = src.split("data-src=\"")[1].split("\" data-alt")[0];
		//System.out.println(src);
		  String folder = null;
		  
	        //Exctract the name of the image from the src attribute
	        int indexname = src.lastIndexOf("/");
	 
	        if (indexname == src.length()) {
	            src = src.substring(1, indexname);
	        }
	 
	        indexname = src.lastIndexOf("/");
	        String name = src.substring(indexname, src.length());
	        //images.add((new ImageIcon(Scraper.class.getResource(name))).getImage());
	       // System.out.print(a);
	        
	        //System.out.println(name + " " + src);
	        //Open a URL Stream
	        URL url = new URL(src);
	        /*InputStream in = url.openStream();
	        Path resourceDirectory = Paths.get("src");
	        if (Scraper.class.getResource(name) != null)
	        {
	        	//System.out.println("bruh?");
	        	return true;
	        }*/
	        
	        String naga = name.substring(1, name.length()-4);
	       
	        if (names.get(i).equalsIgnoreCase(naga))
	        {
	        	images.add(new ImageIcon(url).getImage()); 
	        	urls.add(src);
	        	//System.out.println(naga);
	        }
	        else
	        {
	        	return false;
	        }
	      /*  OutputStream out = new BufferedOutputStream(new FileOutputStream(resourceDirectory + name));
	        
	        for (int b; (b = in.read()) != -1;) {
	            out.write(b);
	        }
	       // Thread.sleep(1000);
	        
	        out.close();
	        in.close();*/
	        return true;
		
	}
}
