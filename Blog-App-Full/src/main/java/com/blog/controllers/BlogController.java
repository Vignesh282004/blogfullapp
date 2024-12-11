package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.dtos.CommentDto;
import com.blog.dtos.PostDto;
import com.blog.services.impl.PostServiceImpl;

@Controller
public class BlogController {
	@Autowired
	private PostServiceImpl postServiceImpl;
	
	  // handler method to handle http://localhost:8080/
    @GetMapping("/")
    public String viewBlogPosts(Model model,@RequestParam(defaultValue = "0") int page){
    	
    	int pagesize = 4;
        Page<PostDto> postsResponse = postServiceImpl.findallPosts(page, pagesize);
        model.addAttribute("postsResponse", postsResponse);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",postsResponse.getTotalPages());
        return "blog/view_posts";
    }
    
    // handler method to handle view post request
    @GetMapping("/post/{postUrl}")
    private String showPost(@PathVariable("postUrl") String postUrl,
                            Model model){
        PostDto post = postServiceImpl.findPostsbyUrl(postUrl);
        model.addAttribute("post", post);
        CommentDto commentDto = new CommentDto();
        model.addAttribute("comment",commentDto);
        return "blog/blog_post";
    }
    
    // handler method to handle blog post search request
    // http://localhost:8080/page/search?query=java
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model,
                              @RequestParam(defaultValue = "0") int page){
    	int pagesize = 10;
        Page<PostDto> postsResponse = postServiceImpl.searchPost(query,page,pagesize);
        model.addAttribute("postsResponse", postsResponse);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",postsResponse.getTotalPages());
        model.addAttribute("query",query);
        return "blog/view_posts";
    }
}
