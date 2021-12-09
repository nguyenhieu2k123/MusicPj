/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musicpj;

import Jwiki.*;
/**
 *
 * @author Rhylaw
 */
public class jwiki {
    
    public static void main(String[] args) {
        Jwiki jwiki = new Jwiki("Son Tung MTP");
        System.out.println("Title :"+jwiki.getDisplayTitle()); //get title
        System.out.println("Text : "+jwiki.getExtractText());  //get summary text
        System.out.println("Image : "+jwiki.getImageURL());    //get image URL
    }
    
}
