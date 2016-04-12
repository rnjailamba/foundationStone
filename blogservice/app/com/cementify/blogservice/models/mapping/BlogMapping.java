package com.cementify.blogservice.models.mapping;

import com.cementify.blogservice.models.Blog;
import com.cementify.blogservice.models.request.CreateBlogRequest;
import com.cementify.blogservice.models.response.BlogResponse;


import java.text.SimpleDateFormat;


/**
 * Created by roshan on 26/03/16.
 */
public class BlogMapping {

    private  static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static BlogResponse getBlogResponseFromBlog(Blog blog){
        if(blog == null)
            return null;
        BlogResponse blogResponse=new BlogResponse();
        return getBlogResponseFromBlog(blog,blogResponse);
    }

    public static BlogResponse getBlogResponseFromBlog(Blog blog,BlogResponse blogResponse){
         blogResponse.setBlogId(blog.getBlogId().toString());
         blogResponse.setCategoryId(blog.getCategoryId());
         blogResponse.setCreatedDate(simpleDateFormat.format(blog.getCreatedDate()));
         blogResponse.setIsVerified(blog.getIsVerified());
         blogResponse.setLikeUserList(blog.getLikeUserList());
         blogResponse.setNoOfCommentsCollections(blog.getNoOfCommentsCollections());
         blogResponse.setNoOfView(blog.getNoOfView());
         blogResponse.setParagraphs(blog.getParagraphs());
         blogResponse.setCustomerId(blog.getPostedBy());
         blogResponse.setTags(blog.getTags());
         blogResponse.setTitle(blog.getTitle());
         blogResponse.setSubCategoryId(blog.getSubCategoryId());
         blogResponse.setApprovedBy(blog.getApprovedBy());
         blogResponse.setBlogType(blog.getBlogType());
         blogResponse.setCoverImageUrl(blog.getCoverImageUrl());
         return blogResponse;
    }

    public static Blog getBlogFromBlogCreateRequest(CreateBlogRequest createBlogRequest){
        if(createBlogRequest == null)
            return null;
        Blog blog = new Blog();
        return getBlogFromBlogCreateRequest(createBlogRequest,blog);
    }

    public static Blog getBlogFromBlogCreateRequest(CreateBlogRequest createBlogRequest,Blog blog){
        blog.setPostedBy(createBlogRequest.getPostedBy());
        blog.setCategoryId(createBlogRequest.getCategoryId());
        blog.setLikeUserList(createBlogRequest.getLikeUserList());
        blog.setNoOfView(createBlogRequest.getNoOfView());
        blog.setParagraphs(createBlogRequest.getParagraphs());
        blog.setSubCategoryId(createBlogRequest.getSubCategoryId());
        blog.setTitle(createBlogRequest.getTitle());
        blog.setTags(createBlogRequest.getTags());
        blog.setApprovedBy(createBlogRequest.getApprovedBy());
        blog.setBlogType(createBlogRequest.getBlogType());
        blog.setCoverImageUrl(createBlogRequest.getCoverImage());
        return blog;
    }




}
