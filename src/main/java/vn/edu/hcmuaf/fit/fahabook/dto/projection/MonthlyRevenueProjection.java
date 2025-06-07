package vn.edu.hcmuaf.fit.fahabook.dto.projection;

import java.math.BigDecimal;

public interface MonthlyRevenueProjection {
    Integer getMonth();

    BigDecimal getRevenue();
}
