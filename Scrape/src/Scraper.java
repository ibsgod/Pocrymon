import java.awt.Graphics;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Scraper 
{
	Graphics g;
	static ImageIcon a = null;
	static int y = 0;
	static int x = 0;
	public static void main (String args[]) throws Exception
	{
		
		StartScreen s = new StartScreen ();
		//Screen s = new Screen();
	}
}
