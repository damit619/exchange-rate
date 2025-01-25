package com.exchangerate.web.resources;

import com.exchangerate.modle.bill.CalculatedBillDetail;
import com.exchangerate.service.exchangerate.ExchangeService;
import com.exchangerate.web.converter.BillDetailsConverter;
import com.exchangerate.web.dto.bill.BillDetailRequestDTO;
import com.exchangerate.web.dto.bill.BillDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.exchangerate.web.converter.BillDetailsConverter.toBillDetail;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/exchange-rates")
public class ExchangeRateResourceV1 {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateResourceV1.class);

    private final ExchangeService exchangeService;

    @PostMapping("/calculate")
    public ResponseEntity<BillDetailResponseDTO> calculateBill (@RequestBody BillDetailRequestDTO billDetailRequestDTO) {
        logger.info("Calculate bill request received.");
        CalculatedBillDetail billDetail = exchangeService.calculateExchangeRate(toBillDetail(billDetailRequestDTO));
        return ResponseEntity.ok(BillDetailsConverter.toBillDetailResponseDTO(billDetail));
    }
}
