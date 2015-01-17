package be.christophedetroyer.bencoding.types;

import be.christophedetroyer.bencoding.Utils;

import java.util.Arrays;

public class BByteString implements IBencodable
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

    public String bencodedString()
    {
        return data.length + ":" + new String(data);
    }

    public byte[] bencode()
    {
        long length = data.length;
        String lstring = Long.toString(length);
        byte[] sizeBytes = lstring.getBytes();

        byte[] bencoded = new byte[sizeBytes.length + 1 + data.length];
        bencoded[sizeBytes.length] = ':';
        System.arraycopy(sizeBytes, 0, bencoded, 0, sizeBytes.length);

        for (int i = 0; i < data.length; i++)
            bencoded[i + sizeBytes.length + 1] = data[i];

        return bencoded;
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
