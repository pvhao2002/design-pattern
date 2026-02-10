# Kế hoạch 5 ngày — Hoàn thành 23 Design Patterns

**Đối tượng:** Sinh viên mới bắt đầu học design pattern  
**Ngày bắt đầu:** 02/02/2026  
**Mục tiêu:** Hiểu và áp dụng đủ 23 pattern vào project Backend (Quản lý cửa hàng quần áo)

---

## Tổng quan

| Ngày | Ngày làm việc | Trọng tâm | Số pattern |
|------|----------------|-----------|------------|
| 1 | 02/02/2026 (CN) | Creational + nền tảng project | 5 |
| 2 | 03/02/2026 (T2) | Behavioral (phần 1) | 4 |
| 3 | 04/02/2026 (T3) | Behavioral (phần 2) | 4 |
| 4 | 05/02/2026 (T4) | Structural | 7 |
| 5 | 06/02/2026 (T5) | Tích hợp, review, test | 3 (ôn + kiểm tra) |

---

## Ngày 1 — 02/02/2026: Creational Patterns & Nền tảng

**Mục tiêu:** Chạy được project, tạo entity/repository cơ bản, áp dụng 5 pattern Creational.

### Buổi sáng (3–4 giờ)

1. **Chuẩn bị môi trường (30 phút)**
   - Cài JDK 21, Maven, MySQL (hoặc dùng H2 cho đơn giản).
   - Clone/mở project, chạy `mvnw spring-boot:run`, gọi `GET /api/health`.

2. **Lý thuyết Creational (45 phút)**
   - Đọc/tóm tắt: Factory, Abstract Factory, Builder, Prototype, Singleton.
   - Ghi chú: dùng khi nào, ưu/nhược từng pattern.

3. **Factory (1 giờ)**
   - Đọc code: `CategoryFactory`, `ProductFactory`, `OrderFactory`, `OrderItemFactory`.
   - Thử: tạo 1 category, 1 product qua factory trong test hoặc main.

### Buổi chiều (2–3 giờ)

4. **Abstract Factory (45 phút)**
   - Đọc: `OrderProcessorFactory` (tạo validation chain).
   - Hiểu: “họ sản phẩm” = nhiều handler trong một chain.

5. **Builder (30 phút)**
   - Xem entity/DTO có `@Builder` (Lombok): `Order`, `Product`, `Category`.
   - Tự viết 1 class đơn giản dùng `@Builder`.

6. **Prototype (30 phút)**
   - Đọc: `Product.copy()`.
   - Thử: clone 1 product, đổi name/price rồi lưu.

7. **Singleton (30 phút)**
   - Đọc: `StoreConfig`, Spring `@Service`/`@Component` (mặc định singleton).
   - Hiểu: tại sao Spring bean mặc định là singleton.

**Checklist ngày 1:** [ ] Project chạy được  [ ] Hiểu 5 Creational  [ ] Đọc hết code Factory/Abstract Factory/Builder/Prototype/Singleton trong project

---

## Ngày 2 — 03/02/2026: Behavioral (1) — State, Strategy, Observer, Chain

**Mục tiêu:** Nắm State, Strategy, Observer, Chain of Responsibility; đọc và chạy được luồng đơn hàng.

### Buổi sáng (3 giờ)

1. **Lý thuyết (45 phút)**
   - State: đối tượng đổi hành vi theo trạng thái (DRAFT → CONFIRMED → …).
   - Strategy: thuật toán thay đổi được (theo ngày/tháng, giảm giá).
   - Observer: một thay đổi → nhiều listener (event).
   - Chain of Responsibility: chuỗi xử lý (validation từng bước).

2. **State (1 giờ)**
   - Đọc: `OrderState`, `OrderStateContext`, `DraftState`, `ConfirmedState`, …
   - Chạy API: tạo order → `POST /orders/{id}/confirm` → kiểm tra status.

3. **Strategy (45 phút)**
   - Đọc: `RevenueReportStrategy`, `RevenueByDayStrategy`, `RevenueByMonthStrategy`.
   - Đọc: `DiscountStrategy`, `RuleBasedDiscountStrategy`.
   - Gọi: `GET /api/statistics/revenue?from=...&to=...&groupBy=day`.

