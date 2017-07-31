package hemano.utils.io;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import hemano.utils.misc.Constants;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.openqa.selenium.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by hemantojha on 25/09/16.
 */
public final class FileUtils
{

    //region FileUtils - Variables Declaration and Initialization Section.

    /**
     * The extension separator character.
     */
    public static final char EXTENSION_SEPARATOR = Constants.PERIOD_CHAR;

    private static final Logger logger = LoggerFactory.getLogger( FileUtils.class );

    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = Constants.SLASH_CHAR;

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = Constants.ESCAPE_CHAR;

    /** Constant for parsing numbers in hex format. */
    private static final int HEX = 16;

    /** Constant for the file URL protocol.*/
    private static final String PROTOCOL_FILE = "file";

    /** Constant for the file URL protocol */
    private static final String FILE_SCHEME = "file:";

    //endregion


    //region FileUtils - Constructor Methods Section

    private FileUtils() throws IllegalAccessException
    {
        throw new IllegalAccessException( "Utility class should not be constructed" );
    }

    //endregion


    //region FileUtils - Public Methods Section

    public static BigInteger fileSizeAsBigInteger(Path value ) throws IOException
    {
        long size = Files.size( value );
        return NumberUtils.createBigInteger( String.valueOf( value ) );
    }
    public static long fileSize( Path value ) throws IOException
    {
        return Files.size( value );
    }

    /**
     * Gets the extension of a filename.
     * <p>
     * This method returns the textual part of the filename after the last dot.
     * There must be no directory separator after the dot.
     * <pre>
     *     foo.txt      --> "txt"
     *     a/b/c.jpg    --> "jpg"
     *     a/b.txt/c    --> ""
     *     a/b/c        --> ""
     * </pre>
     * <p>
     * The output will be the same irrespective of the machine that the code is running on.
     *
     * @param file the file Path to retrieve the extension of.
     *
     * @return the extension of the file or an empty string if none exists or {@code null} if the filename is {@code null}.
     *
     * @see Path#getFileName()
     */
    public static String getExtension( Path file )
    {
        if ( file == null )
        {
            return null;
        }
        int index = indexOfExtension( file.toString() );
        if ( index == - 1 )
        {
            return "";
        }
        else
        {
            return file.toString().substring( index + 1 );
        }
    }

    /**
     *
     * @param folder  The folder to scan
     * @param glob    string glob pattern {@code "*.{java,class,jar}"}
     *
     * @return        a list of files matching glob
     *
     * @throws IOException
     */
    public static List<Path> scanDirectoryValidFiles(Path folder, String glob ) throws IOException
    {
        Preconditions.checkArgument( Files.exists( folder ), "The given folder \"%s\" does not exists", folder );
        Preconditions.checkArgument( Files.isDirectory( folder ), "The given path \"%s\" is not a directory/folder", folder );
        List<Path> files = Lists.newLinkedList();

        try ( DirectoryStream<Path> stream = Files.newDirectoryStream( folder, glob ) )
        {
            logger.debug( "iterate over the content of the directory \"{}\"", folder );
            for ( Path entry : stream )
            {
                files.add( entry.getFileName() );
            }

            return files;
        }
        catch ( IOException e )
        {
            logger.error( e.getMessage() );
            throw e;
        }
    }

    /**
     * Scans the directory using the glob pattern passed
     * as parameter.
     * @param folder directory to scan
     */
    public static List<Path> scanDirectoryValidFiles( Path folder, DirectoryStream.Filter<? super Path> filter ) throws IOException
    {
        Preconditions.checkArgument( Files.exists( folder ), "The given folder \"%s\" does not exists", folder );
        Preconditions.checkArgument( Files.isDirectory( folder ), "The given path \"%s\" is not a directory/folder", folder );
        List<Path> files = Lists.newLinkedList();

        try ( DirectoryStream<Path> stream = Files.newDirectoryStream( folder, filter ) )
        {
            logger.debug( "iterate over the content of the directory \"{}\"", folder );
            for ( Path entry : stream )
            {
                files.add( entry );
            }

            return files;
        }
        catch ( IOException e )
        {
            logger.error( e.getMessage() );
            throw e;
        }
    }

