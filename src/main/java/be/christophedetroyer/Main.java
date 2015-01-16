package be.christophedetroyer;


import be.christophedetroyer.torrent.Torrent;

import java.io.IOException;

/**
 * Created by christophe on 15.01.15.
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println(Torrent.parseTorrent(args[0]));
    }
}
