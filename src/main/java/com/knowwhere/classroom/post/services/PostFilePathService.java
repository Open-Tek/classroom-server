package com.knowwhere.classroom.post.services;

import com.knowwhere.classroom.post.repos.PostFilePathRepo;
import com.knowwhere.classroom.utils.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.knowwhere.classroom.filepath.models.PostFilePath;

@Service
public class PostFilePathService {

    @Autowired
    private PostFilePathRepo postFilePathRepo;

    @Autowired
    private StorageService storageService;

    public PostFilePath getById(Long id){
        return this.postFilePathRepo.findById(id).orElseThrow(() -> new NoSuchResourceException("PostFilePath", "id", id.toString()));
    }

    public PostFilePath createPostFilePath(PostFilePath postFilePath){
        return this.postFilePathRepo.save(postFilePath);
    }

    /**
     * TODO ADD AUTH--- THIS METHOD MUST TAKE Classes, BaseUser instances as args.
     * Returns a File as a Resource to be served
     * @param id- -> The of the PostFilePath instance.
     * @return Resource
     */
    public Resource getActualFileById(Long id){
        return this.storageService.loadAsResource(this.getById(id).getFileName());
    }



}
