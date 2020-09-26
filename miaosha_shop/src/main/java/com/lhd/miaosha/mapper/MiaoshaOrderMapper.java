package com.lhd.miaosha.mapper;

import com.lhd.vo.MiaoshaOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MiaoshaOrderMapper {

    void insert(MiaoshaOrder order);
}
