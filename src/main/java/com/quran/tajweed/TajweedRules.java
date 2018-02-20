package com.quran.tajweed;

import com.quran.tajweed.exporter.Exporter;
import com.quran.tajweed.exporter.ImageExporter;
import com.quran.tajweed.exporter.SvgExporter;
import com.quran.tajweed.exporter.TextExporter;
import com.quran.tajweed.model.Result;
import com.quran.tajweed.model.ResultUtil;
import com.quran.tajweed.model.TajweedRule;

import java.util.ArrayList;
import java.util.List;

public class TajweedRules {

  public static void main(String args[]) {

    // text uses uthmani text from tanzil
    String[] text = new String[]{
        "ٱلْحَمْدُ لِلَّـهِ رَبِّ ٱلْعَـٰلَمِينَ",  // 1:2
        "الٓمٓ", // 2:1
        "ذَٰلِكَ ٱلْكِتَـٰبُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ",  // 2:2
        "ثُمَّ بَعَثْنَـٰكُم مِّنۢ بَعْدِ مَوْتِكُمْ لَعَلَّكُمْ تَشْكُرُونَ",  // 2:56
        "كٓهيعٓصٓ", // 19:1
        " مَا يُجَـٰدِلُ فِىٓ ءَايَـٰتِ ٱللَّـهِ إِلَّا ٱلَّذِينَ كَفَرُوا۟ فَلَا يَغْرُرْكَ تَقَلُّبُهُمْ فِى ٱلْبِلَـٰدِ", // 40:4
        "تَرْمِيهِم بِحِجَارَةٍ مِّن سِجِّيلٍ",  // 105:4
        "تَبَّتْ يَدَآ أَبِى لَهَبٍ وَتَبَّ",  // 111:1
    };

    Exporter exporter = new TextExporter();
    exporter.onOutputStarted();

    Exporter image = new ImageExporter();
    image.onOutputStarted();

    Exporter svg = new SvgExporter();
    svg.onOutputStarted();

    List<TajweedRule> rules = TajweedRule.MADANI_RULES;
    for (String ayahText : text) {
      List<Result> results = new ArrayList<>();
      for (TajweedRule tajweedRule : rules) {
        results.addAll(tajweedRule.rule.checkAyah(ayahText));
      }
      ResultUtil.INSTANCE.sort(results);
      exporter.export(ayahText, results);
      image.export(ayahText,results);
      svg.export(ayahText,results);
    }
    exporter.onOutputCompleted();
  }
}
