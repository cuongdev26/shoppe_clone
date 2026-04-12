package com.shoppe.constant;

import java.util.Set;

public enum Role {
    ADMIN,
    USER,
    CUSTOMER,
    SELLER;


    public static Role getHighest(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) return USER;
        if (roles.contains(ADMIN))  return ADMIN;
        if (roles.contains(SELLER)) return SELLER;
        return USER;
    }
}
