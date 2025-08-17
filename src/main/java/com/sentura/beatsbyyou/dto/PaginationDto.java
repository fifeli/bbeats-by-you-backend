package com.sentura.beatsbyyou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationDto {

    private int pageNumber;
    private int rowsPerPage;
    private int totalPages;
    private Object pagedData;
}
