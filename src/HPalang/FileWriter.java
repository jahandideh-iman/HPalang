/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class FileWriter
{
    public void Write(String fileName, String data)
    {

        java.io.FileWriter out = null;
        try {
            out = new java.io.FileWriter(fileName);
            out.write(data);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
