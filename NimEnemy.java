public class NimEnemy
{
    private int deleteNum = 0;
    private int integer = 0;

    public void play()
    {
        if (MoveableTile.allTiles.length == 0)
        {
            NimRunner.nimComp.nextTurn();
            NimRunner.enemyTurn = -1;
            NimRunner.gameWon = true;
            return;
        }
        if (NimRunner.enemyTurn == -1)
        {
            deleteNum = solve();
            NimRunner.enemyTurn = 30 + (deleteNum*15) + 14;
        }
        else if (NimRunner.enemyTurn > 30 && NimRunner.enemyTurn%15 == 0)
        {
            if (integer%2 == 1)
            {
                MoveableTile.allTiles[integer].changePos(NimRunner.screenWidth-159, (60*((integer-1)/2))+30);
            }
            else
            {
                MoveableTile.allTiles[integer].changePos(NimRunner.screenWidth-99, (60*((integer/2))+30));
            }
            integer++;
        }
        else if (NimRunner.enemyTurn == 10)
        {
            MoveableTile[] temp = new MoveableTile[MoveableTile.allTiles.length-deleteNum];
            for (int i = deleteNum; i<MoveableTile.allTiles.length; i++)
            {
                temp[i-deleteNum] = MoveableTile.allTiles[i];
            }
            for (int i = 0; i<deleteNum; i++)
            {
                NimRunner.selfDestruct(MoveableTile.allTiles[i].getX()-10, MoveableTile.allTiles[i].getY()-10);
            }
            MoveableTile.allTiles = temp;
        }
        else if (NimRunner.enemyTurn == 0)
        {
            deleteNum = 0;
            integer = 0;
            NimRunner.nimComp.nextTurn();
        }
        NimRunner.enemyTurn--;
    }

    public int solve()
    {
        int numTiles = MoveableTile.allTiles.length;
        if (numTiles > 3 && numTiles < 7)
        {
            return numTiles - 3;
        }
        else if (numTiles > 7 && numTiles < 15)
        {
            return numTiles - 7;
        }
        else if (numTiles > 15 && numTiles < 31)
        {
            return numTiles - 15;
        }
        else if (numTiles > 31)
        {
            return numTiles - 31;
        }
        else if (numTiles <= 3)
        {
            return 1;
        }
        else
        {
            return (int)(Math.random()*numTiles/2)+1;
        }
    }
}
