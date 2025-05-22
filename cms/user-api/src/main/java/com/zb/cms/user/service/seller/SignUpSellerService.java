package com.zb.cms.user.service.seller;

import com.zb.cms.user.domain.SignUpForm;
import com.zb.cms.user.domain.model.Seller;
import com.zb.cms.user.domain.repository.SellerRepository;
import com.zb.cms.user.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.zb.cms.user.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class SignUpSellerService {
    private final SellerRepository sellerRepository;

    public Seller signUp(SignUpForm form) {
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (seller.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        }

        if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(CODE_NOT_MATCH);
        }

        if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(DATE_EXPIRED);
        }

        seller.setVerify(true);
    }

    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        seller.setVerificationCode(verificationCode);
        seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        return seller.getVerifyExpiredAt();

    }
}