### Buổi chiều (2 giờ)

4. **Observer (45 phút)**
   - Đọc: `OrderStatusChangedEvent`, `OrderStatusChangedListener`.
   - Hiểu: khi nào event được publish (trong `OrderService` confirm/pay/…).

5. **Chain of Responsibility (1 giờ)**
   - Đọc: `OrderValidationHandler`, `StockValidationHandler`, `MinAmountValidationHandler`.
   - Đọc: `OrderProcessorFactory` nối chain, `OrderService.confirm()` gọi validate.
   - Thử: tạo order vượt tồn kho hoặc dưới min amount → xem lỗi.

**Checklist ngày 2:** [ ] Hiểu State/Strategy/Observer/Chain  [ ] Chạy được confirm order + revenue API  [ ] Hiểu thứ tự validation (stock → min amount)

---

## Ngày 3 — 04/02/2026: Behavioral (2) — Command, Interpreter, Iterator, Mediator, Memento, Template, Visitor

**Mục tiêu:** Đọc và hiểu 7 pattern Behavioral còn lại; không cần viết thêm code lớn.

### Buổi sáng (3 giờ)

1. **Command (30 phút)**
   - Đọc: `OrderCommand`, `ConfirmOrderCommand`, `CancelOrderCommand`.
   - Ý tưởng: đóng gói thao tác (execute/undo).

2. **Interpreter (30 phút)**
   - Đọc: `DiscountRule`, `PercentIfTotalAboveRule`.
   - Ý tưởng: “câu” rule giảm giá (X% nếu tổng >= Y).

3. **Iterator (20 phút)**
   - Đọc: `OrderItemIterator` (duyệt danh sách OrderItem).

4. **Mediator (30 phút)**
   - Đọc: `OrderWorkflowMediator` (điều phối validate + state).
   - Ý tưởng: không để controller gọi trực tiếp nhiều component.

5. **Memento (30 phút)**
   - Đọc: `OrderMemento`, `OrderMementoCareTaker`.
   - Ý tưởng: lưu snapshot đơn để restore/undo.

### Buổi chiều (2 giờ)

6. **Template Method (30 phút)**
   - Đọc: `AbstractReportGenerator`, `RevenueReportGenerator` (query → aggregate → format).

7. **Visitor (45 phút)**
   - Đọc: `OrderVisitor`, `TotalAmountVisitor`, `OrderVisitable`.
   - Ý tưởng: duyệt cấu trúc (Order + items) không sửa entity.

8. **Ôn Behavioral (45 phút)**
   - Liệt kê 11 pattern, ghi 1 câu “dùng khi nào” cho mỗi cái.
   - Đối chiếu với `backend/DESIGN_PATTERNS.md`.

**Checklist ngày 3:** [ ] Đọc hết code 7 pattern còn lại  [ ] Ghi chú ngắn “khi nào dùng” từng pattern  [ ] Ôn lại 11 Behavioral

---

## Ngày 4 — 05/02/2026: Structural Patterns

**Mục tiêu:** Nắm 7 pattern Structural; đọc code và hiểu vai trò từng lớp.

### Buổi sáng (3 giờ)

1. **Lý thuyết Structural (30 phút)**
   - Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy — mục đích ngắn gọn.

2. **Adapter (30 phút)**
   - Đọc: `PaymentGateway`, `ExternalPaymentService`, `ExternalPaymentAdapter`.
   - Ý tưởng: chuyển interface bên ngoài thành interface nội bộ.

3. **Bridge (30 phút)**
   - Đọc: `NotificationSender`, `EmailNotificationSender`, `SmsNotificationSender`, `OrderNotification`.
   - Ý tưởng: tách abstraction (gửi thông báo) và implementation (email/SMS).

4. **Composite (30 phút)**
   - Đọc: `Category` (parent/children), `Order` + `OrderItem`.
   - Ý tưởng: cấu trúc cây / part-whole.

