package ru.koval;

import java.io.*;
import java.net.URL;
import java.util.HashMap;


public class GetSearchResultTest {


    private static final String FILE_NAME = "HtmlParse.txt";

    public static void main(String[] args) throws Exception {
      /*  //check args for URL as param
        if(args.length == 0 || args[0] == null){
            System.out.println("Error URL is empty");
            return;
        }

        String url = args[0];*/

        //create new file
        File file = new File(FILE_NAME); file.createNewFile();
        //save html to file to the local disk space
        try (InputStream is = new URL("simbirsoft.com").openStream();
             OutputStream out = new FileOutputStream(FILE_NAME) ) {
            int count;
            byte[] buff = new byte[64];
            while ((count = is.read(buff)) != -1) {
                out.write(buff, 0, count);
            }
        } catch (IOException e) {
            //print logs
            e.printStackTrace();
        }

        HashMap<String, Integer> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Integer count;
            while ((line = br.readLine()) != null) {
                //get words from string line
                String[] split = line.split("[ ,.;!?\n\r\t()\\[\\]]");
                for (String s : split) {
                    //get words for user see only
                    if (s.toUpperCase().matches("[А-Я]+")) {
                      count = result.get(s.toUpperCase());
                      if (count != null){
                      //word already exist
                          count = count + 1;
                          result.put(s.toUpperCase(), count);
                      } else {
                          //add new word
                          result.put(s.toUpperCase(), 1);
                      }

                    }
                }


            }
        }
        for (String s : result.keySet()) {
            System.out.println(s + " - " + result.get(s));
        }

    }

}

