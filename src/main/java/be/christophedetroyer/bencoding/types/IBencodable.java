package be.christophedetroyer.bencoding.types;

/**
 * Created by christophe on 16.01.15.
 */
public interface IBencodable
{
    /**
     * Returns the byte representation of the bencoded object.
     * @return byte representation of the bencoded object.
     */
    byte[] bencode();

    /**
     * Returns string representation of bencoded object.
     * @return string representation of bencoded object.
     */
    String bencodedString();
}
