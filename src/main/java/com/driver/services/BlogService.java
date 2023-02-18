package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;
    @Autowired
    private ImageRepository imageRepository;

    public Blog createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);

        User user = userRepository1.findById(userId).get();
        blog.setUser(user);
        //Blog is created
        blogRepository1.save(blog);
        //Blog is saved in repo

        //Update list of blogs by user
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        userRepository1.save(user);
        return blog;
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        try {
            //1. Delete corresponding images of the blog
            List<Image> imageList = blogRepository1.findById(blogId).get().getImageList();
            for (Image i : imageList) {
                int imgId = i.getId();
                imageRepository.deleteById(imgId);
            }
            //2. Delete blog from user blog-list
            List<Blog> blogList = blogRepository1.findById(blogId).get().getUser().getBlogList();
            blogList.remove(blogRepository1.findById(blogId));
            //save this updated list to author repo.

            //Delete blog
            blogRepository1.deleteById(blogId);
        }catch (Exception e) {
            return;
        }
    }
}
