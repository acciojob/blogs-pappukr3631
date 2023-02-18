package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        //Create an image object
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        //Get blog
        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);
//        imageRepository2.save(image);not required
        //Save image in blog list
        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        //Save blog in blog repo.
        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){
        if(imageRepository2.findById(id).isPresent()) {
            Image image = imageRepository2.findById(id).get();

            Blog blog = image.getBlog();
            List<Image> imageList = blog.getImageList();
            imageList.remove(image);
        }
//        blogRepository2.save(blog);
        //2nd place:- maybe have to save list again
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String imgDimension = imageRepository2.findById(id).get().getDimensions();
        String iDimension[] = imgDimension.split("X");
        String sDimension[] = screenDimensions.split("X");
        //It's not correct. But still trying
        int idim = Integer.parseInt(sDimension[0]) / Integer.parseInt(iDimension[0]);
        int sdim =  Integer.parseInt(sDimension[1]) / Integer.parseInt(iDimension[1]);
        return sdim*idim;
    }
}
