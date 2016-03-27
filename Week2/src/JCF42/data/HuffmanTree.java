package JCF42.data;

import java.io.Serializable;

/**
 * Created by wouter on 27-3-2016.
 */
public abstract class HuffmanTree implements Comparable<HuffmanTree>, Serializable {
    public final int frequency; // the frequency of this tree

    public HuffmanTree(int freq) {
        frequency = freq;
    }

    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}