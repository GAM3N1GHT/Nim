import java.awt.event.MouseListener;

public class MouseEvent implements MouseListener{
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        System.out.println("Click index: " + GraphicButton.clickIndex(e.getX(), e.getY()));

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
                    for (int i = 0; i<NimRunner.nimComp.getNumTiles(); i++)
                    {
                        NimRunner.tiles[i] = new MoveableTile((i%10)*10, ((i/10)-(i%10))*10, 1);
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
                    System.out.println(NimRunner.nimComp.getNumTiles());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e)
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
        else
        {
            
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e)
    {
        for (int i = 0; i<GraphicButton.allButtons.length; i++)
        {
            if (GraphicButton.allButtons[i].isHeld())
            {
                GraphicButton.allButtons[i].normalColor();
                GraphicButton.allButtons[i].changeHeld();
            }
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
