package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicPriceDTO {

    private String id;

    @NotBlank
    @Length(max = 255, message = "max length type maybe 255")
    private String type;

    @NotBlank
    @PositiveOrZero
    @Digits(integer = 9, fraction = 2, message = "price maybe no more than two decimal places")
    @DecimalMin(value = "0.01", inclusive = false, message = "minimum price maybe 0.01")
    private BigDecimal price;
}
