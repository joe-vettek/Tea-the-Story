package cloud.lemonslice.teastory.block.crops;



public class VinePair
{
    private final VineType type;
    private final TrellisBlock emptyTrellis;

    public VinePair(VineType type, TrellisBlock trellis)
    {
        this.type = type;
        this.emptyTrellis = trellis;
    }

    @Override
    public int hashCode()
    {
        return type.hashCode() + emptyTrellis.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o instanceof VinePair)
        {
            VinePair pair = (VinePair) o;
            return (pair.type == this.type && pair.emptyTrellis == this.emptyTrellis);
        }
        return false;
    }
}