    public static int createNullFile( Path file, long size ) throws IOException, InterruptedException
    {
        if( Platform.getCurrent().equals( Platform.LINUX ) || Platform.getCurrent().equals( Platform.MAC ) )
        {
            final String CMD = String.format( "dd if=/dev/zero of=%s count=1024 bs=%d", file, size / 1024 );
            Process process = Runtime.getRuntime().exec( CMD );
            BufferedReader input = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
            String processBuffer;
            while ( ( processBuffer = input.readLine() ) != null )
            {
                logger.debug( "create null file: {}", processBuffer );
            }
            input.close();
            return process.waitFor();
        }

        throw new NotImplementedException();
    }

    public static int createTextFile( Path file, long size ) throws IOException, InterruptedException
    {
        if( Platform.getCurrent().equals( Platform.LINUX ) || Platform.getCurrent().equals( Platform.MAC ) )
        {
            final String CMD = String.format( "dd if=/dev/urandom of=%s count=1024 bs=%d", file, size / 1024 );
            Process process = Runtime.getRuntime().exec( CMD );
            BufferedReader input = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
            String processBuffer;
            while ( ( processBuffer = input.readLine() ) != null )
            {
                logger.debug( "create text file: {}", processBuffer );
            }
            input.close();
            return process.waitFor();

        }

        throw new NotImplementedException();
    }

    public static StrBuilder readFileWithFileSizeBuffer(Path file ) throws IOException
    {
        FileChannel inChannel = null;
        RandomAccessFile aFile = null;
        StrBuilder builder = new StrBuilder();
        try
        {
            aFile = new RandomAccessFile( file.toString(), "r" );
            inChannel = aFile.getChannel();
            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate( ( int ) fileSize );
            inChannel.read( buffer );
            buffer.flip();
            for ( int i = 0; i < fileSize; i++ )
            {
                builder.append( ( char ) buffer.get() );
            }

            return builder.trim();
        }
        finally
        {
            if( null != inChannel ) inChannel.close();
            if( null != aFile ) aFile.close();
        }
    }

    public static void copyFileToDestination( Path src, Path destination ) throws IOException
    {
        Files.copy( src, destination, StandardCopyOption.REPLACE_EXISTING );
    }

    /**
     * Tries to find a resource with the given name in the classpath.
     *
     * @param resourceName the name of the resource
     *
     * @return the URL to the found resource or <b>null</b> if the resource cannot be found
     */
    public static URL locateFromClasspath(String resourceName )
    {
        URL url = null;

        logger.debug( "attempt to load from the context classpath" );
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if ( loader != null )
        {
            url = loader.getResource( resourceName );
            if ( url != null )
            {
                logger.debug( "Loading configuration from the context classpath (" + resourceName + ")" );
            }
        }

        logger.debug( "attempt to load from the system classpath" );
        if ( url == null )
        {
            url = ClassLoader.getSystemResource( resourceName );

            if ( url != null )
            {
                logger.debug( "Loading configuration from the system classpath (" + resourceName + ")" );
            }
        }
        return url;
    }

    public static URL urlFromPath( Path path ) throws IOException
    {
        Preconditions.checkArgument( path != null, "Path cannot be null" );
        return path.toUri().toURL();
    }

    /**
     * Extract the file name from the specified URL.
     *
     * @param url the URL from which to extract the file name
     * @return the extracted file name
     */
    public static Path pathFromURL( URL url ) throws URISyntaxException
    {
        return Paths.get( url.toURI() ).getFileName();
    }

    /**
     * Returns the index of the last extension separator character, which is a dot.
     * <p>
     * This method also checks that there is no directory separator after the last dot.
     * To do this it uses {@link #indexOfLastSeparator(String)} which will
     * handle a file in either Unix or Windows format.
     * <p>
     * The output will be the same irrespective of the machine that the code is running on.
     *
     * @param filename  the filename to find the last path separator in, null returns -1
     * @return the index of the last separator character, or -1 if there
     * is no such character
     */
    private static int indexOfExtension( String filename )
    {
        if ( filename == null )
        {
            return - 1;
        }
        int extensionPos = filename.lastIndexOf( EXTENSION_SEPARATOR );
        int lastSeparator = indexOfLastSeparator( filename );
        return lastSeparator > extensionPos ? - 1 : extensionPos;
    }

    public static List<Path> fileList( Path directory ) throws IOException
    {
        List<Path> files = Lists.newArrayList();
        try ( DirectoryStream<Path> directoryStream = Files.newDirectoryStream( directory ) )
        {
            for ( Path path : directoryStream )
            {
                files.add( path );
            }
        }
        catch ( IOException ex )
        {
            logger.error( "Failed to list file", ex );
            throw ex;
        }
        return files;
    }

