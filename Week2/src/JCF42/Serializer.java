package JCF42;

import JCF42.data.HuffmanTree;
import JCF42.data.TreeAndText;

import java.io.*;

/**
 * Created by wouter on 27-3-2016.
 */
public class Serializer {

    public void saveToFile(HuffmanTree tree, String compressed) {


        String strFilePath = "compressedFile.txt";

        try {
            //create FileOutputStream object
            FileOutputStream fos = new FileOutputStream(strFilePath);
            ObjectOutputStream dos = new ObjectOutputStream(fos);

            //Writing the tree to the file
            dos.writeObject(tree);
            //Writing a character for certainty that the tree has ended (maybe i'm paranoid)
            dos.writeChar('2');

            //going through the string and making the charactervalues into actual bytes
            for (char c : compressed.toCharArray()) {
                if (c == '1')
                    dos.writeBoolean(true);
                else if (c == '0')
                    dos.writeBoolean(false);
            }
            dos.close();

        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }

    }
    public TreeAndText readFromFile() throws Exception {


        String strFilePath = "compressedFile.txt";
        HuffmanTree tree=null;
        String returnValue="";
        try {
            //create FileOutputStream object
            FileInputStream fos = new FileInputStream(strFilePath);
            ObjectInputStream dos = new ObjectInputStream(fos);

            //Writing the tree to the file
            tree = (HuffmanTree)dos.readObject();
            //Writing a character for certainty that the tree has ended (maybe i'm paranoid)
            if(dos.readChar()!='2'){
                throw new Exception("The tree was not properly closed");
            };

            while(dos.available()>0){
                if(dos.readBoolean()){
                    returnValue+='1';
                }else{
                    returnValue+='0';
                }
            }
            dos.close();

        } catch (Exception e) {
            System.out.println("IOException : " + e);
        }
        return new TreeAndText(tree,returnValue);

    }


}