5. **Decorator (30 phút)**
   - Đọc: `OrderServiceDelegate`, `OrderServiceDecorator` (bọc `OrderService`).

### Buổi chiều (2 giờ)

6. **Facade (25 phút)**
   - Đọc: `CategoryService`, `ProductService`, `OrderService`, `RevenueStatisticsService`.
   - Ý tưởng: API đơn giản che nhiều component bên dưới.

7. **Flyweight (25 phút)**
   - Đọc: `CategoryMetadataCache` (chia sẻ slug, icon).

8. **Proxy (25 phút)**
   - Đọc: `OrderServiceProxy` (controller gọi qua proxy).
   - Nhớ: JPA lazy loading cũng là dạng proxy (Customer.orders).

9. **Ôn Structural (25 phút)**
   - Đối chiếu 7 pattern với bảng trong `DESIGN_PATTERNS.md`.

**Checklist ngày 4:** [ ] Hiểu 7 Structural  [ ] Đọc hết code Adapter/Bridge/Composite/Decorator/Facade/Flyweight/Proxy  [ ] Giải thích được “Facade vs Decorator vs Proxy” bằng 1 câu mỗi cái

---

## Ngày 5 — 06/02/2026: Tích hợp, Review & Kiểm tra

**Mục tiêu:** Ôn toàn bộ 23 pattern, chạy flow đầy đủ, tự kiểm tra.

### Buổi sáng (3 giờ)

1. **Chạy flow đầy đủ (1,5 giờ)**
   - Tạo category → product → customer.
   - Tạo order (DRAFT) → thêm items → confirm → pay → ship → deliver.
   - Gọi API thống kê doanh thu.
   - Kiểm tra: validation (stock, min amount), event (Observer), state chuyển đúng.

2. **Điền checklist 23 pattern (1 giờ)**
   - Mở `backend/DESIGN_PATTERNS.md`.
   - Với từng pattern: ghi lại **tên class** mình đã đọc và **1 ví dụ dùng** trong project.

3. **SOLID (30 phút)**
   - Đọc phần SOLID trong `DESIGN_PATTERNS.md`.
   - Tìm trong code: 1 ví dụ S, 1 O, 1 L, 1 I, 1 D (ghi chú file + dòng hoặc method).

### Buổi chiều (2 giờ)

4. **Tự kiểm tra (1 giờ)**
   - Viết (hoặc nói) ngắn gọn: “Nếu thêm tính năng X thì sẽ dùng pattern Y vì …”.
   - Ví dụ: thêm gửi SMS khi đơn ship → Observer + Bridge (NotificationSender).

5. **Ôn lại & chuẩn bị nộp bài (1 giờ)**
   - Đảm bảo project compile, test pass (`mvnw test`).
   - Chuẩn bị slide/word: liệt kê 23 pattern + file/class tương ứng (có thể copy từ `DESIGN_PATTERNS.md`).

**Checklist ngày 5:** [ ] Chạy full flow order + revenue  [ ] Điền xong checklist 23 pattern  [ ] Nêu được 1 ví dụ SOLID  [ ] Project chạy & test OK

---

## Ghi chú cho sinh viên

- **Mỗi ngày:** Ưu tiên **đọc code** và **chạy API** hơn học thuộc lý thuyết.
- **Gặp khó:** Xem comment trong code (Design pattern: …), đối chiếu `DESIGN_PATTERNS.md` và `.cursor/skills/design-pattern-store/reference.md`.
- **Thời gian:** Có thể linh hoạt (ví dụ ngày 3–4 gộp bớt nếu đã quen); đảm bảo ngày 5 vẫn dành cho review và test.
- **Ngày bắt đầu:** 02/02/2026; ngày kết thúc plan: 06/02/2026.

---

## Tài liệu tham chiếu trong project

| Tài liệu | Đường dẫn |
|----------|-----------|
| Checklist 23 pattern + SOLID | `backend/DESIGN_PATTERNS.md` |
| Skill design pattern store | `.cursor/skills/design-pattern-store/SKILL.md` |
| Feature → pattern | `.cursor/skills/design-pattern-store/reference.md` |
| Requirements đề bài | `requirements.md` |
