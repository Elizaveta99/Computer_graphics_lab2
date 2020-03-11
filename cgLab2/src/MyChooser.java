
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.bmp.BmpHeaderDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.gif.GifHeaderDirectory;
import com.drew.metadata.jfif.JfifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.pcx.PcxDirectory;
import com.drew.metadata.png.PngDirectory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyChooser extends JFileChooser {
        

    @Override
    public void approveSelection() {        
        String fullFilename = this.getSelectedFile().getAbsolutePath().toString(); 
        Path pathToAFile = Paths.get(fullFilename);
        String filename = pathToAFile.getFileName().toString();
        Main.dataList.add(new Vector<Object>());
        Main.dataList.get(Main.cnt).add(filename);
                try 
                {
                    Metadata metadata = ImageMetadataReader.readMetadata(this.getSelectedFile());
                    ParseMetadata.parseMetadata(metadata, filename);                 
                } catch (ImageProcessingException e) {
                    print(e);
                } catch (IOException e) {
                    print(e);
                } catch (InterruptedException ex) {
            Logger.getLogger(MyChooser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MetadataException ex) {
            Logger.getLogger(MyChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.dm.addRow(Main.dataList.get(Main.cnt));  
        Main.cnt++;
        super.approveSelection();
    }

    @Override
    public void cancelSelection() {
        super.cancelSelection();
    }

    private void print(Exception exception)
    {    }
    
    
}
