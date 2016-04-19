package se.crisp.example.cleancode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;

public abstract class XmlPropertiesProvider {
    abstract void load(Properties var1, InputStream var2) throws IOException, InvalidPropertiesFormatException;

    abstract void store(Properties var1, OutputStream var2, String var3, String var4) throws IOException;
}
