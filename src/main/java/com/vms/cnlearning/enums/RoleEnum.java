package com.vms.cnlearning.enums;

/**
 * 角色枚举
 */
public enum RoleEnum {
    STUDENT("学生"),
    TEACHER("教师"),
    ADMIN("管理员");
    
    private final String roleName;
    
    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    /**
     * 根据角色名称获取枚举值
     * @param roleName 角色名称
     * @return 枚举值
     */
    public static RoleEnum getByRoleName(String roleName) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }
} 