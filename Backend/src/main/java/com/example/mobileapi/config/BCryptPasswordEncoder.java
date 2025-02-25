package com.example.mobileapi.config;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder {

    private static final int LOG_ROUNDS = 12; // Độ phức tạp của thuật toán

    /**
     * Băm mật khẩu sử dụng BCrypt.
     *
     * @param password Mật khẩu gốc.
     * @return Mật khẩu đã được băm.
     */
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Kiểm tra mật khẩu gốc với mật khẩu đã được băm.
     *
     * @param password       Mật khẩu gốc.
     * @param hashedPassword Mật khẩu đã được băm.
     * @return true nếu mật khẩu khớp, false nếu không.
     */
    public boolean matches(String password, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hashed password format");
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
}
