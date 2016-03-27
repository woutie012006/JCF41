package JCF42.data;

/**
 * Created by wouter on 27-3-2016.
 */
public class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}