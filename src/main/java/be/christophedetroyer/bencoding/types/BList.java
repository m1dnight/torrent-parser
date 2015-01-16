package be.christophedetroyer.bencoding.types;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by christophe on 15.01.15.
 */
public class BList
{
    private final List<Object> list;

    public BList()
    {
        this.list = new LinkedList<Object>();
    }
    ////////////////////////////////////////////////////////////////////////////
    //// LOGIC METHODS /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public Iterator<Object> getIterator()
    {
        return list.iterator();
    }

    public void add(Object o)
    {
        this.list.add(o);
    }
    ////////////////////////////////////////////////////////////////////////////
    //// OVERRIDDEN METHODS ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Object entry : this.list)
        {
            sb.append(entry.toString());
        }
        sb.append(") ");

        return sb.toString();
    }

}
