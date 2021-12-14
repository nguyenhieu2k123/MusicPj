/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIProcessing;

import com.google.gson.JsonArray;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import musicpj.runApp;

/**
 *
 * @author nguyenhieu
 */
public class handleEvent {
    Vector vtHeader = new Vector();
    Vector vtData = new Vector();
//    public static int destPort = 1234;
//    public static String hostname = "localhost";
    
    public void displaySongData(String query, DefaultTableModel tbList) {
        
        JsonArray listSong = app.getSongDataFromAPI(query);
        
    }

    public String songSelected(int index) {
        Vector select = (Vector) vtData.get(index);
        String id = (String) select.get(0);
        return id;
    }

}
