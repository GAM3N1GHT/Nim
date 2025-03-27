import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MoveableTile
{
    public static MoveableTile[] allTiles = new MoveableTile[0];
    private int x = 0;
    private int y = 0;
    private int screenNum;

    MoveableTile(int x, int y, int sn)
    {
        this.x = x;
        this.y = y;
        this.screenNum = sn;
        MoveableTile[] replace = new MoveableTile[allTiles.length+1];
        for (int i = 0; i<allTiles.length; i++)
        {
            replace[i] = allTiles[i];
        }
        replace[allTiles.length] = this;
        allTiles = replace;
    }

    public static MoveableTile getTileObject(int index)
    {
        return allTiles[index];
    }

    public boolean clickCheck(int xClick, int yClick)
    {
        if(this.screenNum == NimRunner.screenNum && (xClick >= x && xClick <= x+30 && yClick >= y && yClick <= y+30))
        {
            return true;
        }
        return false;
    }

    public static int clickIndex(int xClick, int yClick)
    {
        for(int i = 0; i<allTiles.length; i++)
        {
            if(allTiles[i] != null && allTiles[i].clickCheck(xClick, yClick))
            {
                return i;
            }
        }
        return -1;
    }

    public void changePos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void draw(Graphics g, Graphics2D g2)
    {
        if (this.screenNum == NimRunner.screenNum)
        {
            g.setColor(new Color(255,255,255));
            g2.fillRect(x, y, 30, 30);
        }
    }

    public static void drawAll(Graphics g, Graphics2D g2)
    {
        if(allTiles.length > 0)
        {
            for(int i = 0; i<allTiles.length; i++)
            {
                if (allTiles[i] != null)
                {
                    allTiles[i].draw(g ,g2);
                }
            }
        }
    }

    public static void removeAllTiles()
    {
        for (int i = 0; i<allTiles.length; i++)
        {
            allTiles[i] = null;
        }
    }
}

