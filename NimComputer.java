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
}
