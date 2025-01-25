package com.exchangerate.service.exchangerate;

import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.CalculatedBillDetail;

public interface ExchangeService {

    CalculatedBillDetail calculateExchangeRate (BillDetail billDetail);

}
