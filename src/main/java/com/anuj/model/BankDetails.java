package com.anuj.model;

//just a nornal call not entity


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {

    private String accountNumber;
    private String accountHolderName;
    //    private String bankName;
    private String ifscCode;

}
