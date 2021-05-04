package com.main.app.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class Entities<T> {

    private List<T> entities = new ArrayList<T>();
    private long total;
    private long totalPages;
    private Map<T, T> children;

}
