package com.anuj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.anuj.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String password;
    private String password = "defaultPassword";  // Default password

//    @Column(nullable = false)
@Column(unique = true)
private String email;

//    @Column(nullable = false)
    private String fullName;

    private String mobile;

//    private USER_ROLE role;
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER; // Default role

    @OneToMany
    private Set<Address> addresses=new HashSet<>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_coupons",
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> usedCoupons=new HashSet<>();
}
