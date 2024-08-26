package com.hj00.prac.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVO {

    private Integer prodId;
    private LocalDateTime prodRegdate;
    private String prodEnddate;
    private String prodCategory;
    private String prodWriter;
    private String prodName;
    private Integer prodPrice;
    private Integer prodCount;
    private Integer prodDiscount;
    private String prodPurchaseYn;
    private String prodContent;
    private String prodComment;
}