    //endregion


    //region FileUtils - Private Function Section

    /**
     * Returns the index of the last directory separator character.
     * <p>
     * This method will handle a file in either Unix or Windows format.
     * The position of the last forward or backslash is returned.
     * <p>
     * The output will be the same irrespective of the machine that the code is running on.
     *
     * @param filename  the filename to find the last path separator in, null returns -1
     * @return the index of the last separator character, or -1 if there
     * is no such character
     */
    private static int indexOfLastSeparator( String filename )
    {
        if ( filename == null )
        {
            return - 1;
        }
        int lastUnixPos = filename.lastIndexOf( UNIX_SEPARATOR );
        int lastWindowsPos = filename.lastIndexOf( WINDOWS_SEPARATOR );
        return Math.max( lastUnixPos, lastWindowsPos );
    }

    public static void closeQuietly( Closeable closeable )
    {
        if( null == closeable ) return;
        try
        {
            closeable.close();
        }
        catch ( IOException e )
        {
            logger.warn( "failed to close Closeable", e );
        }
    }

    public static void closeQuietly( AutoCloseable closeable )
    {
        if( null == closeable ) return;
        try
        {
            closeable.close();
        }
        catch ( Exception e )
        {
            logger.warn( "failed to close AutoCloseable", e );
        }
    }


    //endregion

    public static void deleteRecursive( Path path ) throws IOException
    {
        Files.walkFileTree( path, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs )
                    throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed( Path file, IOException exc ) throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( Path dir, IOException exc ) throws IOException
            {
                if ( exc == null )
                {
                    Files.delete( dir );
                    return FileVisitResult.CONTINUE;
                }
                else
                {
                    Throwables.propagate( new Exception( exc ) );
                }
                return null;
            }
        } );
    }

    public static void cleanDirectory( Path path ) throws IOException
    {
        Files.walkFileTree( path, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
                    throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed( Path file, IOException exc ) throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( Path dir, IOException exc ) throws IOException
            {
                if ( exc == null )
                {
                    if ( dir.equals( path ) )
                    {
                        return FileVisitResult.TERMINATE;
                    }
                    Files.delete( dir );
                    return FileVisitResult.CONTINUE;
                }
                else
                {
                    Throwables.propagate( new Exception( exc ) );
                }
                return null;
            }
        } );
    }

    public static Path searchFile( Path searchFile ) throws IOException
    {
        Search walk = new Search( searchFile );
        EnumSet<FileVisitOption> opts = EnumSet.of( FileVisitOption.FOLLOW_LINKS );
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for ( Path root : dirs )
        {
            if ( ! walk.found )
            {
                Files.walkFileTree( root, opts, Integer.MAX_VALUE, walk );
            }
        }

        if ( ! walk.found )
        {
            logger.info( "The file \"{}\" was not found!", searchFile );
        }

        return walk.getFoundFile();
    }

    public static Path searchFile( Path initialDirectory, Path searchFile ) throws IOException
    {
        Search walk = new Search( searchFile );
        EnumSet<FileVisitOption> opts = EnumSet.of( FileVisitOption.FOLLOW_LINKS );

        if ( ! walk.found )
        {
            Files.walkFileTree( initialDirectory, opts, Integer.MAX_VALUE, walk );
        }

        if ( ! walk.found )
        {
            logger.info( "The file \"{}\" was not found!", searchFile );
        }

        return walk.getFoundFile();
    }


    private static class Search implements FileVisitor {
        private final Path searchedFile;

        public boolean found;

        private Path foundFile = null;

        public Search(Path searchedFile) {
            this.searchedFile = searchedFile;
            this.found = false;
        }

        void search(Path file) throws IOException {
            Path name = file.getFileName();
            if (name != null && name.equals(searchedFile)) {
                logger.debug("Searched file was found: " + searchedFile + " in " + file.toRealPath().toString());
                foundFile = file;
                found = true;
            }
        }

        @Override
        public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
            logger.debug("Visited: \"{}\"", dir.toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attributes) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attributes) throws IOException {
            search((Path) file);
            if (!found) {
                return FileVisitResult.CONTINUE;
            } else {
                return FileVisitResult.TERMINATE;
            }
        }

        @Override
        public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        public Path getFoundFile() {
            return foundFile;
        }

    }

}
