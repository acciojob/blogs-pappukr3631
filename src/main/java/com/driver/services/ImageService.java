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
        image.setDimension(dimensions);

        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);
        imageRepository2.save(image);
        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        List<Image> imageList = image.getBlog().getImageList();
        imageList.remove(image);
        //2nd place:- maybe have to save list again
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String imgDimension = imageRepository2.findById(id).get().getDimension();
        String iDimension[] = imgDimension.split("X");
        String sDimension[] = screenDimensions.split("X");
        //It's not correct. But still trying
        int idim = Integer.parseInt(iDimension[0]) * Integer.parseInt(iDimension[1]);
        int sdim = Integer.parseInt(sDimension[0]) * Integer.parseInt(sDimension[1]);
        return sdim/idim;
    }
}
