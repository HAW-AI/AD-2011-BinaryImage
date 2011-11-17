/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adp2.application;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author sebastian
 */
public class BlobFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if(file.isDirectory()) return true;
        
        String s = file.getName();
        int extensionStart = s.lastIndexOf(".");
        
        if (extensionStart > 0 && extensionStart < s.length() - 1) {
            String extension = s.substring(extensionStart + 1).toLowerCase();
            if (extension.equals("blob")) return true;
            else return false;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return ".blob";
    }
    
}
