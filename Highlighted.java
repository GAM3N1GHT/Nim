import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

public class Highlighted
{
    private int x, y, width, height, deltaX, deltaY;
    private MoveableTile[] highlightedTiles = new MoveableTile[0];
    private int[] highlightedDeltaX = new int[0];
    private int[] highlightedDeltaY = new int[0];
    private boolean created = false;

    public Highlighted(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, Graphics2D g2)
    {
        int dX = x;
        int dY = y;
        int dW = width;
        int dH = height;
        if (width < 0)
        {
            dX += width;
            dW *= -1;
        }
        if (height < 0)
        {
            dY += height;
            dH *= -1;
        }

        g2.setColor(new Color(255, 255, 255));
        g.drawRect(dX, dY, dW, dH);
        g2.setColor(new Color(255, 255, 255, 100));
        g.fillRect(dX, dY, dW, dH);
    }

    public boolean clickCheck(int xClick, int yClick)
    {
        if(xClick >= x && xClick <= x+width && yClick >= y && yClick <= y+height)
        {
            return true;
        }
        return false;
    }

    public int countSet() // Counts ad replaces highlightedTiles with the tiles in the area of the highlighted region
    {
        int count = 0;
        for (int i = 0; i<MoveableTile.allTiles.length; i++)
        {
            // If left wall of tile is less than right wall of highlighted
            // If right wall of tile is greater than left wall of highlighted
            // If top wall of tile is less than bottom wall of highlighted
            // If bottom wall of tile is greater than top wall of highlighted
            if ((MoveableTile.allTiles[i].getX() <= x+width) && (MoveableTile.allTiles[i].getX()+30 >= x) && (MoveableTile.allTiles[i].getY() <= y+height) && (MoveableTile.allTiles[i].getY()+30 >= y))
            {
                count++;
                MoveableTile[] temp = new MoveableTile[highlightedTiles.length+1];
                for (int ii = 0; ii<highlightedTiles.length; ii++)
                {
                    temp[ii] = highlightedTiles[ii];
                }
                temp[highlightedTiles.length] = MoveableTile.allTiles[i];
                highlightedTiles = temp;

                int[] deltaXTemp = new int[highlightedDeltaX.length+1];
                for (int ii = 0; ii<highlightedDeltaX.length; ii++)
                {
                    deltaXTemp[ii] = highlightedDeltaX[ii];
                }
                deltaXTemp[highlightedDeltaX.length] = MoveableTile.allTiles[i].getX()-x;
                highlightedDeltaX = deltaXTemp;

                int[] deltaYTemp = new int[highlightedDeltaY.length+1];
                for (int ii = 0; ii<highlightedDeltaY.length; ii++)
                {
                    deltaYTemp[ii] = highlightedDeltaY[ii];
                }
                deltaYTemp[highlightedDeltaY.length] = MoveableTile.allTiles[i].getY()-y;
                highlightedDeltaY = deltaYTemp;
            }
        }
        return count;
    }

    public void highlightedTrack()
    {
        int tempX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - NimRunner.screen.getLocationOnScreen().getX());
        int tempY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - NimRunner.screen.getLocationOnScreen().getY());
        tempX = tempX - deltaX;
        tempY = tempY - deltaY;
        if (tempX < 0)
        {
            tempX = 0;
        }
        else if (tempX+width+14 > NimRunner.screenWidth)
        {
            tempX = NimRunner.screenWidth-width-14;
        }
        if (tempY < 0)
        {
            tempY = 0;
        }
        else if (tempY+height+40 > NimRunner.screenHeight)
        {
            tempY = NimRunner.screenHeight-height-40;
        }
        x = tempX;
        y = tempY;

        for (int i = 0; i<highlightedTiles.length; i++)
        {
            highlightedTiles[i].changePos(x + highlightedDeltaX[i], y + highlightedDeltaY[i]);
        }
    }

    public boolean getCreated()
    {
        return created;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public MoveableTile[] getHeld()
    {
        return highlightedTiles;
    }

    public void setCreated(boolean c)
    {
        this.created = c;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setDX(int dx)
    {
        this.deltaX = dx;
    }

    public void setDY(int dy)
    {
        this.deltaY = dy;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setHighlighted(MoveableTile[] highlighted)
    {
        this.highlightedTiles = highlighted;
    }
}
