import java.awt.MouseInfo;
import java.awt.event.MouseListener;

public class MouseEvent implements MouseListener{
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int button = GraphicButton.clickIndex(e.getX(), e.getY());
        

        if (button != -1)
        {
            String id = GraphicButton.getButton(button).getID();
            switch (id)
            {
                case "Single":
                    NimRunner.screenNum = 1;
                    NimRunner.nimComp = new NimComputer();
                    NimRunner.tiles = new MoveableTile[NimRunner.nimComp.getNumTiles()];
                    for (int i = 0; i<NimRunner.nimComp.getNumTiles(); i++)
                    {
                        NimRunner.tiles[i] = new MoveableTile(250 + ((i%10)*50), 100 + ((i-(i%10))*5), 1);
                    }
                    NimRunner.enemy = new NimEnemy();
                    break;
                case "Double":
                    NimRunner.screenNum = 1;
                    NimRunner.nimComp = new NimComputer();
                    if (Math.random()>0.5)
                    {
                        NimRunner.nimComp.nextTurn();
                    }
                    NimRunner.tiles = new MoveableTile[NimRunner.nimComp.getNumTiles()];
                    for (int i = 0; i<NimRunner.nimComp.getNumTiles(); i++)
                    {
                        NimRunner.tiles[i] = new MoveableTile(250 + ((i%10)*50), 100 + ((i-(i%10))*5), 1);
                    }
                    break;
                case "HomeScreen":
                    NimRunner.screenNum = 0;
                    NimRunner.nimComp = null;
                    MoveableTile.allTiles = new MoveableTile[0];
                    NimRunner.gameWon = false;
                    NimRunner.tooManyCool = -1;
                    NimRunner.highlighted = null;
                    NimRunner.enemy = null;
                    NimRunner.enemyTurn = -1;
                    break;
                case "Quit":
                    System.exit(0);
                    break;
                case "Submit":
                    if (!(NimRunner.enemyTurn >= 0))
                    {
                        NimRunner.nimComp.nim();
                        if (NimRunner.enemy != null && !NimRunner.gameWon)
                        {
                            NimRunner.enemy.play();
                        }
                    }
                    else
                    {
                        System.out.println("During Enemy Turn");
                    }
                    break;
                default:
                    System.out.println("You forgor");
                    break;
            }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e)
    {
        NimRunner.mouseHeld = true;

        if (NimRunner.highlighted != null && NimRunner.highlighted.getCreated() && NimRunner.highlighted.clickCheck(e.getX(), e.getY()))
        {
            NimRunner.highlighted.setDX(((int)(MouseInfo.getPointerInfo().getLocation().getX() - NimRunner.screen.getLocationOnScreen().getX()))-NimRunner.highlighted.getX());
            NimRunner.highlighted.setDY(((int)(MouseInfo.getPointerInfo().getLocation().getY() - NimRunner.screen.getLocationOnScreen().getY()))-NimRunner.highlighted.getY());
            NimRunner.highlightTracking = true;
        }
        else
        {
            int button = GraphicButton.clickIndex(e.getX(), e.getY());
            if (button != -1)
            {
                GraphicButton btn = GraphicButton.getButton(button);
                btn.heldColor();
                btn.changeHeld();
            }

            int tile = MoveableTile.clickIndex(e.getX(), e.getY());
            if (tile != -1)
            {
                MoveableTile obj = MoveableTile.getTileObject(tile);
                int deltaX = e.getX() - obj.getX();
                int deltaY = e.getY() - obj.getY();
                NimRunner.holdingTile(deltaX, deltaY, obj);
            }
            else if (button == -1 && (NimRunner.highlighted == null || !NimRunner.highlighted.clickCheck(e.getX(), e.getY())))
            {
                NimRunner.highlighted = new Highlighted(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e)
    {
        NimRunner.mouseHeld = false;

        for (int i = 0; i<GraphicButton.allButtons.length; i++)
        {
            if (GraphicButton.allButtons[i].isHeld())
            {
                GraphicButton.allButtons[i].normalColor();
                GraphicButton.allButtons[i].changeHeld();
            }
        }

        
        if (NimRunner.highlighted != null && !NimRunner.highlighted.getCreated()) // If there is at least one tile inside the highlighted area
        {
            if (NimRunner.highlighted.getX() >= e.getX()) // Set width and height / fix x and y
            {
                int temp = NimRunner.highlighted.getX();
                NimRunner.highlighted.setX(e.getX());
                NimRunner.highlighted.setWidth(temp - NimRunner.highlighted.getX());
            }
            else
            {
                NimRunner.highlighted.setWidth(e.getX() - NimRunner.highlighted.getX());
            }

            if (NimRunner.highlighted.getY() >= e.getY())
            {
                int temp = NimRunner.highlighted.getY();
                NimRunner.highlighted.setY(e.getY());
                NimRunner.highlighted.setHeight(temp - NimRunner.highlighted.getY());
            }
            else
            {
                NimRunner.highlighted.setHeight(e.getY() - NimRunner.highlighted.getY());
            }


            if (NimRunner.highlighted.countSet() == 0)
            {
                NimRunner.highlighted = null;
            }
            else 
            {
                NimRunner.highlighted.setCreated(true);
            }
        }

        if (NimRunner.highlightTracking)
        {
            NimRunner.highlightTracking = false;
            NimRunner.highlighted = null;
        }

        NimRunner.stopTile();
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e)
    {
        NimRunner.stopTile();
    }
}
