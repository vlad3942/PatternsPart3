package ru.ssau.patterns_example.state;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public interface State {
    ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    String getFileName();
    default BufferedImage getStateImage() throws IOException {
        return ImageIO.read(Objects.requireNonNull(CLASS_LOADER.getResource(getFileName())));
    }
}
