package io.hardingadonis.saledock.model;

import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Entity(name = "Order")
@Table(name = "`order`")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {

    public static enum Status {
        PENDING,
        SHIPPING,
        DONE,
        CANCELLED
    }

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "`code`", unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "`employee_id`", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "`customer_id`", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "`status`", nullable = false)
    private Status status;

    @Column(name = "`total`", nullable = false)
    private Double total;

    @Column(name = "`note`", columnDefinition = "longtext")
    private String note;

    @Column(name = "`created_at`")
    private LocalDateTime createdAt;

    @Column(name = "`updated_at`")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    public void addProduct(Product product, Integer quantity) {
        var detail = new OrderDetail(this, product);
        detail.setQuantity(quantity);

        this.orderDetails.add(detail);
    }
}