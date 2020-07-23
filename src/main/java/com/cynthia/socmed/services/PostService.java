package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.LikesDao;
import com.cynthia.socmed.DAO.LinkPreviewDao;
import com.cynthia.socmed.DAO.PostDao;
import com.cynthia.socmed.comp.PostTimeComparator;
import com.cynthia.socmed.models.Likes;
import com.cynthia.socmed.models.LinkPreview;
import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    LikesDao likesDao;

    @Autowired
    LinkPreviewDao linkPreviewDao;

    @Autowired
    UserService userService;


    public Post[] showAllPosts(User user) {
        List<Post> posts = postDao.findAll();
        List<User> friends = userService.getFriendship(user);

        List<Post> userPosts = new ArrayList<Post>();
        for (Post po : posts) {
            if (po.getAuthor().equals(user.getUsername())) {
                userPosts.add(po);
            }
        }
        for (User friend : friends) {
            for (Post p : postDao.findByAuthor(friend.getUsername())) {
                userPosts.add(p);
            }
        }
        Post[] osef = new Post[userPosts.size()];
        Post[] usersPostsArray = userPosts.toArray(osef);
        Arrays.sort(usersPostsArray, new PostTimeComparator());

        return usersPostsArray;
    }

    public Post[] showFriendsPosts(User item) {
        List<Post> posts = postDao.findAll();
        List<Post> userPosts = new ArrayList<Post>();
        for (Post po : posts) {
            if (po.getAuthor().equals(item.getUsername())) {
                userPosts.add(po);
            }
        }
        Post[] osef = new Post[userPosts.size()];
        Post[] usersPostsArray = userPosts.toArray(osef);
        Arrays.sort(usersPostsArray, new PostTimeComparator());
        return usersPostsArray;
    }

    public void addPostPicture(MultipartFile file, Post p) throws IOException {

        List <Post> posts = postDao.findAll();
        List<Integer> postIds = new ArrayList<>();
        int num;
        String n;
        for(Post po : posts) {
            postIds.add(po.getId());
        }
         if(!postIds.isEmpty()) {
             num = Collections.max(postIds) + 1;
         } else {
             num = 0;
         }
        n = Integer.toString(num);
        File convertFile = new File("C:\\Users\\CynthiaM\\Desktop\\socmed\\images\\"+n+".jpg");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(String.valueOf(convertFile));
        fout.write(file.getBytes());
        fout.close();
        p.setPicture(convertFile);
        p.setPicturePath("images/"+n+".jpg");

    }

    public Post makePost(String s, Post p) {
        StringBuffer sb = new StringBuffer();
        String[] arrOfStr = s.split("\\r?\\n?\\s");
        for (int i = 0; i < arrOfStr.length; i++) {
            sb.append(arrOfStr[i] + " ");

            try {
                if (arrOfStr[i].startsWith("https://")) {
                    LinkPreview w = new LinkPreview();
                    Document doc = Jsoup.connect(arrOfStr[i]).get();
                    String description = getMetaTagContent(doc, "meta[name=description]");
                    String title = getMetaTagContent(doc, "meta[property=og:title]");
                    String image = getMetaTagContent(doc, "meta[property=og:image]");
                    String siteName = getMetaTagContent(doc, "meta[property=og:site_name]");
                    if (!image.isEmpty()) {
                        w.setImage(image);
                    }
                    w.setSiteName(siteName);
                    w.setDescription(description);
                    w.setTitle(title);

                    linkPreviewDao.save(w);
                    p.setLinkPreview(w);
                    p.setUrl(arrOfStr[i]);
                    sb.delete(sb.indexOf("https"), sb.length());
                }

            } catch (Exception e) {
                p.setUrl(arrOfStr[i]);

            }

            if ((sb.toString().contains("soundcloud") && arrOfStr[i].startsWith("src="))) {
                String em = arrOfStr[i];
                int lastInd = em.indexOf(">");
                String embed = em.substring(5, lastInd - 1);
                p.setEmbedLink(embed);
            }

            if (sb.toString().contains("beatport") && arrOfStr[i].startsWith("src=")) {
                String em = arrOfStr[i];
                int last = em.indexOf("track") + 5;
                String emb = em.substring(5, last);
                p.setEmbedLink(emb);
            }
        }
        if (sb.toString().contains("<iframe") && sb.toString().contains("div>")) {
            sb.delete(sb.indexOf("<iframe"), sb.indexOf("div>") + 4);
        }

        if (sb.toString().contains("<iframe") && sb.toString().contains("iframe>")) {
            sb.delete(sb.indexOf("<iframe"), sb.indexOf("iframe>") + 7);
        }

        String safe = Jsoup.clean(sb.toString(), Whitelist.basic());
        if (!safe.equals("")) {
            p.setText(safe);
        } else {
            p.setText(null);
        }

        return p;

    }

    public Post generatePost(String s, String username) {
        LocalDate localDate = LocalDate.now();
        Post p = new Post();
        p.setAuthor(username);
        p.setDate(localDate);
        makePost(s, p);
        return p;
    }

    public boolean postExists(Post p) {
        if (p.getUrl() == null
                && p.getEmbedLink() == null
                && p.getLinkPreview() == null
                && p.getPicture() == null
                && p.getText() == null) {
            return false;
        }
        return true;
    }

    private String getMetaTagContent(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("content");
        }
        return "";
    }


    public int countLikes(Post p) {
        List<Likes> likes = likesDao.findAllByPost(p);
        return likes.size();
    }

    public Post findById(int id) {

        return postDao.findById(id);
    }

    public Post save(Post p) {

        return postDao.save(p);
    }

    public List<Likes> likePosts(int id, User u) {
        Post p = postDao.findById(id);
        List<Likes> likedPosts = likesDao.findAllByUser(u);
        List<Integer> postIds = new ArrayList<>();
        for (Likes lis : likedPosts) {
            postIds.add(lis.getPost().getId());
        }
        if (postIds.contains(p.getId())){
            for (Likes l : likedPosts) {
                if (l.getPost().getId()==p.getId() && l.getUser().getId()==u.getId()) {
                    likesDao.delete(l);
                }
            }
        } else {
            Likes like = new Likes();
            like.setUser(u);
            like.setPost(p);
            likesDao.save(like);

        }
        p.setLikes(countLikes(p));
        postDao.save(p);
        return likedPosts;
    }

    public void deletePost (Post p, User u, String author) {
        if(author.equals(u.getUsername()))
            postDao.delete(p);

    }


    public void updatePost (String username, Post p, String text) {
        if (p.getAuthor().equals(username)) {
            makePost(text, p);
        }
    }
}


