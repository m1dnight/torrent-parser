package be.christophedetroyer.torrent;

import java.util.List;

public class TorrentFile
{
    private final Long fileLength;
    private final List<String> fileDirs;

    public TorrentFile(Long fileLength, List<String> fileDirs)
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
    public Long getFileLength()
    {
        return fileLength;
    }

    public List<String> getFileDirs()
    {
        return fileDirs;
    }
}
