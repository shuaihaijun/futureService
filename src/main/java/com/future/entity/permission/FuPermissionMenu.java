package com.future.entity.permission;

import java.util.Date;

public class FuPermissionMenu {
    /**
     * 
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 节点类型（0 菜单，1 按钮）
     */
    private Byte menuType;

    /**
     * 父级节点ID
     */
    private Integer menuFatherId;

    /**
     * 父级节点名称
     */
    private String menuFatherName;

    /**
     * 节点地址
     */
    private String menuUrl;

    /**
     * 节点状态（0 正常，1 隐藏，2 删除）
     */
    private Byte menuState;

    /**
     * 创建人ID
     */
    private Integer createUser;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改时间
     * @return modify_date 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 修改时间
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 菜单名称
     * @return menu_name 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 菜单名称
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 节点类型（0 菜单，1 按钮）
     * @return menu_type 节点类型（0 菜单，1 按钮）
     */
    public Byte getMenuType() {
        return menuType;
    }

    /**
     * 节点类型（0 菜单，1 按钮）
     * @param menuType 节点类型（0 菜单，1 按钮）
     */
    public void setMenuType(Byte menuType) {
        this.menuType = menuType;
    }

    /**
     * 父级节点ID
     * @return menu_father_id 父级节点ID
     */
    public Integer getMenuFatherId() {
        return menuFatherId;
    }

    /**
     * 父级节点ID
     * @param menuFatherId 父级节点ID
     */
    public void setMenuFatherId(Integer menuFatherId) {
        this.menuFatherId = menuFatherId;
    }

    /**
     * 父级节点名称
     * @return menu_father_name 父级节点名称
     */
    public String getMenuFatherName() {
        return menuFatherName;
    }

    /**
     * 父级节点名称
     * @param menuFatherName 父级节点名称
     */
    public void setMenuFatherName(String menuFatherName) {
        this.menuFatherName = menuFatherName == null ? null : menuFatherName.trim();
    }

    /**
     * 节点地址
     * @return menu_url 节点地址
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * 节点地址
     * @param menuUrl 节点地址
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    /**
     * 节点状态（0 正常，1 隐藏，2 删除）
     * @return menu_state 节点状态（0 正常，1 隐藏，2 删除）
     */
    public Byte getMenuState() {
        return menuState;
    }

    /**
     * 节点状态（0 正常，1 隐藏，2 删除）
     * @param menuState 节点状态（0 正常，1 隐藏，2 删除）
     */
    public void setMenuState(Byte menuState) {
        this.menuState = menuState;
    }

    /**
     * 创建人ID
     * @return create_user 创建人ID
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 创建人ID
     * @param createUser 创建人ID
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}