package com.andrew.proshop.converter;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;


public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET> {

    protected abstract TARGET generateTarget();

    @Override
    public TARGET convert(SOURCE source) {
        if (source == null) {
            return null;
        }
        TARGET result = generateTarget();
        convert(source, result);
        return result;
    }

    @Override
    public List<TARGET> convert(Collection<SOURCE> sources) {
        return sources.stream().map(this::convert).collect(toList());
    }
}
