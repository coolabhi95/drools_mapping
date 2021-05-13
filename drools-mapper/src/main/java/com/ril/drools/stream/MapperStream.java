package com.ril.drools.stream;

import com.google.gson.JsonElement;
import com.ril.drools.utils.DroolsObjectUtils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MapperStream<T> extends StreamWrapper<T> {
  private Stream<T> stream;

  public MapperStream(Stream<T> stream) {
    super(stream);
    this.stream = new StreamWrapper<>(stream);
  }

  @Override
  public MapperStream<T> filter(Predicate<? super T> predicate) {
    stream = stream.filter(predicate);
    return this;
  }

  public Stream<Map.Entry<String, JsonElement>> mapStream(String path) {
    return stream
        .findAny()
        .map(value -> DroolsObjectUtils.getOptionalValue(path, (JsonElement) value))
        .map(i -> i.get())
        .map(i -> i.getAsJsonObject())
        .get()
        .entrySet()
        .stream();
  }

  @Override
  public Optional<T> findAny() {
    return stream.findAny();
  }

    }

