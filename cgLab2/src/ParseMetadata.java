import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.bmp.BmpHeaderDirectory;
import com.drew.metadata.exif.ExifIFD0Descriptor;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.gif.GifHeaderDirectory;
import com.drew.metadata.jfif.JfifDirectory;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.pcx.PcxDirectory;
import com.drew.metadata.png.PngDescriptor;
import com.drew.metadata.png.PngDirectory;
import static java.lang.Thread.sleep;
import javax.swing.SwingUtilities;

public class ParseMetadata {
    public static void parseMetadata(Metadata metadata, String filename) throws InterruptedException, MetadataException 
    {        
        if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".tif"))
                    {
                        ExifIFD0Directory  directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                        String size = directory.getString(ExifIFD0Directory.TAG_IMAGE_WIDTH) + "x" + directory.getString(ExifIFD0Directory.TAG_IMAGE_HEIGHT) + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        String resolution = directory.getString(ExifIFD0Directory.TAG_X_RESOLUTION) + "x" + directory.getString(ExifIFD0Directory.TAG_Y_RESOLUTION) + " dpi";
                        Main.dataList.get(Main.cnt).add(resolution);
                        int depth_arr[] = directory.getIntArray(ExifIFD0Directory.TAG_BITS_PER_SAMPLE);
                        int depth_1 = 0;
                        for (int d: depth_arr)
                            depth_1 += d;
                        String depth = ((Integer)depth_1).toString();
                        Main.dataList.get(Main.cnt).add(depth);
                        ExifIFD0Descriptor descriptor = new ExifIFD0Descriptor(directory);
                        String compr = descriptor.getCompressionDescription();
                        Main.dataList.get(Main.cnt).add(compr);
                        
                    }
                    else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".gif"))
                    {
                        GifHeaderDirectory  directory = metadata.getFirstDirectoryOfType(GifHeaderDirectory.class);
                        String size = directory.getString(GifHeaderDirectory.TAG_IMAGE_WIDTH) + "x" + directory.getString(GifHeaderDirectory.TAG_IMAGE_HEIGHT) + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        Main.dataList.get(Main.cnt).add("-");
                        String depth_1 = directory.getString(GifHeaderDirectory.TAG_BITS_PER_PIXEL);
                        int depth_int_1 = Integer.parseInt(depth_1);
                        String depth_2 = directory.getString(GifHeaderDirectory.TAG_COLOR_TABLE_SIZE);
                        int depth_int_2 = Integer.parseInt(depth_2);
                        Main.dataList.get(Main.cnt).add(depth_int_1 * depth_int_2); 
                        Main.dataList.get(Main.cnt).add("LZW");
                        
                    }
                    else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".bmp"))
                    {                       
                        BmpHeaderDirectory  directory = metadata.getFirstDirectoryOfType(BmpHeaderDirectory.class);
                        String size = directory.getString(BmpHeaderDirectory.TAG_IMAGE_WIDTH) + "x" + directory.getString(BmpHeaderDirectory.TAG_IMAGE_HEIGHT) + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        Main.dataList.get(Main.cnt).add("-");
                        String depth_1 = directory.getString(BmpHeaderDirectory.TAG_BITS_PER_PIXEL);
                        Main.dataList.get(Main.cnt).add(depth_1);
                        String compr = directory.getCompression().toString();
                        Main.dataList.get(Main.cnt).add(compr);                        
                    }
                    else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".jpg"))
                    {                       
                        JpegDirectory  directory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
                        String size = directory.getString(JpegDirectory.TAG_IMAGE_WIDTH) + "x" + directory.getString(JpegDirectory.TAG_IMAGE_HEIGHT) + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        
                        String resolution = "-";
                        if (metadata.containsDirectoryOfType(JfifDirectory.class))
                        {
                            JfifDirectory dir = metadata.getFirstDirectoryOfType(JfifDirectory.class);
                            resolution = dir.getString(JfifDirectory.TAG_RESX) + "x" + dir.getString(JfifDirectory.TAG_RESY) + " dpi";
                        }
                        else 
                        {
                            ExifIFD0Directory dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                            resolution = dir.getString(ExifIFD0Directory.TAG_X_RESOLUTION) + "x" + dir.getString(ExifIFD0Directory.TAG_Y_RESOLUTION) + " dpi";
                        }      
                        Main.dataList.get(Main.cnt).add(resolution);
                        
                        String depth_1 = directory.getString(JpegDirectory.TAG_DATA_PRECISION);
                        int depth_int_1 = Integer.parseInt(depth_1);
                        String depth_2 = directory.getString(JpegDirectory.TAG_NUMBER_OF_COMPONENTS);
                        int depth_int_2 = Integer.parseInt(depth_2);
                        Main.dataList.get(Main.cnt).add(depth_int_1 * depth_int_2);

                        JpegDescriptor descriptor = new JpegDescriptor(directory);
                        String compr = descriptor.getImageCompressionTypeDescription();
                        Main.dataList.get(Main.cnt).add(compr);                        
                    }
                    else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".png"))
                    {                       
                        PngDirectory  directory = metadata.getFirstDirectoryOfType(PngDirectory.class);
                        String size = directory.getString(PngDirectory.TAG_IMAGE_WIDTH) + "x" + directory.getString(PngDirectory.TAG_IMAGE_HEIGHT) + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        
                        String resolution = "-";
                        if (directory.containsTag(PngDirectory.TAG_PIXELS_PER_UNIT_X) && directory.containsTag(PngDirectory.TAG_PIXELS_PER_UNIT_Y)) 
                        {
                            Integer resX = (int)(directory.getInt(PngDirectory.TAG_PIXELS_PER_UNIT_X) * 0.254);                  
                            Integer resY = (int)(directory.getInt(PngDirectory.TAG_PIXELS_PER_UNIT_Y) * 0.254);
                            resolution = resX.toString() + "x" + resY + " dpi";
                        }
                        Main.dataList.get(Main.cnt).add(resolution);

                        String depth_1 = directory.getString(PngDirectory.TAG_BITS_PER_SAMPLE);                        
                        Main.dataList.get(Main.cnt).add(depth_1);
                        
                        PngDescriptor descriptor = new PngDescriptor((PngDirectory) directory);
                        String compr = descriptor.getCompressionTypeDescription();
                        Main.dataList.get(Main.cnt).add(compr);                        
                    }
                    else if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".pcx"))
                    {                       
                        PcxDirectory  directory = metadata.getFirstDirectoryOfType(PcxDirectory.class);
                        Integer width = directory.getInt(PcxDirectory.TAG_XMAX) - directory.getInt(PcxDirectory.TAG_XMIN); 
                        Integer height = directory.getInt(PcxDirectory.TAG_YMAX) - directory.getInt(PcxDirectory.TAG_YMIN); 
                        String size = width + "x" + height + " px";
                        Main.dataList.get(Main.cnt).add(size);
                        
                        String resolution = directory.getString(PcxDirectory.TAG_HORIZONTAL_DPI) + "x" + directory.getString(PcxDirectory.TAG_VERTICAL_DPI) + " dpi";
                        Main.dataList.get(Main.cnt).add(resolution);          
                        
                        String depth_1 = directory.getString(PcxDirectory.TAG_BITS_PER_PIXEL);                        
                        Main.dataList.get(Main.cnt).add(depth_1);
                        
                        String compr = "RLE";
                        Main.dataList.get(Main.cnt).add(compr);
                        
                    } 
                    //print(metadata);
                    
    }
    
    private static void print(Metadata metadata)
    {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.print(' ');
        System.out.println();

        for (Directory directory : metadata.getDirectories()) {

            for (Tag tag : directory.getTags()) {
                System.out.println(tag.toString());
            }

            for (String error : directory.getErrors()) {
                System.out.println("ERROR: " + error);
            }
        }
    }
     
    private void print(Exception exception)
    {    }
    
}
