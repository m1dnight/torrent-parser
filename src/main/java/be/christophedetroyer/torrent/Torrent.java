package be.christophedetroyer.torrent;

import be.christophedetroyer.bencoding.Reader;
import be.christophedetroyer.bencoding.Utils;
import be.christophedetroyer.bencoding.types.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by christophe on 16.01.15.
 */
public class Torrent
{

    private final String announce;
    private final String name;
    private final Long pieceLength;
    private final byte[] piecesBlob;
    private final List<String> pieces;
    private final boolean singleFileTorrent;
    private final Long totalSize;
    private final List<TorrentFile> fileList;
    private final String comment;
    private final String createdBy;
    private final Date creationDate;
    private final List<String> announceList;

    public Torrent(String announce, String name, Long pieceLength, List<String> pieces, byte[] piecesBlob, boolean singleFileTorrent,
                   Long totalSize, List<TorrentFile> fileList, String comment, String createdBy, Date creationDate,
                   List<String> announceList)
    {

        this.announce = announce;
        this.name = name;
        this.pieceLength = pieceLength;
        this.pieces = pieces;
        this.piecesBlob = piecesBlob;
        this.singleFileTorrent = singleFileTorrent;
        this.totalSize = totalSize;
        this.fileList = fileList;
        this.comment = comment;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.announceList = announceList;
    }
    ////////////////////////////////////////////////////////////////////////////
    //// LOGIC METHODS /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public String getInfoHash()
    {
        return "";
    }
    ////////////////////////////////////////////////////////////////////////////
    //// OVERRIDDEN METHODS ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString()
    {
        return "Torrent{" +
                "announce='" + announce + '\'' +
                ", name='" + name + '\'' +
                ", pieceLength=" + pieceLength +
                ", pieces=" + pieces.size() +
                ", singleFileTorrent=" + singleFileTorrent +
                ", totalSize=" + totalSize +
                ", fileList=" + fileList +
                ", comment='" + comment + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationDate=" + creationDate +
                ", announceList=" + announceList +
                '}';
    }

    ////////////////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public String getAnnounce()
    {
        return announce;
    }

    public String getName()
    {
        return name;
    }

    public Long getPieceLength()
    {
        return pieceLength;
    }

    public List<String> getPieces()
    {
        return pieces;
    }

    public boolean isSingleFileTorrent()
    {
        return singleFileTorrent;
    }

    public Long getTotalSize()
    {
        return totalSize;
    }

    public List<TorrentFile> getFileList()
    {
        return fileList;
    }

