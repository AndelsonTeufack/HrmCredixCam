package com.credix.credixhrm.enumm;

public enum Role {
    SUPERADMIN,
    ADMIN,
    USER;

    public static Role fromValueIgnoreCase(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}
