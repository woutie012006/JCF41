package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import com.sun.deploy.util.ArrayUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {

    private static final String DEFAULT_TEXT =
            "Een, twee, drie, vier\n" +
                    "Hoedje van, hoedje van\n" +
                    "Een, twee, drie, vier\n" +
                    "Hoedje van papier\n"; //+//15
    //            "\n" +
//            "Heb je dan geen hoedje meer\n" +
//            "Maak er één van bordpapier\n" +
//            "Eén, twee, drie, vier\n" +
//            "Hoedje van papier\n" +//18
//            "\n" +
//            "Een, twee, drie, vier\n" +
//            "Hoedje van, hoedje van\n" +
//            "Een, twee, drie, vier\n" +
//            "Hoedje van papier\n" +//16
//            "\n" +
//            "En als het hoedje dan niet past\n" +
//            "Zetten we 't in de glazenkas\n" +
//            "Een, twee, drie, vier\n" +
//            "Hoedje van papier";//20
//            //64 woorden
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }

    @FXML
    private void aantalAction(ActionEvent event) {

        String text = taInput.getText();
        text = text.toLowerCase();
        String[] splittedText = text.split("[^a-zA-Z0-9-]+");
        Set set = new HashSet<>();

        System.out.println(splittedText);
        for (String s : splittedText) {
            System.out.println(s);
            set.add(s);
        }
        System.out.println("Set");
        set.forEach(System.out::println);

        taOutput.clear();
        taOutput.appendText(splittedText.length + "\n");
        taOutput.appendText(set.size() + "");

    }

    @FXML
    private void sorteerAction(ActionEvent event) {

        String text = taInput.getText();
        text = text.toLowerCase();

        String[] splittedText = text.split("[^a-zA-Z0-9-]+");
        Set set = new HashSet<>();
        for (String s : splittedText) {
            set.add(s);
        }
        TreeSet test = new TreeSet(set);
        Iterator it = test.descendingIterator();

        taOutput.clear();
        while (it.hasNext()) {
            taOutput.appendText(it.next() + "\n");
        }

    }

    @FXML
    private void frequentieAction(ActionEvent event) {

        String input = taInput.getText();
        input = input.toLowerCase();

        String[] splittedText = input.split("[^a-zA-Z0-9-]+");
        HashMap<String, Integer> hashmap = new HashMap<>();

        for (String text : splittedText) {
            if (hashmap.containsKey(text)) {
                hashmap.put(text, hashmap.get(text) + 1);
            } else {
                hashmap.put(text, 1);
            }
        }

        Comparator<String> valueComparator =
                new Comparator<String>() {
                    public int compare(String k1, String k2) {
                        int compare =
                                hashmap.get(k1).compareTo(hashmap.get(k2));
                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };
        TreeMap<String, Integer> test = new TreeMap<>(valueComparator);
        test.putAll(hashmap);

        taOutput.clear();
        for(Map.Entry b : test.entrySet()){
            taOutput.appendText(b.getValue() + " : " + b.getKey() + "\n");
            System.out.println(b.getValue() + " : " + b.getKey());
        }


    }

    @FXML
    private void concordatieAction(ActionEvent event) {

        String input = taInput.getText();
        input = input.toLowerCase();
        String[] lines = input.split("\n");
//        TreeMap<String, String> hashmap = new TreeMap<>();
//
//        for(int j=0;j<lines.length;j++) {
//            String[] splittedText = lines[j].split("[^a-zA-Z0-9-]+");
//
//            for (int i = 0; i < splittedText.length; i++) {
//                if (hashmap.containsKey(splittedText[i])) {
//                    hashmap.put(splittedText[i], hashmap.get(splittedText[i]) + ", " + (j+1));
//                } else {
//                    hashmap.put(splittedText[i], " " + (j+1) );
//                }
//            }
//        }
        TreeMap<String, Set> hashmap = new TreeMap<>();

        for(int j=0;j<lines.length;j++) {
            String[] splittedText = lines[j].split("[^a-zA-Z0-9-]+");

            for (int i = 0; i < splittedText.length; i++) {

                if (hashmap.containsKey(splittedText[i])) {
                    hashmap.get(splittedText[i]).add(j+1);
                    hashmap.put(splittedText[i], hashmap.get(splittedText[i]));
                } else {
                    TreeSet set = new TreeSet();
                    set.add(j+ 1);
                    hashmap.put(splittedText[i], set);
                }
            }
        }
        taOutput.clear();
        for(Map.Entry a : hashmap.entrySet() ){

            taOutput.appendText(a.getKey() + " : " );
            for(Integer line : (Set<Integer>)a.getValue()) {
                taOutput.appendText(line +",");
            }
            taOutput.appendText("\n");
        }


    }

}
