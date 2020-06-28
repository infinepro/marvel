package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicPriceDTO {

    private String id;

    @NotBlank
    private String type;

    @NotBlank
    @PositiveOrZero
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
}