    public String getComment()
    {
        return comment;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public List<String> getAnnounceList()
    {
        return announceList;
    }

    ////////////////////////////////////////////////////////////////////////////
    //// PARSER FUNCTIONALITY //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public static Torrent parseTorrent(String filePath) throws IOException
    {
        Reader r = new Reader(new File(filePath));
        List<IBencodable> x = r.read();
        // A valid torrentfile should only return a single dictionary.
        if (x.size() != 1)
            throw new Error("Parsing .torrent yielded wrong number of bencoding structs.");
        try
        {
            return Torrent.parseTorrent(x.get(0));
        } catch (ParseException e)
        {
            System.err.println("Error parsing torrent!");
        }
        return null;
    }

    private static Torrent parseTorrent(Object o) throws ParseException
    {
        if (o instanceof BDictionary)
        {
            BDictionary dictionary = (BDictionary) o;
            // Obligated elements of dictionary.
            String announce = parseAnnounce(dictionary);
            // Get the information out of the dictionary.
            BDictionary info = parseInfoDictionary(dictionary);

            System.out.println(Utils.SHAsum(info.blob));


            String name = parseTorrentLocation(info);
            Long pieceLength = parsePieceLength(info);
            List<String> pieces = parsePiecesHashes(info);
            byte[] piecesBlob = parsePiecesBlob(info);

            // Optional values
            List<TorrentFile> fileList = parseFileList(info);
            String comment = parseComment(dictionary);
            String createdBy = parseCreatorName(dictionary);
            Date creationDate = parseCreationDate(dictionary);
            List<String> announceList = parseAnnounceList(dictionary);
            Long totalSize = parseSingleFileTotalSize(info);

            // Single file or multi-file torrent.
            boolean singleFileTorrent;
            singleFileTorrent = null != info.find(new BByteString("length"));

            return new Torrent(announce, name, pieceLength, pieces, piecesBlob, singleFileTorrent, totalSize, fileList, comment,
                               createdBy, creationDate, announceList);
        } else
        {
            throw new ParseException("Could not parse Object to BDictionary", 0);
        }
    }

    /**
     * @param info info dictionary
     * @return length — size of the file in bytes (only when one file is being shared)
     */
    private static Long parseSingleFileTotalSize(BDictionary info)
    {
        if (null != info.find(new BByteString("length")))
            return ((BInt) info.find(new BByteString("length"))).getValue();
        return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return info — this maps to a dictionary whose keys are dependent on whether
     * one or more files are being shared.
     */
    private static BDictionary parseInfoDictionary(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("info")))
            return (BDictionary) dictionary.find(new BByteString("info"));
        else
            return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return creation date: (optional) the creation time of the torrent, in standard UNIX epoch format
     * (integer, seconds since 1-Jan-1970 00:00:00 UTC)
     */
    private static Date parseCreationDate(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("creation date")))
            return new Date(Long.parseLong(dictionary.find(new BByteString("creation date")).toString()));
        return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return created by: (optional) name and version of the program used to create the .torrent (string)
     */
    private static String parseCreatorName(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("created by")))
            return dictionary.find(new BByteString("created by")).toString();
        return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return comment: (optional) free-form textual comments of the author (string)
     */
    private static String parseComment(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("comment")))
            return dictionary.find(new BByteString("comment")).toString();
        else
            return null;
    }

    /**
     * @param info infodictionary of torrent
     * @return piece length — number of bytes per piece. This is commonly 28 KiB = 256 KiB = 262,144 B.
     */
    private static Long parsePieceLength(BDictionary info)
    {
        if (null != info.find(new BByteString("piece length")))
            return ((BInt) info.find(new BByteString("piece length"))).getValue();
        else
            return null;
    }

    /**
     * @param info info dictionary of torrent
     * @return name — suggested filename where the file is to be saved (if one file)/suggested directory name
     * where the files are to be saved (if multiple files)
     */
    private static String parseTorrentLocation(BDictionary info)
    {
        if (null != info.find(new BByteString("name")))
            return info.find(new BByteString("name")).toString();
        else
            return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return announce — the URL of the tracke
     */
    private static String parseAnnounce(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("announce")))
            return dictionary.find(new BByteString("announce")).toString();
        else
            return null;
    }

    /**
     * @param info info dictionary of .torrent file.
     * @return pieces — a hash list, i.e., a concatenation of each piece's SHA-1 hash. As SHA-1 returns a 160-bit hash,
     * pieces will be a string whose length is a multiple of 160-bits.
     */
    private static byte[] parsePiecesBlob(BDictionary info)
    {
        if (null != info.find(new BByteString("pieces")))
        {
            return ((BByteString) info.find(new BByteString("pieces"))).getData();
        } else
        {
            throw new Error("Info dictionary does not contain pieces bytestring!");
        }
    }
    /**
     * @param info info dictionary of .torrent file.
     * @return pieces — a hash list, i.e., a concatenation of each piece's SHA-1 hash. As SHA-1 returns a 160-bit hash,
     * pieces will be a string whose length is a multiple of 160-bits.
     */
    private static List<String> parsePiecesHashes(BDictionary info)
    {
        if (null != info.find(new BByteString("pieces")))
        {
            List<String> sha1HexRenders = new ArrayList<String>();
            byte[] piecesBlob = ((BByteString) info.find(new BByteString("pieces"))).getData();
            // Split the piecesData into multiple hashes. 1 hash = 20 bytes.
            if (piecesBlob.length % 20 == 0)
            {
                int hashCount = piecesBlob.length / 20;
                for (int currHash = 0; currHash < hashCount; currHash++)
                {
                    byte[] currHashByteBlob = Arrays.copyOfRange(piecesBlob, 20 * currHash, (20 * (currHash + 1)) - 1);
                    String sha1 = Utils.bytesToHex(currHashByteBlob);
                    sha1HexRenders.add(sha1);
                }
            } else
            {
                throw new Error("Error parsing SHA1 piece hashes. Bytecount was not a multiple of 20.");
            }
            return sha1HexRenders;
        } else
        {
            throw new Error("Info dictionary does not contain pieces bytestring!");
        }
    }

    /**
     * @param info info dictionary of torrent
     * @return files — a list of dictionaries each corresponding to a file (only when multiple files are being shared).
     */
    private static List<TorrentFile> parseFileList(BDictionary info)
    {
        if (null != info.find(new BByteString("files")))
        {
            List<TorrentFile> fileList = new ArrayList<TorrentFile>();
            BList filesBList = (BList) info.find(new BByteString("files"));

            Iterator<IBencodable> fileBDicts = filesBList.getIterator();
            while (fileBDicts.hasNext())
            {
                Object fileObject = fileBDicts.next();
                if (fileObject instanceof BDictionary)
                {
                    BDictionary fileBDict = (BDictionary) fileObject;
                    BList filePaths = (BList) fileBDict.find(new BByteString("path"));
                    BInt fileLength = (BInt) fileBDict.find(new BByteString("length"));
                    // Pick out each subdirectory as a string.
                    List<String> paths = new LinkedList<String>();
                    Iterator<IBencodable> filePathsIterator = filePaths.getIterator();
                    while (filePathsIterator.hasNext())
                        paths.add(filePathsIterator.next().toString());

                    fileList.add(new TorrentFile(fileLength.getValue(), paths));
                }
            }
            return fileList;
        }
        return null;
    }

    /**
     * @param dictionary root dictionary of torrent
     * @return announce-list: (optional) this is an extention to the official specification, offering
     * backwards-compatibility. (list of lists of strings).
     */
    private static List<String> parseAnnounceList(BDictionary dictionary)
    {
        if (null != dictionary.find(new BByteString("announce-list")))
        {
            List<String> announceUrls = new LinkedList<String>();

            BList announceList = (BList) dictionary.find(new BByteString("announce-list"));
            Iterator<IBencodable> subLists = announceList.getIterator();
            while (subLists.hasNext())
            {
                BList subList = (BList) subLists.next();
                Iterator<IBencodable> elements = subList.getIterator();
                while (elements.hasNext())
                {
                    // Assume that each element is a BByteString
                    BByteString tracker = (BByteString) elements.next();
                    announceUrls.add(tracker.toString());
                }
            }
            return announceUrls;
        } else
        {
            return null;
        }
    }
}
