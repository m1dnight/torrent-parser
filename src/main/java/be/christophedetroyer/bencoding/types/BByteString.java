package be.christophedetroyer.bencoding.types;

import be.christophedetroyer.bencoding.Utils;

import java.util.Arrays;

/**
 * Created by christophe on 15.01.15.
 */
public class BByteString
{
    private final byte[] data;

    public BByteString(byte[] data)
    {
        this.data = data;
    }

    public BByteString(String name)
    {
        this.data = name.getBytes();
    }

    ////////////////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public byte[] getData()
    {
        return data;
    }

    ////////////////////////////////////////////////////////////////////////////
    //// OVERRIDDEN METHODS ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString()
    {
        if (Utils.allAscii(data))
        {
            return new String(this.data);
        } else
        {
            return "<bytes:" + this.data.length + ">";
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BByteString that = (BByteString) o;

        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode()
    {
        return data != null ? Arrays.hashCode(data) : 0;
    }
}
