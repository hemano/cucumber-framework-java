package hemano.utils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.base.Throwables;
import hemano.utils.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by hemantojha on 02/09/16.
 */
public class PropertyReader extends MyPropertyReader {

    static Path filePath;

    public PropertyReader()
    {
        super( "mPoint.properties" );
    }

    public static Map readParameters( String resourcePath )
    {
        try
        {
            URL url = FileUtils.locateFromClasspath( resourcePath );
            YamlReader reader = new YamlReader( new FileReader( Paths.get( url.toURI() ).toFile() ) );
            Object object = reader.read();
            return ( Map ) object;
        } catch( FileNotFoundException | YamlException e )
        {
            Throwables.propagate( new Exception( e ) );
        } catch( URISyntaxException e )
        {
            e.printStackTrace();
        }
        return null;
    }

}
