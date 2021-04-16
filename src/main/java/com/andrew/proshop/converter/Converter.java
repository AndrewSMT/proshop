package com.andrew.proshop.converter;

import java.util.Collection;


public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

    void convert(SOURCE source, TARGET target);

    Collection<TARGET> convert(Collection<SOURCE> source);
}
