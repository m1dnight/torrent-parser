package be.christophedetroyer.torrent;

import java.util.*;

/**
 * Created by christophe on 16.01.15.
 */
public class Torrent
{
    private String announce;
    private String name;
    private Long pieceLength;
    private byte[] piecesBlob;
    private List<String> pieces;
    private boolean singleFileTorrent;
    private Long totalSize;
    private List<TorrentFile> fileList;
    private String comment;
    private String createdBy;
    private Date creationDate;
    private List<String> announceList;
    private String info_hash;

    public Torrent()
    {

    }

    ////////////////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public List<TorrentFile> getFileList()
    {
        return fileList;
    }

    public void setFileList(List<TorrentFile> fileList)
    {
        this.fileList = fileList;
    }

    public String getAnnounce()
    {
        return announce;
    }

    public void setAnnounce(String announce)
    {
        this.announce = announce;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getPieceLength()
    {
        return pieceLength;
    }

    public void setPieceLength(Long pieceLength)
    {
        this.pieceLength = pieceLength;
    }

    public byte[] getPiecesBlob()
    {
        return piecesBlob;
    }

    public void setPiecesBlob(byte[] piecesBlob)
    {
        this.piecesBlob = piecesBlob;
    }

    public List<String> getPieces()
    {
        return pieces;
    }

    public void setPieces(List<String> pieces)
    {
        this.pieces = pieces;
    }

    public boolean isSingleFileTorrent()
    {
        return singleFileTorrent;
    }

    public void setSingleFileTorrent(boolean singleFileTorrent)
    {
        this.singleFileTorrent = singleFileTorrent;
    }

    public Long getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(Long totalSize)
    {
        this.totalSize = totalSize;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public List<String> getAnnounceList()
    {
        return announceList;
    }

    public void setAnnounceList(List<String> announceList)
    {
        this.announceList = announceList;
    }

    public String getInfo_hash()
    {
        return info_hash;
    }

    public void setInfo_hash(String info_hash)
    {
        this.info_hash = info_hash;
    }
}
