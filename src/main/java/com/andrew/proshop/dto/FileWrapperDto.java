package com.andrew.proshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileWrapperDto {
    private String filename;
    private byte[] data;
}
