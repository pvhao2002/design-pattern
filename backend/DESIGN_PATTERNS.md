# Checklist Design Patterns — Backend

Áp dụng đủ **23** mẫu thiết kế theo requirements và `.cursor/skills/design-pattern-store`.  
Đã kiểm tra: compile, null-safety, tích hợp chain/validation/observer.

---

## Creational (5)

| Pattern | Class / Vị trí |
|--------|----------------|
| **Factory** | `factory/CategoryFactory`, `ProductFactory`, `OrderFactory`, `OrderItemFactory` |
| **Abstract Factory** | `factory/OrderProcessorFactory` — tạo validation chain cho order |
| **Builder** | Entity/DTO dùng Lombok `@Builder`: `Category`, `Product`, `Customer`, `Order`, `OrderItem`, các DTO |
| **Prototype** | `entity/Product.copy()` — clone product |
| **Singleton** | `config/StoreConfig` + Spring `@Service`/`@Component` (scope mặc định singleton) |

---

## Behavioral (11)

| Pattern | Class / Vị trí |
|--------|----------------|
| **Chain of Responsibility** | `chain/OrderValidationHandler`, `StockValidationHandler`, `MinAmountValidationHandler` — pipeline validation đơn (stock → min amount), gọi trước confirm |
| **Command** | `command/OrderCommand`, `ConfirmOrderCommand`, `CancelOrderCommand` |
| **Interpreter** | `interpreter/DiscountRule`, `PercentIfTotalAboveRule` — rule giảm giá "X% nếu tổng >= Y" |
| **Iterator** | `iterator/OrderItemIterator` — duyệt `OrderItem` |
| **Mediator** | `mediator/OrderWorkflowMediator` — điều phối validation + state |
| **Memento** | `memento/OrderMemento`, `OrderMementoCareTaker` — snapshot đơn để undo/restore |
| **Observer** | `observer/OrderStatusChangedEvent`, `OrderStatusChangedListener` (Spring `@EventListener`) |
| **State** | `state/OrderState`, `AbstractOrderState`, `DraftState`, `ConfirmedState`, `PaidState`, `ShippedState`, `DeliveredState`, `OrderStateContext` |
| **Strategy** | `statistics/RevenueReportStrategy`, `RevenueByDayStrategy`, `RevenueByMonthStrategy`; `strategy/DiscountStrategy`, `RuleBasedDiscountStrategy` |
| **Template Method** | `template/AbstractReportGenerator`, `RevenueReportGenerator` — query → aggregate → format |
| **Visitor** | `visitor/OrderVisitor`, `TotalAmountVisitor`, `OrderVisitable` — duyệt Order/OrderItem tính tổng |

---

## Structural (7)

| Pattern | Class / Vị trí |
|--------|----------------|
| **Adapter** | `adapter/PaymentGateway`, `ExternalPaymentService`, `ExternalPaymentAdapter` — adapt API ngoài |
| **Bridge** | `bridge/NotificationSender`, `EmailNotificationSender`, `SmsNotificationSender`, `OrderNotification` |
| **Composite** | `entity/Category` (parent/children); `Order` + `OrderItem` (order = composite items) |
| **Decorator** | `decorator/OrderServiceDelegate`, `OrderServiceDecorator` — bọc `OrderService` |
| **Facade** | `service/CategoryService`, `ProductService`, `CustomerService`, `OrderService`, `RevenueStatisticsService` |
| **Flyweight** | `flyweight/CategoryMetadataCache` — chia sẻ metadata category (slug, icon) |
| **Proxy** | `proxy/OrderServiceProxy` — đại diện OrderService (controller gọi qua proxy); JPA lazy (Customer.orders) |

---

## SOLID

- **S**: Mỗi class một trách nhiệm (handler, state, strategy, v.v.).
- **O**: Mở rộng qua interface/abstract (OrderState, DiscountRule, RevenueReportStrategy, NotificationSender).
- **L**: Các state thay thế được qua `OrderState`; các strategy thay thế được.
- **I**: Interface nhỏ (OrderCommand, DiscountRule, OrderVisitor, PaymentGateway).
- **D**: Controller/Service phụ thuộc abstraction (OrderServiceDelegate, PaymentGateway, NotificationSender).
