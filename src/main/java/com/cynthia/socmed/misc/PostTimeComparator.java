package com.cynthia.socmed.misc;

import com.cynthia.socmed.models.Post;

import java.util.Comparator;

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
