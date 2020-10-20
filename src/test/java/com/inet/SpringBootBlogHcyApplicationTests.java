package com.inet;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.inet.codebase.entity.Blog;
import com.inet.codebase.entity.Category;
import com.inet.codebase.entity.User;
import com.inet.codebase.mapper.BlogMapper;
import com.inet.codebase.mapper.CategoryMapper;
import com.inet.codebase.service.BlogService;
import com.inet.codebase.service.CategoryService;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utils.ArchivesUtils;
import com.inet.codebase.utils.PageUtils;
import com.inet.codebase.utils.RegesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootBlogHcyApplicationTests {

    @Resource
    private UserService userService;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private BlogService blogService;

    final String text = "你好呀";
    @Test
    void contextLoads() {
        /**
         * false : 不是敏感词
         * true : 是敏感词
         */
        boolean contains = SensitiveWordBs.newInstance().contains("晓寻遥");
        System.out.println(contains);
//        System.out.println("123");
    }

    @Test
    void contextLoads1(){
        List<Category> categories = categoryMapper.listAddCount();
        System.out.println(categories);
    }


    @Test
    void contextLoads2(){
//        String url = "http://47.104.249.85:8080/PCQadmin";
        String url = "";
        System.out.println(RegesUtils.isUrl(url));
    }
    @Test
    void contextLoads3(){
        List<String> list = new ArrayList<>();
        list.add("2FE1F4ABEF5347DCB098C4D00BDA16B3");
        list.add("ACA2CCF1B1D340FB8593185FCDA378A6");
//        System.out.println(list);
        categoryService.removeByIds(list);
    }

    @Test
    void contextLoads4(){
        List<Category> categoryList = categoryMapper.listAddCount();
        for (Category category : categoryList){
            System.out.println(category);

        }
    }

    @Test
    void contextLoads5(){
        PageUtils page = blogService.numberOfBlogsAndComments("", 1, 10);
        System.out.println(page.getResultList());
    }

    @Test
    void contextLoads6(){
        Map<String, Object> map = blogService.CheckTheArchive();
        System.out.println(map.get("2019-10"));
    }
    @Test
    void contextLoads7(){
        System.out.println(blogMapper.lookAtTheLatest());
    }

    @Test
    void contextLoads8(){
        List<Blog> blogs = blogMapper.lookAtTheHottest();
        for (Blog blog : blogs){
            System.out.println(blog);
        }
    }
}
