package be.christophedetroyer.bencoding;

import be.christophedetroyer.bencoding.types.*;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ReaderTest
{

    @Test
    public void testByteString()
    {
        int NUMBEROFTESTS = 100;

        for (int i = 1; i < NUMBEROFTESTS; i++)
        {
            String randomInput = i + ":" + randomString(i);
            Reader r = new Reader(randomInput);

            byte[] bencoded = r.read().get(0).bencode();
            byte[] expected = randomInput.getBytes();

            assertArrayEquals("Bytes do not match for " + randomInput, bencoded, expected);
            assertEquals("Strings do not match", randomInput, r.read().get(0).bencodedString());
        }
    }

    @Test
    public void testInteger()
    {
        int NUMBEROFTESTS = 100;

        for (int i = 1; i < NUMBEROFTESTS; i++)
        {
            int randomInt = randInt(0, Integer.MAX_VALUE - 1);
            String randomInput = "i" + randomInt + "e";
            Reader r = new Reader(randomInput);

            byte[] bencoded = r.read().get(0).bencode();
            byte[] expected = randomInput.getBytes();

            assertArrayEquals("Bytes do not match for " + randomInput, bencoded, expected);
            assertEquals("Strings do not match", randomInput, r.read().get(0).bencodedString());
        }
    }

    @Test
    public void testList()
    {
        String input = "l4:spami42e4:spami42e4:spami42e4:spami42ee";
        Reader r = new Reader(input);

        List<IBencodable> output = r.read();
        IBencodable first = output.get(0);
        byte[] bencoded = first.bencode();

        assertArrayEquals("Bytes do not match!", bencoded, input.getBytes());
        assertEquals("Strings do not match", input, r.read().get(0).bencodedString());
    }
    @Test
    public void testDictionary()
    {
        String input = "d3:bar4:spam5:fooooi42ee";
        Reader r = new Reader(input);

        List<IBencodable> output = r.read();
        IBencodable first = output.get(0);
        byte[] bencoded = first.bencode();

        assertArrayEquals("Bytes do not match!", bencoded, input.getBytes());
        assertEquals("Strings do not match", input, r.read().get(0).bencodedString());

    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static Random rand = new Random();
    public static int randInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    public static String randomString(int length)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            sb.append((char) randInt(33, 127));
        }
        return sb.toString();
    }

}