package org.primetmu.primetmu_api.post;

import java.math.BigDecimal;
import java.util.UUID;

public record PostAdditionRequest(
        UUID userId,
        UUID imageId,
        String category,
        String itemType,
        String title,
        String description,
        String location,
        BigDecimal price) {
}
