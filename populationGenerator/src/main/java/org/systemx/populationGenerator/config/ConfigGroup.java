package org.systemx.populationGenerator.config;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

public class ConfigGroup {

    public static void write(Config config, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
        encoder.writeObject(config);
        encoder.close();
    }

    public static Config read(String filename) throws Exception {
        XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
        Config config = (Config)decoder.readObject();
        decoder.close();
        return config;
    }
	
}
