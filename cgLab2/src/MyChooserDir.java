import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyChooserDir extends JFileChooser {

    @Override
    public void approveSelection() {
        int count = 0;
        File file = new File(this.getSelectedFile().toString()); 
        for (File child : file.listFiles()) 
        {      
            String fullFilename = child.getAbsolutePath().toString(); 
            Path pathToAFile = Paths.get(fullFilename);
            String filename = pathToAFile.getFileName().toString();
            Main.dataList.add(new Vector<Object>());
            Main.dataList.get(Main.cnt).add(filename);
                try 
                {
                    Metadata metadata = ImageMetadataReader.readMetadata(child);
                    ParseMetadata.parseMetadata(metadata, filename); 
                    Main.cnt++;
                } catch (ImageProcessingException e) {
                    print(e);
                } catch (IOException e) {
                    print(e);
                } catch (InterruptedException ex) {
                Logger.getLogger(MyChooserDir.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MetadataException ex) {
                Logger.getLogger(MyChooserDir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        while (count < Main.cnt) 
        {
            Main.dm.addRow(Main.dataList.get(count));
            count++;
        }

        super.approveSelection();
    }

    @Override
    public void cancelSelection() {
        super.cancelSelection();
    }
     
    private void print(Exception exception)
    {    }
    
}
