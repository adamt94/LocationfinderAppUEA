package com.activity.adam.locationfinder;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adam on 29-Dec-15.
 */
 public class Database {
    //contains all the data of uea locations,buildings etc
    private static ArrayList<MapData> data;

    static BufferedReader reader;

    public Database(AssetManager am){
        data = new ArrayList<>();
        getinfo(am);
    }

    //reads all the data from excel
    public static void getinfo(AssetManager am){
        String line ="";

        try {
            //create file reader

            InputStream stream = am.open("uea-map-data.tsv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            //Read the CSV file header to skip it
            reader.readLine();



            //Read the file line by line starting from the second line
            while ((line = reader.readLine()) != null) {

                //Get all tokens available in line
                String[] tokens = line.split("\t");


                if (tokens.length > 0) {
                    MapData md = new MapData(tokens[0], tokens[1], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[2]), tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9]);

                    data.add(md);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }



    }



    public ArrayList<MapData> getData() {
        return data;
    }

    public void setData(ArrayList<MapData> data) {
        this.data = data;
    }
}