package com.cynthia.socmed.comp;

import com.cynthia.socmed.models.Post;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PostTimeComparator implements Comparator<Post> {

    @Override
    public int compare(Post post, Post t1) {
        if (post.getId() <  t1.getId())
            return 1;
        if (post.getId() >  t1.getId())
            return -1;
        return 0;
    }


}
