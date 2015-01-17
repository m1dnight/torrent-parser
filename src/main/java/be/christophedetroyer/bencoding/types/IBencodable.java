package be.christophedetroyer.bencoding.types;

/**
 * Created by christophe on 16.01.15.
 */
public interface IBencodable
{
    byte[] bencode();

    String bencodedString();
}
