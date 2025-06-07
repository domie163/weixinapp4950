package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 便民服务
 *
 * @author 
 * @email
 */
@TableName("bianmin")
public class BianminEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public BianminEntity() {

	}

	public BianminEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 便民服务名称
     */
    @ColumnInfo(comment="便民服务名称",type="varchar(200)")
    @TableField(value = "bianmin_name")

    private String bianminName;


    /**
     * 便民服务编号
     */
    @ColumnInfo(comment="便民服务编号",type="varchar(200)")
    @TableField(value = "bianmin_uuid_number")

    private String bianminUuidNumber;


    /**
     * 便民服务照片
     */
    @ColumnInfo(comment="便民服务照片",type="varchar(200)")
    @TableField(value = "bianmin_photo")

    private String bianminPhoto;


    /**
     * 便民服务地点
     */
    @ColumnInfo(comment="便民服务地点",type="varchar(200)")
    @TableField(value = "bianmin_address")

    private String bianminAddress;


    /**
     * 便民服务类型
     */
    @ColumnInfo(comment="便民服务类型",type="int(11)")
    @TableField(value = "bianmin_types")

    private Integer bianminTypes;


    /**
     * 便民服务介绍
     */
    @ColumnInfo(comment="便民服务介绍",type="longtext")
    @TableField(value = "bianmin_content")

    private String bianminContent;


    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)")
    @TableField(value = "bianmin_delete")

    private Integer bianminDelete;


    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="录入时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：便民服务名称
	 */
    public String getBianminName() {
        return bianminName;
    }
    /**
	 * 设置：便民服务名称
	 */

    public void setBianminName(String bianminName) {
        this.bianminName = bianminName;
    }
    /**
	 * 获取：便民服务编号
	 */
    public String getBianminUuidNumber() {
        return bianminUuidNumber;
    }
    /**
	 * 设置：便民服务编号
	 */

    public void setBianminUuidNumber(String bianminUuidNumber) {
        this.bianminUuidNumber = bianminUuidNumber;
    }
    /**
	 * 获取：便民服务照片
	 */
    public String getBianminPhoto() {
        return bianminPhoto;
    }
    /**
	 * 设置：便民服务照片
	 */

    public void setBianminPhoto(String bianminPhoto) {
        this.bianminPhoto = bianminPhoto;
    }
    /**
	 * 获取：便民服务地点
	 */
    public String getBianminAddress() {
        return bianminAddress;
    }
    /**
	 * 设置：便民服务地点
	 */

    public void setBianminAddress(String bianminAddress) {
        this.bianminAddress = bianminAddress;
    }
    /**
	 * 获取：便民服务类型
	 */
    public Integer getBianminTypes() {
        return bianminTypes;
    }
    /**
	 * 设置：便民服务类型
	 */

    public void setBianminTypes(Integer bianminTypes) {
        this.bianminTypes = bianminTypes;
    }
    /**
	 * 获取：便民服务介绍
	 */
    public String getBianminContent() {
        return bianminContent;
    }
    /**
	 * 设置：便民服务介绍
	 */

    public void setBianminContent(String bianminContent) {
        this.bianminContent = bianminContent;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getBianminDelete() {
        return bianminDelete;
    }
    /**
	 * 设置：逻辑删除
	 */

    public void setBianminDelete(Integer bianminDelete) {
        this.bianminDelete = bianminDelete;
    }
    /**
	 * 获取：录入时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：录入时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Bianmin{" +
            ", id=" + id +
            ", bianminName=" + bianminName +
            ", bianminUuidNumber=" + bianminUuidNumber +
            ", bianminPhoto=" + bianminPhoto +
            ", bianminAddress=" + bianminAddress +
            ", bianminTypes=" + bianminTypes +
            ", bianminContent=" + bianminContent +
            ", bianminDelete=" + bianminDelete +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
