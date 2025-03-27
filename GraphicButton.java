import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GraphicButton
{
    public static GraphicButton[] allButtons = new GraphicButton[0];
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private Color origionalColor = new Color(0,0,0);
    private Color color = new Color(0, 0, 0);
    private Color textColor = new Color(0,0,0);
    private String text = "temp";
    private String ID = "temp";
    private int screenNum;

    private boolean isHeld = false;

    Font stringFont = new Font( "Times New Roman", Font.PLAIN, 18);

    GraphicButton(int x, int y, int w, int h, String txt, int r, int g, int b, int tr, int tg, int tb, String id, int sn, int size)
    {
        GraphicButton[] temp = new GraphicButton[allButtons.length+1];
        for(int i = 0; i<allButtons.length; i++)
        {
            temp[i] = allButtons[i];
        }
        temp[allButtons.length] = this;
        allButtons = temp;

        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = txt;
        this.origionalColor = new Color(r,g,b);
        this.color = new Color(r,g,b);
        this.textColor = new Color(tr,tg,tb);
        this.ID = id;
        this.screenNum = sn;
        stringFont = new Font("Times New Roman", Font.PLAIN, size);
    }

    public boolean clickCheck(int xClick, int yClick)
    {
        if(this.screenNum == NimRunner.screenNum && (xClick >= x && xClick <= x+width && yClick >= y && yClick <= y+height))
        {
            return true;
        }
        return false;
    }

    public static int clickIndex(int xClick, int yClick)
    {
        for(int i = 0; i<allButtons.length; i++)
        {
            if(allButtons[i].clickCheck(xClick, yClick))
            {
                return i;
            }
        }
        return -1;
    }

    public static GraphicButton getButton(int index)
    {
        return allButtons[index];
    }

    public void setText(String newText)
    {
        this.text = newText;
    }

    public boolean isHeld()
    {
        return isHeld;
    }

    public void changeHeld()
    {
        if(isHeld)
        {
            isHeld = false;
        }
        else
        {
            isHeld = true;
        }
    }

    public void heldColor()
    {
        int average = (color.getRed()+color.getGreen()+color.getBlue())/3;
        if (average<127.5)
        {
            if (color.getRed()+70<255)
            {
                color = new Color(color.getRed()+70,color.getGreen(),color.getBlue());
            }
            if (color.getGreen()+70<255)
            {
                color = new Color(color.getRed(),color.getGreen()+70,color.getBlue());
            }
            if (color.getBlue()+70<255)
            {
                color = new Color(color.getRed(),color.getGreen(),color.getBlue()+70);
            }
        }
        else
        {
            if (color.getRed()-70>0)
            {
                color = new Color(color.getRed()-70,color.getGreen(),color.getBlue());
            }
            if (color.getGreen()-70>0)
            {
                color = new Color(color.getRed(),color.getGreen()-70,color.getBlue());
            }
            if (color.getBlue()-70>0)
            {
                color = new Color(color.getRed(),color.getGreen(),color.getBlue()-70);
            }
        }
    }

    public void normalColor()
    {
        color = origionalColor;
    }

    public String getID()
    {
        return this.ID;
    }

    public void draw(Graphics g, Graphics2D g2)
    {
        if (this.screenNum == NimRunner.screenNum)
        {
            g.setFont(stringFont);
            g.setColor(color);
            g2.fillRect(x, y, width, height);
            g.setColor(textColor);
            g2.drawString(text, x-(text.length()*(stringFont.getSize()/5))+(width/2), y+(height/2)+(stringFont.getSize()/6)); // this.size/6,   text, x-(text.length()*4)+(width/2), y+(height/2)+5
        }
    }

    public static void drawAll(Graphics g, Graphics2D g2)
    {
        for(int i = 0; i<allButtons.length; i++)
        {
            allButtons[i].draw(g,g2);
        }
    }
}
