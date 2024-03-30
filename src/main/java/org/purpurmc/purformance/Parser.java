package org.purpurmc.purformance;

import java.util.Arrays;
import joptsimple.OptionParser;

public class Parser extends OptionParser {
    public static final OptionParser PARSER;

    static {
        PARSER = new Parser();
    }

    public Parser() {
        super();
        this.acceptsAll(Arrays.asList("nogui"), "Disables the graphical console");
    }
}
