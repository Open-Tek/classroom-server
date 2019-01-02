package com.knowwhere.classroom.post.services;

import com.knowwhere.classroom.classes.models.Classes;
import com.knowwhere.classroom.classes.models.UserClassesSubscription;
import com.knowwhere.classroom.post.models.Post;
import com.knowwhere.classroom.post.models.Submission;
import com.knowwhere.classroom.post.models.SubmissionFilePaths;
import com.knowwhere.classroom.post.repos.SubmissionRepo;
import com.knowwhere.classroom.user.models.BaseUser;
import com.knowwhere.classroom.utils.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepo submissionRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private StorageService storageService;


    /**
     * This method adds a submission to the class provided the post is an assignment and the Student is part
     * of the class. Another check has been made to ensure upload time is less than the due date of the assignment.
     * This method can be called when there are multiple files to be uploaded by the ui multiple times.
     * @param assignmentPostId- -> The id of the post thats an assignment.
     * @param student- -> The student that wishes to upload an Assignment.
     * @param multipartFile- -> The file to be uploaded.
     * @return Submission
     */
    @Transactional
    public Submission createSubmission(Long assignmentPostId, BaseUser student, MultipartFile multipartFile){
        Post possibleAssignment = this.postService.findPostById(assignmentPostId);
        Classes postClass = possibleAssignment.getClasses();
        UserClassesSubscription userClassesSubscription = new UserClassesSubscription(postClass, student);
        if( possibleAssignment.getAssignment() && postClass.getStudents().contains(userClassesSubscription)){
            //valid student can submit
            // TODO ADD A SUBMITTED CHECK ....

            if (System.currentTimeMillis() < possibleAssignment.getDueDate().getTime()){
                //can submit

                Submission submission = this.findByBaseUserAndPostId(possibleAssignment, student);
                if (submission == null)
                    submission = new Submission(student, possibleAssignment);

                String subPath = "organization/"+postClass.getOrganization().getId()+"/classroom/"+postClass.getId()+"/posts/"+possibleAssignment.getId()+
                        "/submissions/"+student.getId()+"/";

                String actualPath = subPath+multipartFile.getOriginalFilename();

                SubmissionFilePaths submissionFilePaths = new SubmissionFilePaths();
                submissionFilePaths.setFileName(actualPath);
                submissionFilePaths.setSubmission(submission);

                submission.getSubmissionFilePaths().add(submissionFilePaths);

                this.storageService.store(subPath, multipartFile);

                this.submissionRepo.save(submission);


                return submission;

            }



        }
        throw new UnauthorizedAccessException("Submission");
    }


    private Submission findByBaseUserAndPostId(Post post, BaseUser user){
        return this.submissionRepo.findByParentAssignmentAndSubOwner(post, user);
    }

}
