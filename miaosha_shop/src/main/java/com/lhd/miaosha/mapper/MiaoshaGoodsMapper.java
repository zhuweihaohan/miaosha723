package com.lhd.miaosha.mapper;

import com.lhd.vo.MiaoshaGoods;
import com.lhd.vo.MiaoshaGoodsExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MiaoshaGoodsMapper {
    long countByExample(MiaoshaGoodsExample example);

    int deleteByExample(MiaoshaGoodsExample example);

    int deleteByPrimaryKey(Integer miaoshaGoodsId);

    int insert(MiaoshaGoods record);

    int insertSelective(MiaoshaGoods record);

    List<MiaoshaGoods> selectByExample(MiaoshaGoodsExample example);

    MiaoshaGoods selectByPrimaryKey(Integer miaoshaGoodsId);

    int updateByExampleSelective(@Param("record") MiaoshaGoods record, @Param("example") MiaoshaGoodsExample example);

    int updateByExample(@Param("record") MiaoshaGoods record, @Param("example") MiaoshaGoodsExample example);

    int updateByPrimaryKeySelective(MiaoshaGoods record);

    int updateByPrimaryKey(MiaoshaGoods record);
    //获取所有秒杀商品
    List<MiaoshaGoods> getAll();
    //根据商品ID获得商品信息；
    public MiaoshaGoods getGoodsById(@Param("miaoshaGoodsId") Integer miaoshaGoodsId);
//对某个秒杀商品做减库存的操作；
public int updateGoodsCount(@Param("miaoshaGoodsId") int miaoshaGoodsId);
}