package be.christophedetroyer.torrent;

import java.util.List;

public class TorrentFile
{
    private final int fileLength;
    private final List<String> fileDirs;

    public TorrentFile(int fileLength, List<String> fileDirs)
    {
        this.fileLength = fileLength;
        this.fileDirs = fileDirs;
    }

    @Override
    public String toString()
    {
        return "TorrentFile{" +
                "fileLength=" + fileLength +
                ", fileDirs=" + fileDirs +
                '}';
    }

    ////////////////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public int getFileLength()
    {
        return fileLength;
    }

    public List<String> getFileDirs()
    {
        return fileDirs;
    }
}
