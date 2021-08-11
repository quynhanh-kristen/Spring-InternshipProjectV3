package com.internship.controller;

import com.internship.model.Post;
import com.internship.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {

    @Autowired
    private PostServiceImpl service;

    @RequestMapping(value="/upload")
    public String showUploadPage(Model model){
        Post post = new Post();
        model.addAttribute(post);
        return "uploadPage";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savedUploadFile(@RequestParam("file-input")MultipartFile file,
                                  @RequestParam("title")String title,
                                  @RequestParam("content")String content){
        int fileTypeIndex = file.getContentType().indexOf("/");
        Post post = new Post(title, content, file.getContentType().substring(0,fileTypeIndex));
        String fileId = service.saveImage(file);
        if(fileId != null){
            post.setFileID(fileId);
            service.savePost(post);
        }
        return "confirm";

    }
}
