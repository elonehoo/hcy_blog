package com.inet.codebase.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.mapper.BlogMapper;
import com.inet.codebase.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inet.codebase.utils.ArchivesUtils;
import com.inet.codebase.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HCY
 * @since 2020-09-26
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    /**
     * 获取博客和进行得评论数目
     * @author HCY
     * @since 2020-10-3
     * @param category 种类得分类
     * @param currentPage 需要获取得页数
     * @param total 条目数
     * @return 自定义得page对象
     */
    @Override
    public PageUtils numberOfBlogsAndComments(String category,
                                              Integer currentPage,
                                              Integer total) {
        //设置PageUtils对象
        PageUtils page = new PageUtils();
        //设置当前是第几页
        page.setCurrent(currentPage);
        //设置当前页数得条目数
        page.setPageNavSize(total);
        //计算当前页开始得位数
        currentPage  = ( currentPage * 10 ) - 10;
        //查找到属于得集合
        List<Blog> blogList = blogMapper.numberOfBlogsAndComments(category, currentPage, total);
        //填充进入page集合数据
        page.setResultList(blogList);
        //查找总共有多少条博客
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        //设置条件
        if ( ! category.equals("")){
            queryWrapper.eq("blog_category",category);
        }
        //进行查找博客数目
        int count = this.count(queryWrapper);
        //设置page集合中得总共数目
        page.setTotalCount(count);
        return page;
    }

    /**
     * 归档请求,查看写博客得日期和博客数目
     * @author HCY
     * @since 2020-10-04
     * @return
     */
    @Override
    public Map<String , Object> CheckTheArchive() {
        //进行获取 XX年XX月
        List<ArchivesUtils> archivesUtilsList = blogMapper.CheckTheArchive();
        //创建存储得map对象
        Map<String , Object> map = new HashMap<>();
        //进行循环
        for(ArchivesUtils archivesUtils : archivesUtilsList){
            //确定查询条件得容器
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            //设置查询条件
            queryWrapper.like("blog_establish",archivesUtils.getDate());
            queryWrapper.orderByDesc("blog_establish");
            //进行查询
            List<Blog> blogList = this.list(queryWrapper);
            //存入
            map.put(archivesUtils.getDate() , blogList);
        }
        return map;
    }

    /**
     * 查看最新得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    @Override
    public List<Blog> lookAtTheLatest() {
        return blogMapper.lookAtTheLatest();
    }

    /**
     * 查看最热得博客
     * @author HCY
     * @since 2020-10-04
     * @return list集合
     */
    @Override
    public List<Blog> lookAtTheHottest() {
        return blogMapper.lookAtTheHottest();
    }
}
