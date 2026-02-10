---
name: design-pattern-store
description: Guides implementation of a clothing store management backend in Java Spring Boot by applying all Gang of Four design patterns (Creational, Behavioral, Structural) and SOLID principles. Use when implementing or reviewing BE APIs, services, DTOs, or when deciding which design pattern to apply for product/customer/order management and revenue statistics.
---

# Design Pattern Store (Backend)

Dự án: Quản lý cửa hàng bán quần áo — BE Java Spring Boot, FE Angular 19, DB MySQL. Bắt buộc áp dụng **tất cả** mẫu thiết kế dưới đây và thiết kế theo **SOLID**.

## Tech stack

- **BE:** Java Spring Boot
- **FE:** Angular 19
- **DB:** MySQL

## Chức năng cần có

- Quản lý loại sản phẩm (category)
- Quản lý sản phẩm (product)
- Quản lý khách hàng (customer)
- Quản lý đơn hàng (order)
- Thống kê doanh thu (revenue statistics)

## Nguyên tắc SOLID

Khi viết hoặc review code, đảm bảo:

- **S**ingle Responsibility: Mỗi class một trách nhiệm rõ ràng.
- **O**pen/Closed: Mở cho mở rộng (interface, abstract), đóng cho sửa code sẵn có.
- **L**iskov Substitution: Subtype thay thế được base type mà không phá hành vi.
- **I**nterface Segregation: Interface nhỏ, cụ thể; tránh interface “béo”.
- **D**ependency Inversion: Phụ thuộc vào abstraction (interface), không phụ thuộc concrete class.

## Checklist mẫu thiết kế (phải áp dụng đủ)

Copy checklist khi implement hoặc review; đánh dấu khi đã áp dụng.

### Creational

- [ ] **Factory** — tạo object (VD: product, order item) qua factory thay vì `new` trực tiếp.
- [ ] **Abstract Factory** — họ sản phẩm (VD: repository/validator cho từng aggregate).
- [ ] **Builder** — xây entity/DTO phức tạp (Order, Report, Filter).
- [ ] **Prototype** — clone object (VD: copy đơn hàng, copy cấu hình).
- [ ] **Singleton** — một instance duy nhất (VD: config, cache manager) — trong Spring thường dùng scope bean.

### Behavioral

- [ ] **Chain of Responsibility** — xử lý request qua chuỗi handler (VD: validation pipeline, discount rules).
- [ ] **Command** — đóng gói thao tác (VD: đặt hàng, hủy đơn, rollback).
- [ ] **Interpreter** — diễn giải ngôn ngữ/rule nhỏ (VD: rule giảm giá, điều kiện filter).
- [ ] **Iterator** — duyệt collection ẩn cấu trúc (VD: trang kết quả, stream items).
- [ ] **Mediator** — điều phối giữa nhiều component (VD: order workflow, notification).
- [ ] **Memento** — lưu/khôi phục trạng thái (VD: draft order, undo).
- [ ] **Observer** — cập nhật khi có thay đổi (VD: event khi đơn hàng thay đổi, thống kê realtime).
- [ ] **State** — hành vi theo trạng thái (VD: trạng thái đơn hàng: Draft → Confirmed → Shipped).
- [ ] **Strategy** — thuật toán thay đổi được (VD: tính giảm giá, tính phí ship).
- [ ] **Template Method** — skeleton thuật toán, bước con override (VD: bước tạo báo cáo, export).
- [ ] **Visitor** — thao tác trên cấu trúc không sửa class gốc (VD: tính tổng đơn, audit log).

### Structural

- [ ] **Adapter** — chuyển interface không tương thích (VD: tích hợp payment, legacy API).
- [ ] **Bridge** — tách abstraction và implementation (VD: nhiều loại notification / nhiều kênh gửi).
- [ ] **Composite** — cấu trúc cây (VD: category cây, order = order lines).
- [ ] **Decorator** — bổ sung hành vi không sửa class gốc (VD: logging, cache, validation wrapper).
- [ ] **Facade** — API đơn giản cho subsystem phức tạp (VD: OrderService che repository + validation + event).
- [ ] **Flyweight** — chia sẻ state chung để giảm bộ nhớ (VD: category ref, shared product type).
- [ ] **Proxy** — đại diện với hành vi thêm (VD: lazy load, access control, cache).

## Quy trình khi thêm tính năng

1. Xác định chức năng thuộc nhóm nào (category/product/customer/order/statistics).
2. Tra [reference.md](reference.md) để chọn pattern phù hợp cho tính năng đó.
3. Viết code tuân SOLID; ưu tiên interface và dependency injection.
4. Cập nhật checklist trên cho đủ 23 pattern khi hoàn thành project.

## Gợi ý áp dụng nhanh

| Khu vực        | Pattern gợi ý |
|----------------|----------------|
| Tạo Order/Item | Factory, Builder |
| Trạng thái đơn hàng | State, Memento |
| Validation / discount | Chain of Responsibility, Strategy, Interpreter |
| Event khi đơn thay đổi | Observer |
| API đơn giản cho FE   | Facade (service layer) |
| Repository/DB khác nhau | Abstract Factory, Adapter |
| Báo cáo / export      | Template Method, Visitor |

Chi tiết map feature → pattern và ví dụ code: [reference.md](reference.md).
