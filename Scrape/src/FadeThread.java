
public class FadeThread extends Thread
{
	int fadeType;
	Lookup l;
	public void run()
	{
		while (l.alpha > 0)
		{
			l.alpha = (int)Math.max(l.alpha - 70, 0);
			//l.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		l.drawing = false;
		this.stop();
	}

}
