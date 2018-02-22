package com.quran.tajweed.exporter;

import com.quran.tajweed.model.Result;
import com.quran.tajweed.model.ResultType;
import com.quran.tajweed.model.TwoPartResult;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;

public class SvgExporter implements Exporter {
  private static final int MAXIMUM_WIDTH = 1000;

  private Font font;

  @Override
  public void export(String ayah, List<Result> results) {
    AttributedString attributedString = new AttributedString(ayah);
    attributedString.addAttribute(TextAttribute.FONT, font);

    writeSvg(ayah);

    for (Result result : results) {
      int start = result.getMinimumStartingPosition();
      if (result instanceof TwoPartResult) {
        TwoPartResult twoPartResult = (TwoPartResult) result;
      }
    }

    //writeImage(attributedString);
  }

  @Override
  public void onOutputStarted() {
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Scheherazade-Bold.ttf"));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
    font = font.deriveFont(75f);
  }

  private void writeSvg(String ayah){
    SVGGraphics2D g2 = new SVGGraphics2D(200, 200);
    g2.setFont(font);

    g2.drawString(ayah,100,100);
    String svgElement = g2.getSVGElement();

    System.out.println(svgElement);

    try (PrintStream out = new PrintStream(new FileOutputStream("filename.svg"))) {
      out.print(svgElement);
    }catch (Exception e){
      e.printStackTrace();
    }

  }

}

/*
<svg viewBox="0 0 100 132" version="1.1"
  xmlns="http://www.w3.org/2000/svg"
  xmlns:xlink="http://www.w3.org/1999/xlink">

  <g stroke="none" stroke-width="1" fill="none"
    fill-rule="evenodd">
    <a xlink:href="https://alligator.io">
      <polygon fill="#CA3030"
        points="..."></polygon>
    </a>
    <a xlink:href="https://www.w3.org/TR/SVG/"
	  target="_blank">
      <circle id="circle" fill="#30C3CA"
        cx="21.5" cy="13.5" r="516.5">
      </circle>
    </a>
    <a xlink:href="https://ponyfoo.com/"
	  target="_blank">
      <rect fill="#F79134"
        transform="..."
        x="..." y="..."
        width="94" height="94">
      </rect>
    </a>
    <a xlink:href="#code">
      <text
        transform="..."
        font-family="..." font-size="20"
        font-weight="normal" fill="#008F68">
          <tspan x="0" y="20">
            Some Text
          </tspan>
      </text>
    </a>
  </g>
</svg>
 */
