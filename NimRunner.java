import java.awt.*;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

// WARNING: This project was made with no planning for the future so the code is garbage.
//          The idea was: make something that works, it doesn't have to be easy to read because you are only going to use this once

public class NimRunner extends JFrame
{
    public static Screen screen;
    public static int timeRunning = 0;
    public static int screenWidth = 1024, screenHeight = 768; // 1024, 768
    public static boolean isTileHeld = false;
	public static boolean isHighlightedHeld = false;
	public static boolean highlightTracking = false;
	public static int screenNum = 0;
    private static int MDeltaX;
    private static int MDeltaY;
    private static MoveableTile heldTile;
	public static MoveableTile[] tiles;
	public static NimComputer nimComp;
	public static Highlighted highlighted;

	public static boolean mouseHeld = false;

    MouseEvent me = new MouseEvent();

	
	//MoveableTile test2 = new MoveableTile(200, 100, 1);
	GraphicButton PlayBtn = new GraphicButton(200, 600, 200, 80, "Play", 150, 0, 150, 255, 255, 255, "Double", 0, 30);
	GraphicButton QuitBtn = new GraphicButton(600, 600, 200, 80, "Quit", 150, 0, 150, 255, 255, 255, "Quit", 0, 30);

	GraphicButton TestBtn = new GraphicButton(600, 600, 200, 80, "Test", 150, 0, 150, 255, 255, 255, "Test", 1, 30);



    public static void main(String[] args)
    {
        new NimRunner();
    }

    NimRunner()
    {
        //Sets up the screen
		this.setSize(screenWidth, screenHeight);
		this.setTitle("Video Editor");		
		this.setLocationRelativeTo(null); //always in middle
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creates the screen
		screen = new Screen();
		this.add(screen);
		this.setVisible(true);

		
		Long lastLong = (long) 0;
		while (true)
		{
			Calendar calendar = Calendar.getInstance();
			Long currentTime = calendar.getTimeInMillis();

			//Provides a fixed update between computers (1000 per second)
			if(currentTime%10 == 0 && !(lastLong.equals(currentTime))) //Runs 100 times per second
			{
				lastLong = currentTime;
				timeRunning++;
				screen.repaint();
			}
		}
    }

    class Screen extends JPanel{
		Screen(){
			this.setBackground(new Color(0, 0, 36));		
			this.addMouseListener(me);	
		}
		

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			//if Tile held then make it follow the mouse and draw selected box
            if (isTileHeld)
            {
                tileTrack();
                g2.setColor(new Color(255,0,255));
				g2.drawRect(heldTile.getX(), heldTile.getY(), 30, 30);
            }

			if (screenNum == 1)
			{
				//Play


				g.setColor(new Color(200, 0, 0));
				g.fillRect(0, 0, 175, screenHeight);
				g.setColor(new Color(0, 0, 200));
				g.fillRect(screenWidth-189, 0, 175, screenHeight);
			}

            MoveableTile.drawAll(g, g2);
			GraphicButton.drawAll(g, g2);
			if (highlighted != null)
			{
				if (mouseHeld)
				{
					if (!highlighted.getCreated())
					{
						int mouseX = (int)(MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().getX());
						int mouseY = (int)(MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().getY());

						highlighted.setWidth(mouseX - highlighted.getX());
						highlighted.setHeight(mouseY - highlighted.getY());
					}
					else if (highlightTracking)
					{
						highlighted.highlightedTrack();
					}
				}
				

				highlighted.draw(g, g2);
			}
		}
	}

    private void tileTrack()
	{
		int tempX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - this.getLocation().getX());
		int tempY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - this.getLocation().getY());
		tempX = tempX - MDeltaX - 7;
		tempY = tempY - MDeltaY - 30;
		if (tempX < 0)
		{
			tempX = 0;
		}
		else if (tempX+44 > screenWidth)
		{
			tempX = screenWidth-44;
		}
		if (tempY < 0)
		{
			tempY = 0;
		}
		else if (tempY+70 > screenHeight)
		{
			tempY = screenHeight-70;
		}
		heldTile.changePos(tempX, tempY);
	}

    public static void holdingTile(int deltaX, int deltaY, MoveableTile tile)
	{
		MDeltaX = deltaX;
		MDeltaY = deltaY;
		heldTile = tile;
		isTileHeld = true;
	}

    public static void stopTile()
	{
		isTileHeld = false;
	}
}
