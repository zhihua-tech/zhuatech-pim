/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pim.workbench;

import static cn.zhuatech.pim.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);

    keys(r, "fields", "imageIds", "availableLocales", "channels");
    Map<String, String> fields = new HashMap<>();
    for (var f : rows(r, "fields", 0, 100)) {
      keys(f, "id", "value");
      String id = id(f, "id");
      Object value = f.get("value");
      if (!(value instanceof String s) || s.length() > 4000)
        throw new IllegalArgumentException("属性值须为4000字符内文本");
      if (fields.putIfAbsent(id, s.strip()) != null) throw new IllegalArgumentException("属性重复");
    }
    var images = strings(r, "imageIds", 0, 100);
    var locales = strings(r, "availableLocales", 0, 100);
    if (new HashSet<>(images).size() != images.size()
        || new HashSet<>(locales).size() != locales.size())
      throw new IllegalArgumentException("图片或语言重复");
    Set<String> ids = new HashSet<>();
    List<Map<String, Object>> out = new ArrayList<>();
    int ready = 0;
    for (var c : rows(r, "channels", 1, 50)) {
      keys(c, "id", "requiredFields", "minimumImages", "requiredLocales");
      String id = id(c, "id");
      unique(ids, id);
      var required = strings(c, "requiredFields", 0, 100);
      var requiredLocales = strings(c, "requiredLocales", 0, 100);
      if (new HashSet<>(required).size() != required.size()
          || new HashSet<>(requiredLocales).size() != requiredLocales.size())
        throw new IllegalArgumentException("渠道规则重复");
      int minimum = integer(c, "minimumImages", 0, 100);
      var missing =
          required.stream().filter(k -> !fields.containsKey(k) || fields.get(k).isBlank()).toList();
      var missingLocales = requiredLocales.stream().filter(k -> !locales.contains(k)).toList();
      boolean complete = missing.isEmpty() && missingLocales.isEmpty() && images.size() >= minimum;
      if (complete) ready++;
      out.add(
          Map.of(
              "id",
              id,
              "contentComplete",
              complete,
              "missingFields",
              missing,
              "missingLocales",
              missingLocales,
              "missingImageCount",
              Math.max(0, minimum - images.size())));
    }
    return Map.of("readyChannels", ready, "channels", out, "automaticPublication", false);
  }
}
