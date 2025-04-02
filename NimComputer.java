import java.util.ArrayList;

public class NimComputer
{
    public int numTiles;
    public boolean turn;

    public NimComputer()
    {
        numTiles = (int)(Math.random()*40) + 10;
        turn = true;
    }

    public void nim()
    {
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
        if (bombable.length != 0 && (bombable.length <= MoveableTile.allTiles.length/2 || bombable.length == 1))
        {
            NimRunner.nimComp.nextTurn();
            ArrayList<MoveableTile> bomb = new ArrayList<MoveableTile>();
            ArrayList<MoveableTile> all = new ArrayList<MoveableTile>();
            for (int i = 0; i<bombable.length; i++)
            {
                bomb.add(bombable[i]);
            }
            for (int i = 0; i<MoveableTile.allTiles.length; i++)
            {
                all.add(MoveableTile.allTiles[i]);
            }

            for (int i = bomb.size()-1; i>=0; i--)
            {
                for (int ii = all.size()-1; ii>=0; ii--)
                {
                    if (bomb.get(i) == all.get(ii))
                    {
                        //Do explosion at all.get(ii)
                        NimRunner.selfDestruct(all.get(ii).getX()-10, all.get(ii).getY()-10);
                        all.remove(ii);
                    }
                }
            }

            MoveableTile.allTiles = new MoveableTile[all.size()];
            for (int i = 0; i<all.size(); i++)
            {
                MoveableTile.allTiles[i] = all.get(i);
            }
        }
        else if (bombable.length != 0)
        {
            NimRunner.tooManyCool = 100;
        }
        if (MoveableTile.allTiles.length == 0)
        {
            NimRunner.gameWon = true;
        }
        System.out.println(MoveableTile.allTiles.length);
    }

    public int getNumTiles()
    {
        return numTiles;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public void nextTurn()
    {
        turn = !turn;
    }
}
