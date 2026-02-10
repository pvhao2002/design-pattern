# Reference: Feature → Design Pattern

Map từng chức năng và khu vực code sang pattern cụ thể. Dùng khi cần chọn pattern cho một tính năng.

---

## Quản lý loại sản phẩm (Category)

| Pattern | Cách áp dụng |
|--------|---------------|
| **Composite** | Category cha-con (cây danh mục). |
| **Factory** | Tạo `Category` entity qua `CategoryFactory`. |
| **Flyweight** | Chia sẻ metadata category (icon, slug) dùng lại. |

---

## Quản lý sản phẩm (Product)

| Pattern | Cách áp dụng |
|--------|---------------|
| **Factory / Abstract Factory** | `ProductFactory` tạo product theo type; Abstract Factory cho họ repository/validator. |
| **Builder** | `ProductDTO.Builder` hoặc `Product.builder()` cho entity nhiều field. |
| **Prototype** | Clone product (copy để tạo biến thể). |
| **Strategy** | Chiến lược tính giá (giá gốc, khuyến mãi, bulk). |

---

## Quản lý khách hàng (Customer)

| Pattern | Cách áp dụng |
|--------|---------------|
| **Builder** | `Customer` hoặc `CustomerDTO` với Builder. |
| **Memento** | Lưu/khôi phục profile nháp (draft). |
| **Proxy** | Lazy load danh sách đơn hàng của customer. |

---

## Quản lý đơn hàng (Order)

| Pattern | Cách áp dụng |
|--------|---------------|
| **Factory** | `OrderFactory`, `OrderItemFactory` tạo đơn và dòng đơn. |
| **Builder** | `Order.builder()` với nhiều line items và thông tin. |
| **Composite** | Order = composite của nhiều OrderItem. |
| **State** | OrderState: DRAFT → CONFIRMED → PAID → SHIPPED → DELIVERED. |
| **Memento** | Lưu snapshot đơn để undo / restore. |
| **Command** | `PlaceOrderCommand`, `CancelOrderCommand` (có thể kết hợp undo). |
| **Chain of Responsibility** | Chuỗi validation: stock → payment → shipping. |
| **Observer** | Publish event khi order thay đổi (inventory, thống kê, email). |
| **Template Method** | Thuật toán xử lý đơn: validate → reserve stock → create payment → persist (các bước con override nếu cần). |
| **Facade** | `OrderService` ẩn repository, validator, event publisher. |

---

## Thống kê doanh thu (Revenue / Reports)

| Pattern | Cách áp dụng |
|--------|---------------|
| **Strategy** | Cách tính: theo ngày/tuần/tháng, theo category, theo region. |
| **Template Method** | Skeleton report: query data → aggregate → format (PDF/Excel override bước format). |
| **Visitor** | Duyệt collection orders/items để tính tổng, không thêm method vào entity. |
| **Iterator** | Duyệt kết quả thống kê phân trang hoặc stream. |

---

## Hạ tầng & chung

| Pattern | Cách áp dụng |
|--------|---------------|
| **Singleton** | Config, cache manager (hoặc Spring `@Service` single scope). |
| **Adapter** | Adapter cho payment gateway, legacy API, external service. |
| **Bridge** | Notification: abstraction (OrderNotification) vs implementation (Email/SMS/Push). |
| **Decorator** | Wrap service bằng logging, cache, retry. |
| **Facade** | Service layer che nhiều component (repository + event + validation). |
| **Flyweight** | Shared data: category cache, product type metadata. |
| **Proxy** | Lazy load, cache, hoặc security check trước khi gọi service. |
| **Mediator** | Order workflow mediator điều phối Inventory, Payment, Shipping. |
| **Interpreter** | Rule nhỏ: "giảm 10% nếu tổng > 1M", "free ship nếu >= 3 items". |

---

## Ví dụ code ngắn (Java / Spring)

**State — Order:**
```java
public interface OrderState {
    void confirm(Order order);
    void ship(Order order);
}
// Concrete: DraftState, ConfirmedState, ShippedState...
```

**Strategy — Discount:**
```java
public interface DiscountStrategy {
    BigDecimal apply(Order order);
}
// PercentDiscount, FixedDiscount, BuyXGetY...
```

**Chain of Responsibility — Validation:**
```java
public abstract class OrderValidationHandler {
    private OrderValidationHandler next;
    public void setNext(OrderValidationHandler next) { this.next = next; }
    protected abstract boolean doValidate(Order order);
    public boolean validate(Order order) {
        return doValidate(order) && (next == null || next.validate(order));
    }
}
```

**Observer — Spring Events:**
```java
@EventListener
public void onOrderConfirmed(OrderConfirmedEvent e) {
    // update inventory, send email, update stats
}
```

Khi implement, ưu tiên đúng SOLID và map rõ class nào thuộc pattern nào để dễ review.
