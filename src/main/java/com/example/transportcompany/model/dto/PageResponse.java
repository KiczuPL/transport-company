package com.example.transportcompany.model.dto;

import com.example.transportcompany.model.dao.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PageResponse<T> {
    int pageSize;
    int pageNumber;
    int maxPages;

    List<T> data;
}
