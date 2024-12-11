package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.dtos.CommentDto;
import com.blog.dtos.PostDto;
import com.blog.services.impl.CommentServiceImpl;
import com.blog.services.impl.PostServiceImpl;
import com.blog.utils.ROLE;
import com.blog.utils.SecurityUtils;

import jakarta.validation.Valid;

@Controller
public class PostController {
	@Autowired
	private PostServiceImpl postServiceImpl;
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	@GetMapping("/admin/posts")
	public String posts(Model model) 
	{
		String role = SecurityUtils.getRole();
		List<PostDto> posts = null;
		
		if(ROLE.ROLE_ADMIN.name().equals(role)) {
			posts = postServiceImpl.getadminPosts();
		}
		else 
		{
			posts = postServiceImpl.findPostsByUser();
		}
		model.addAttribute("posts",posts);
		return "/admin/alladminposts";
	}
	
	@GetMapping("admin/posts/newpost")
	public String newPost(Model model)
	{
		PostDto postDto = new PostDto();
		model.addAttribute("post",postDto);
		return "admin/create_post";
	}
	@PostMapping("/admin/posts")
	public String createPost(@Valid @ModelAttribute("post") PostDto postDto , BindingResult result, Model model) 
	{
		if(result.hasErrors()) {
			model.addAttribute("post",postDto);
			return "admin/create_post";
		}
		
		postDto.setUrl(geturl(postDto.getTitle()));
		postServiceImpl.createPost(postDto);
		return "redirect:/admin/posts";
	}
	
	public static String geturl(String title) 
	{
		String mytitle  = title.trim().toLowerCase();
		String myurl = mytitle.replaceAll("\\s+","-");
		myurl = myurl.replaceAll("[^A-Za-z0-9]","-");
		return myurl;
	}
    @GetMapping("admin/posts/{postId}/edit")
    public String editPost(@PathVariable("postId")Long postId,
                           Model model)
    {
        PostDto postDto = postServiceImpl.findpostsbyId(postId);
        model.addAttribute("post",postDto);
        return "admin/edit_post";
    }
    
    @PostMapping("admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute("post") PostDto post,
                             BindingResult result, Model model) {
        if(result.hasErrors())
        {
            model.addAttribute("post",post);
            return "admin/edit_post";
        }
        post.setId(postId);
        postServiceImpl.updatePost(post);
        return "redirect:/admin/posts";
    }
    
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId)
    {
        postServiceImpl.deletePost(Long.valueOf(postId));
        return "redirect:/admin/posts";
    }
    
    @GetMapping("admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model)
    {
        PostDto postDto = postServiceImpl.findPostsbyUrl(postUrl);
        model.addAttribute("post",postDto);
        return "admin/view_post";
    }
    @GetMapping("/admin/posts/search")
    public String searchPost(@RequestParam(value="query")String query,
                             Model model, @RequestParam(defaultValue = "0") int page)
    {
    	int pagesize = 4;
        Page<PostDto> posts = postServiceImpl.searchPost(query,page,pagesize);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage" , page);
        model.addAttribute("totalPages",posts.getTotalPages());
        model.addAttribute("query",query);
        return "admin/posts";
    }
    
    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId")Long commentId){
        commentServiceImpl.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }
    @GetMapping("/admin/posts/comments")
    public String postCOmment(Model model) 
    {
    	String role = SecurityUtils.getRole();
    	List<CommentDto> comments = null;
    	
    	if(ROLE.ROLE_ADMIN.name().equals(role)) 
    	{
    		comments = commentServiceImpl.findAllComments();
    	}
    	else 
    	{
    		comments = commentServiceImpl.findCommentsByPost();
    	}
    	
    	model.addAttribute("comments",comments);
    	return "admin/comments";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
