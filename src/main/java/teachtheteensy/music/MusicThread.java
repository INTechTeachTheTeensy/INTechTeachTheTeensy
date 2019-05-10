package teachtheteensy.music;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Thread démon qui gère quelle musique joue.<br/>
 * La musique courante est automatiquement jouée en boucle.
 */
public class MusicThread extends Thread {

    /**
     * La musique en train d'être jouée
     */
    private MusicHandle currentMusic;

    /**
     * Un buffer de lecture pour l'accélérer (on lit plus vite qu'on joue)
     */
    private byte[] buffer = new byte[1024*8*1024]; // 8MB

    /**
     * Est-ce qu'on doit arrêter la musique en cours?
     */
    private boolean stopRequested;

    public MusicThread() {
        super("Music thread");
        setDaemon(true); // le thread doit s'arrêter s'il se retrouve tout seul
    }

    @Override
    public void run() {
        System.out.println("Démarrage du thread de musique");

        // joue des sons en boucle
        while(!isInterrupted()) {
            // sortie son
            if(currentMusic != null) {

                // try-with-resource => le flux du fichier sera fermé qu'il y est une erreur ou non
                try(InputStream unbufferedStream = currentMusic.createStream()) {
                    System.out.println("[Music] Joue "+currentMusic);
                    // =========================================================================================
                    // ========= ADAPTION DU CODE DE TINYJUKEBOX FAIT PAR XAVIER "JGLRXAVPOK" NIOCHAUT  ========
                    // =                        donc oui c'est bien notre code                                 =
                    // ========= https://github.com/jglrxavpok/TinyJukebox =====================================
                    // =========================================================================================

                    BufferedInputStream sourceStream = new BufferedInputStream(unbufferedStream);
                    AudioInputStream input = AudioSystem.getAudioInputStream(sourceStream);
                    AudioFormat baseFormat = input.getFormat();
                    AudioFormat decodedFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            baseFormat.getSampleRate(),
                            16,
                            baseFormat.getChannels(),
                            baseFormat.getChannels() * 2,
                            baseFormat.getSampleRate(),
                            false
                    );
                    // on change le format pour utiliser un lisible
                    AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, input);

                    // sortie audio
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, decodedFormat);
                    SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(decodedFormat);
                    sourceDataLine.start();

                    // nombre d'octets lus par tentative de lecture, -1 si le flux est fini
                    int count;
                    do {
                        try {
                            count = din.read(buffer);
                            if(count >= 0) {
                                //Write data to the internal buffer of the data line where it will be delivered to the speaker.
                                sourceDataLine.write(buffer, 0, count);
                            }

                            if(stopRequested) {
                                stopRequested = false;
                                break; // breaks out of reading -> skips current music
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        // on lit tant qu'il y a quelque chose à lire
                    } while(count != -1);

                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void play(MusicHandle music) {
        currentMusic = music;
        if(currentMusic != null) {
            stopRequested = true; // permet de stopper la musique qui est déjà en train de jouer
        }
    }

    public MusicHandle getCurrentMusic() {
        return currentMusic;
    }

    /**
     * Arrêtes la musique en cours
     */
    public void stopMusic() {
        stopRequested = true;
        currentMusic = null;
    }

}
