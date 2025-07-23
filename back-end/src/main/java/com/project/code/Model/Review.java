package com.project.code.Model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @NotNull(message = "Customer cannot be null")
    private Long customerId;

    @NotNull(message = "Product cannot be null")
    private Long productId;

    @NotNull(message = "Store cannot be null")
    private  Long storeId;

    @NotNull(message = "Rating cannot be null")
    private Integer rating;

    private  String comment;

}
