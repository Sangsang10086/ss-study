package com.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@ToString
@TableName("user")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @TableField(exist = false)
    private List<String> roleIdList;

    private List<String> roles;

    /**
     * 账户是否未过期(1未过期 0已过期)
     */
    @TableField(exist = false)
    private boolean isAccountNonExpired = true;

    /**
     * 账户是否未锁定(1未锁定 0已锁定)
     */
    @TableField(exist = false)
    private boolean isAccountNonLocked = true;

    /**
     * 密码是否未过期(1未过期 0已过期)
     */
    @TableField(exist = false)
    private boolean isCredentialsNonExpired = true;

    /**
     * 账户是否可用(1可用 0不可用)
     */
    @TableField(exist = false)
    private boolean isEnabled = true;

    // 实现UserDetails接口方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
