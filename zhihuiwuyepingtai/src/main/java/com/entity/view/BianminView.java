package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.BianminEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 便民服务
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("bianmin")
public class BianminView extends BianminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 便民服务类型的值
	*/
	@ColumnInfo(comment="便民服务类型的字典表值",type="varchar(200)")
	private String bianminValue;




	public BianminView() {

	}

	public BianminView(BianminEntity bianminEntity) {
		try {
			BeanUtils.copyProperties(this, bianminEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 便民服务类型的值
	*/
	public String getBianminValue() {
		return bianminValue;
	}
	/**
	* 设置： 便民服务类型的值
	*/
	public void setBianminValue(String bianminValue) {
		this.bianminValue = bianminValue;
	}




	@Override
	public String toString() {
		return "BianminView{" +
			", bianminValue=" + bianminValue +
			"} " + super.toString();
	}
}
