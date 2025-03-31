public class NimComputer
{
    public int numTiles;
    public boolean turn;

    public NimComputer()
    {
        numTiles = (int)(Math.random()*40) + 10;
        turn = true;
    }

    public int getNumTiles()
    {
        return numTiles;
    }

    public void subtract(int n)
    {
        
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
