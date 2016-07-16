package JCF42.data;

/**
 * Created by wouter on 27-3-2016.
 */
public class TreeAndText {
    HuffmanTree huffmanTree;
    String text;
    int textLength;

    public int getTextLength() {
        return textLength;
    }

    public String getText() {
        return text;
    }

    public HuffmanTree getHuffmanTree() {

        return huffmanTree;
    }

    public TreeAndText(HuffmanTree huffmanTree, String text, int textLength) {

        this.huffmanTree = huffmanTree;
        this.text = text;
        this.textLength = textLength;
    }
}
