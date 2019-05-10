package teachtheteensy.music;

import java.io.InputStream;

/**
 * Objet qui représente une musique
 */
public class MusicHandle {

    private final String name;
    private String extension;

    public MusicHandle(String name) {
        this(name, "mp3");
    }

    public MusicHandle(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }


    public InputStream createStream() {
        return MusicHandle.class.getResourceAsStream("/musics/"+name+"."+extension);
    }

    @Override
    public String toString() {
        return name;
    }
}
