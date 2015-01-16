package be.christophedetroyer.bencoding.types;

import java.util.Hashtable;
import java.util.Map;

//TODO we don't need this..?
/**
 * Created by christophe on 15.01.15.
 */
public class BDictionary
{
    private final Map<BByteString, Object> dictionary;

    public BDictionary()
    {
        this.dictionary = new Hashtable<BByteString, Object>();
    }
    ////////////////////////////////////////////////////////////////////////////
    //// LOGIC METHODS /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void add(BByteString key, Object value)
    {
        this.dictionary.put(key, value);
    }
    public Object find(BByteString key)
    {
        return dictionary.get(key);
    }
    ////////////////////////////////////////////////////////////////////////////
    //// OVERRIDDEN METHODS ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[\n");
        for (Map.Entry<BByteString, Object> entry : this.dictionary.entrySet())
        {
            sb.append(entry.getKey()).append(" :: ").append(entry.getValue()).append("\n");
        }
        sb.append("]");

        return sb.toString();
    }
}
