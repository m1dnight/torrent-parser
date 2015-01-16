package be.christophedetroyer.bencoding.types;

/**
 * Created by christophe on 15.01.15.
 */
public class BInt
{
    private final int value;

    public BInt(int value)
    {
        this.value = value;
    }

    ////////////////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public int getValue()
    {
        return value;
    }

    ////////////////////////////////////////////////////////////////////////////
    //// OVERRIDDEN METHODS ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BInt bInt = (BInt) o;

        return value == bInt.value;
    }

    @Override
    public int hashCode()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
