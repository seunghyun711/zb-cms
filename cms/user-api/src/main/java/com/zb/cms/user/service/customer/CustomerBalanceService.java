package com.zb.cms.user.service.customer;

import com.zb.cms.user.domain.customer.ChangeBalanceForm;
import com.zb.cms.user.domain.model.Customer;
import com.zb.cms.user.domain.model.CustomerBalanceHistory;
import com.zb.cms.user.domain.repository.CustomerBalanceHistoryRepository;
import com.zb.cms.user.domain.repository.CustomerRepository;
import com.zb.cms.user.exception.CustomException;
import com.zb.cms.user.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {
    private final CustomerRepository customerRepository;
    private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;

    @Transactional
    public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form)
            throws CustomException {
        Customer c = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        CustomerBalanceHistory customerBalanceHistory =
                customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customerId)
                        .orElse(CustomerBalanceHistory.builder()
                                .changeMoney(0)
                                .currentMoney(0)
                                .customer(c)
                                .build());

        if (customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = CustomerBalanceHistory.builder()
                .changeMoney(form.getMoney())
                .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
                .customer(c)
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .build();

        c.setBalance(customerBalanceHistory.getCurrentMoney());
        return customerBalanceHistoryRepository.save(customerBalanceHistory);
    }
}
