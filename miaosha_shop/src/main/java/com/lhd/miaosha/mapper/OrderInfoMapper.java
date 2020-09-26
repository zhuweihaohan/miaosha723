package com.lhd.miaosha.mapper;

import com.lhd.vo.MiaoshaOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    void insert(MiaoshaOrder order);
}
