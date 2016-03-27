package JCF42.data;

/**
 * Created by wouter on 27-3-2016.
 */
public class TreeAndText {
    HuffmanTree huffmanTree;
    String text;

    public String getText() {
        return text;
    }

    public HuffmanTree getHuffmanTree() {

        return huffmanTree;
    }

    public TreeAndText(HuffmanTree huffmanTree, String text) {

        this.huffmanTree = huffmanTree;
        this.text = text;
    }
}
