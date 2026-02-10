package com.store.state;

import com.store.entity.Order;
import org.springframework.stereotype.Component;

/**
 * Terminal state — không chuyển tiếp.
 */
@Component
public class DeliveredState extends AbstractOrderState {
}
