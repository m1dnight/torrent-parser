package be.christophedetroyer.bencoding;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by christophe on 15.01.15.
 */
public class Utils
{

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Takes an array of bytes and converts them to a string of
     * hexadecimal characters.
     *
     * @param bytes byte array
     * @return String
     */
    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++)
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4]; // Get left part of byte
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Get right part of byte
        }
        return new String(hexChars);
    }

    /**
     * Takes a filepath and returns the nth byte of that file.
     *
     * @param path Path to file
     * @param nth  Nth position to get, starting from 0.
     * @return byte.
     */
    public static byte readNthByteFromFile(String path, long nth)
    {
        RandomAccessFile rf = null;
        try
        {
            rf = new RandomAccessFile(path, "r");

            if (rf.length() < nth)
                throw new EOFException("Reading outside of bounds of file");

            rf.seek(nth);
            byte curr = rf.readByte();
            rf.close();

            return curr;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            assert rf != null;
            try
            {
                rf.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * Given a byte and a position, returns if byte at
     * position is set. (Position starts at 0 from the left).
     * @param b byte
     * @param position position in byte
     * @return boolean indicating if bit is 1.
     */
    public static boolean isBitSet(byte b, int position)
    {
        return ((b >> position) & 1) == 1;
    }

    /**
     * Prints a byte.
     * @param b byte
     */
    public static void printByte(byte b)
    {
        String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        System.out.println(s1); // 10000001
    }

    /**
     * Checks if a list of bytes are all printable ascii chars.
     * They are if the mostleft bit is never set (always 0).
     * @param data array of bytes
     * @return bolean indicating wether this byte is a valid ascii char.
     */
    public static boolean allAscii(byte[] data)
    {
        for(byte b : data)
            if(isBitSet(b, 7))
                return false;
        return true;
    }

}
