package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzPreprocessor;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class BottomBandMrzPreprocessor implements MrzPreprocessor {

  @Override
  public File cropToMrzBand(File inputImageFile) {
    try {
      BufferedImage image = ImageIO.read(inputImageFile);
      if (image == null) throw new IllegalArgumentException("Not a readable image");

      int horizontal = 0;
      int vertical = (int) (image.getHeight() * 0.70);
      int width = image.getWidth();
      int height = image.getHeight() - vertical;

      BufferedImage mrzCrop = image.getSubimage(horizontal, vertical, width, height);

      File out = File.createTempFile("mrz-crop-", ".png");
      ImageIO.write(mrzCrop, "png", out);
      return out;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to crop MRZ band", exception);
    }
  }
}
