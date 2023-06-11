package com.coupons.couponmanagementservice.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchase")
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt;

}