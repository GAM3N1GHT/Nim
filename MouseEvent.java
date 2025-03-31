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
                    // Make enemy
                    break;
                case "Double":
                    NimRunner.screenNum = 1;
                    NimRunner.nimComp = new NimComputer();
                    NimRunner.tiles = new MoveableTile[NimRunner.nimComp.getNumTiles()];
                    for (int i = 0; i<NimRunner.nimComp.getNumTiles(); i++)
                    {
                        NimRunner.tiles[i] = new MoveableTile(250 + ((i%10)*50), 100 + ((i-(i%10))*5), 1);
                    }
                    break;
                case "HomeScreen":
                    NimRunner.screenNum = 0;
                    NimRunner.nimComp = null;
                    break;
                case "Quit":
                    System.exit(0);
                    break;
                case "Test":
                    MoveableTile[] bombable = new MoveableTile[0];
                    for (int i = 0; i<MoveableTile.allTiles.length; i++)
                    {
                        if (NimRunner.nimComp.getTurn())
                        {
                            if (MoveableTile.allTiles[i].getX() <= 175)
                            {
                                MoveableTile[] temp = new MoveableTile[bombable.length+1];
                                for (int ii = 0; ii<bombable.length; ii++)
                                {
                                    temp[ii] = bombable[ii];
                                }
                                temp[bombable.length] = MoveableTile.allTiles[i];
                                bombable = temp;
                            }
                        }
                        else
                        {
                            if (MoveableTile.allTiles[i].getX() >= NimRunner.screenWidth - 189)
                            {
                                MoveableTile[] temp = new MoveableTile[bombable.length+1];
                                for (int ii = 0; ii<bombable.length; ii++)
                                {
                                    temp[ii] = bombable[ii];
                                }
                                temp[bombable.length] = MoveableTile.allTiles[i];
                                bombable = temp;
                            }
                        }
                    }
                    System.out.println("Bomb: " + bombable.length + ", All: " + MoveableTile.allTiles.length/2);
                    if (bombable.length < MoveableTile.allTiles.length/2)
                    {
                        NimRunner.nimComp.nextTurn();
                        System.out.println("temp");
                        // for (int i = bombable.length-1; i>=0; i--)
                        // {
                        //     for (int ii = MoveableTile.allTiles.length-1; ii>=0; ii--)
                        //     {
                        //         if (bombable[i] == MoveableTile.allTiles[ii])
                        //         {
                        //             // Get rid of the tile in both arrays
                        //         }
                        //     }
                        // }
                    }
                    else
                    {
                        System.out.println("Too many tiles");
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
