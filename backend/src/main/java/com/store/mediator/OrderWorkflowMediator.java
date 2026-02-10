package com.store.mediator;

import com.store.entity.Order;
import com.store.factory.OrderProcessorFactory;
import com.store.chain.OrderValidationHandler;
import com.store.state.OrderStateContext;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Mediator — điều phối validation + state cho đơn hàng (không để controller gọi trực tiếp nhiều component).
 */
@Component
public class OrderWorkflowMediator {

    private final OrderProcessorFactory processorFactory;
    private final OrderStateContext stateContext;

    public OrderWorkflowMediator(OrderProcessorFactory processorFactory, OrderStateContext stateContext) {
        this.processorFactory = processorFactory;
        this.stateContext = stateContext;
    }

    public void validateOrder(Order order) {
        OrderValidationHandler chain = processorFactory.createValidationChain();
        chain.validate(order);
    }

    public void confirm(Order order) {
        validateOrder(order);
        stateContext.getState(order).confirm(order);
    }

    public void cancel(Order order) {
        stateContext.getState(order).cancel(order);
    }
}
