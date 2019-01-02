package com.knowwhere.classroom.post.services;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.services.ClassesService;
import com.knowwhere.classroom.filepath.models.PostFilePath;
import com.knowwhere.classroom.organization.models.Organization;
import com.knowwhere.classroom.organization.services.OrganizationService;
import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.post.repos.PostRepo;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.NoSuchResourceException;
import com.knowwhere.classroom.utils.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PostFilePathService postFilePathService;


    public Post findPostById(Long postId){
        return this.postRepo.findById(postId).orElseThrow(()-> new NoSuchResourceException("Post", "id", postId.toString()));
    }

    public Post createPost(Long classId, Post post, BaseUser baseUser){
        Classes classes = this.classesService.findById(classId);
        Long probableId = organizationService.getUserOrganizationTeacherId(classes.getOrganization(), baseUser);
        if ( probableId >= 1){
            //valid teacher
            this.postRepo.save(post);
        }
        throw new UnauthorizedAccessException("Class");
    }

    /**
     * This method adds a File to a Post.
     * @param postId- -> Id of the post.
     * @param multipartFile- -> The MultipartFile instance to be bound to this post.
     * @param teacher- -> The teacher who requested this method.
     * @return
     */
    @Transactional
    public Post addMultipartFileToPost(Long postId, MultipartFile multipartFile, BaseUser teacher){
        Post post = this.findPostById(postId);
        Classes classes = post.getClasses();
        Organization organization = this.organizationService.getOrganizationByBaseUser(teacher);

        String subPath = "organization/"+organization.getId()+"/classroom/"+classes.getId()+"/posts/"+post.getId()+"/files/";
        String path = subPath+multipartFile.getOriginalFilename();

        com.knowwhere.classroom.filepath.models.PostFilePath postFilePath = new PostFilePath(post, path);
        this.postFilePathService.createPostFilePath(postFilePath);


        this.storageService.store(subPath, multipartFile);
        return post;
    }

    /**
     * TODO add auth to this method.
     * This method returns an Iterator of Posts.
     * @param classId- -> The id of the class that you wish to fetch posts of.
     * @param baseUser- -> The baseUser who invoked this request.
     * @return Iterator
     */
    public Iterator<Post> getPostsByClassOrderByDesc(Long classId, BaseUser baseUser){
        return this.postRepo.getByClassesOrderByCreatedDateDesc(this.classesService.findById(classId));
    }




}
