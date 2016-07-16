package JCF42;

import JCF42.data.HuffmanLeaf;
import JCF42.data.HuffmanNode;
import JCF42.data.HuffmanTree;
import JCF42.data.TreeAndText;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.startLogic2();
    }

    public void startLogic() {

        String input = "abcdefghijklmnopqrstuvwxyz";

//
//        char[] splittedText = input.toCharArray();
//        HashMap<Character, Integer> hashmap = new HashMap<>();
//
//        //add to hashmap and count how often it occurs
//        for (char text : splittedText) {
//            if (hashmap.containsKey(text)) {
//                hashmap.put(text, hashmap.get(text) + 1);
//            } else {
//                hashmap.put(text, 1);
//            }
//        }
//
//        //making a treemap that sorts on value
//        Comparator<Character> valueComparator =
//                new Comparator<Character>() {
//                    public int compare(Character k1, Character k2) {
//                        int compare =
//                                hashmap.get(k1).compareTo(hashmap.get(k2));
//                        if (compare == 0)
//                            return 1;
//                        else
//                            return compare;
//                    }
//                };
//        TreeMap<Character, Integer> sortedValues = new TreeMap<>(valueComparator);
//        sortedValues.putAll(hashmap);

        // we will assume that all our characters will have
        // code less than 256, for simplicity
        int[] charFreqs = new int[256];
        // read each character and record the frequencies
        for (char c : input.toCharArray())
            charFreqs[c]++;

        // build tree
        HuffmanTree tree = buildTree(charFreqs);


        Serializer s = new Serializer();
        try {
            s.saveToFile(tree,encode(tree, input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(decode(s.readFromFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void startLogic2(){
//        String input = "abcdefghijklmnopqrstuvwxyz aaaaaaaaaaaaaaaaa";
        String input = "Een kleine zin met wat karakters ";

        char[] splittedText = input.toCharArray();
        HashMap<Character, Integer> hashmap = new HashMap<>();

        //add to hashmap and count how often it occurs
        for (char text : splittedText) {
            if (hashmap.containsKey(text)) {
                hashmap.put(text, hashmap.get(text) + 1);
            } else {
                hashmap.put(text, 1);
            }
        }

        //making a treemap that sorts on value
        Comparator<Character> valueComparator =
                new Comparator<Character>() {
                    public int compare(Character k1, Character k2) {
                        int compare = hashmap.get(k1).compareTo(hashmap.get(k2));

                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };
        TreeMap<Character, Integer> sortedValues = new TreeMap<>(valueComparator);
        sortedValues.putAll(hashmap);



        HuffmanTree tree = buildTreeHashmap(sortedValues);

        Serializer s = new Serializer();
        try {
            s.saveToFileBitset(tree,encode(tree, input), input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(decode(s.readFromFileBitset()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(HuffmanTree tree, String text) throws Exception {
        HashMap<Character, String> values = generateCodes(tree, new StringBuffer(), new HashMap());
        String returnValue = "";

        char[] textArray = text.toCharArray();

        for (char c : textArray) {

            if (values.containsKey(c)) {
                returnValue += values.get(c);
            } else {
                throw new Exception("The huffmantree does not contain all proper characters, in this case " + c);
            }

        }
        return returnValue;

    }

    public String decode(TreeAndText treeAndText) throws Exception {

        HuffmanTree tree = treeAndText.getHuffmanTree();
        String text = treeAndText.getText();
        String returnValue = "";

        HuffmanTree currentNode = tree;
//        int counter2 = 0;
        for (char c : text.toCharArray() ) {

            if( returnValue.length()<treeAndText.getTextLength()) {

                if (currentNode instanceof HuffmanNode) {
                    if (c == '1') {
                        currentNode = ((HuffmanNode) currentNode).right;
                    } else if (c == '0') {
                        currentNode = ((HuffmanNode) currentNode).left;
                    }
                } else if (currentNode instanceof HuffmanLeaf) {
                    returnValue += ((HuffmanLeaf) currentNode).value;
                    currentNode = tree;

                    if (currentNode instanceof HuffmanNode) {
                        if (c == '1') {
                            currentNode = ((HuffmanNode) currentNode).right;
                        } else if (c == '0') {
                            currentNode = ((HuffmanNode) currentNode).left;
                        }
                    }
                }

            }
            counter2++;

        }
        return returnValue;

    }

    // input is an array of frequencies, indexed by character code
    public HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char) i));

        if (trees.size() > 0) {
            // loop until there is only one tree left
            while (trees.size() > 1) {
                // two trees with least frequency
                HuffmanTree a = trees.poll();
                HuffmanTree b = trees.poll();

                // put into new node and re-insert into queue
                trees.offer(new HuffmanNode(a, b));
            }
        }
        return trees.poll();
    }

    public HuffmanTree buildTreeHashmap(TreeMap<Character,Integer> charFreqs) {

        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        for(Map.Entry e : charFreqs.entrySet()) {
            trees.offer(new HuffmanLeaf((Integer) e.getValue(), (char) e.getKey()));
        }

        if (trees.size() > 0) {
            while (trees.size() > 1) {
                HuffmanTree a = trees.poll();
                HuffmanTree b = trees.poll();

                trees.offer(new HuffmanNode(a, b));
            }
        }
        return trees.poll();
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);

            // traverse right]
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public HashMap<Character, String> generateCodes(HuffmanTree tree, StringBuffer prefix, HashMap codes) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
            codes.put(leaf.value, prefix.toString());

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;

            // traverse left
            prefix.append('0');
            generateCodes(node.left, prefix, codes);
            prefix.deleteCharAt(prefix.length() - 1);

            // traverse right]
            prefix.append('1');
            generateCodes(node.right, prefix, codes);
            prefix.deleteCharAt(prefix.length() - 1);
        }
        return codes;
    }







}
