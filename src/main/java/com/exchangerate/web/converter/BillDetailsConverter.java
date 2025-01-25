package com.exchangerate.web.converter;

import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.CalculatedBillDetail;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.web.dto.bill.BillDetailRequestDTO;
import com.exchangerate.web.dto.bill.BillDetailResponseDTO;
import com.exchangerate.web.dto.bill.CurrencyCode;

public final class BillDetailsConverter {
    private BillDetailsConverter() {};

    public static BillDetail toBillDetail (BillDetailRequestDTO billDetailDTO) {
        return BillDetail.builder()
                .base(billDetailDTO.getBase())
                .items(billDetailDTO.getItems())
                .totalAmount(billDetailDTO.getTotalAmount())
                .target(billDetailDTO.getTarget())
                .userType(toUserType(billDetailDTO.getUserType().name()))
                .tenure(billDetailDTO.getTenure())
                .build();
    }

    public static BillDetailResponseDTO toBillDetailResponseDTO(CalculatedBillDetail calculatedBillDetail) {
        return BillDetailResponseDTO.builder()
                .netAmount(calculatedBillDetail.netAmount())
                .targetCurrency(toDTOCurrencyCode(calculatedBillDetail.targetCurrency().name()))
                .build();
    }

    private static CurrencyCode toDTOCurrencyCode(String strCode) {
        return CurrencyCode.valueOf(strCode.toUpperCase());
    }
    private static UserType toUserType(String userType) {
        return UserType.valueOf(userType.toUpperCase());
    }
}
