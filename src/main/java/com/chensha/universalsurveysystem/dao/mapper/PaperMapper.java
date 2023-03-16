package com.chensha.universalsurveysystem.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chensha.universalsurveysystem.dao.pojo.Paper;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperMapper extends BaseMapper<Paper> {
    // 可以在这里自定义Paper表的特殊查询方法
}