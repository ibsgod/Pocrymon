import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Screen extends JPanel 
{
	ArrayList<Image> images = new ArrayList<Image>();
	ArrayList<String> names = new ArrayList<String>();
	int y = 0;
	int x = 0;
	JFrame f;
	public Screen() throws Exception
	{
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(0, 0, 121, 121);
		f.setPreferredSize(new Dimension(121, 121));
		f.setResizable(true);
		f.setVisible(true);
		f.setContentPane(this);
		dfo();
	}
	public void dfo() throws Exception
	{
		String url = "https://www.pokemondb.net/pokedex/national";
//		String url = "https://www.pokemon.com/us/pokedex/";
		System.setProperty("http.agent", "Chrome");
		Document p = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 RuxitSynthetic/1.0 v5442443504 t38550").referrer("http://www.google.com/").get();
		
		//String selector = "rg_i Q4LuWd tx8vtf";
		String selector = "sMi44c lNHeqe";
		Elements elements = p.getElementsByAttribute("class");
		Elements elementss = p.getElementsByTag("div");
		//System.out.println(elementss.size());
		 
		for (int i = 0; i < elements.size(); i++)
		{
			String s = elements.get(i).toString();
			if (s.contains("infocard-lg-data text-muted") && s.contains(".png")) 
			{
				int a = s.indexOf(".png");
				int b = s.lastIndexOf("/", a);
				String ss = s.substring(b+1, a);
				if (names.size() == 0 || !names.get(names.size()-1).equals(ss))
				{
					names.add(ss);
				}
			}
			//System.out.println(names.size());
			//System.out.println(s);
		}
		for (int i = 4; i <= 70; i++)
		{
			//System.out.println(elementss.get(i));
			//String src = elementss.get(i).absUrl("src");
			getImages(elementss.get(i).toString());
			f.setSize(Math.min(images.size(), 11) * 120 + 5, Math.min((images.size() / 11 + 1), 6) * 120 + 20);
			f.repaint();
			//System.out.println(i);
		}
		f.repaint();
	}
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		try 
		{
			paintTheBitches(g);
		} 
		catch (Exception e) {}
	}
	public void paintTheBitches(Graphics g) throws Exception
	{
		int x = 0;
		int y = 0;
		for (int i = 0; i < images.size(); i++)
		{
			if (images.get(i) != null)
			{
				g.drawImage(images.get(i), x * 120, y * 120, null);
				x++;
			}
			if (i % 11 == 0 && i > 0)
			{
				y ++;
				x = 0;
			}

		}
		
	}
	public boolean getImages(String src) throws Exception
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
	        InputStream in = url.openStream();
	        Path resourceDirectory = Paths.get("src");
	        if (Scraper.class.getResource(name) != null)
	        {
	        	//System.out.println("bruh?");
	        	return true;
	        }
	        images.add(new ImageIcon(url).getImage());
	        OutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.home") + "/Desktop" + name));
	        System.out.println(src + " " + url + " " + in + " " + in.read());
	        for (int b; (b = in.read()) != -1;) {
	            out.write(b);
	        }
	       // Thread.sleep(1000);
	        
	        out.close();
	        in.close();
	        return true;
		
	}
}
