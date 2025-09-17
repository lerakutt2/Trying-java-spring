package com.firstsite.demo.controllers;

import com.firstsite.demo.models.Post;
import com.firstsite.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    // создаем переменную, ссылающуюся на репозиторий
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) { // названия name из тега input
        Post post = new Post(title, anons, full_text);
        postRepository.save(post); // добавляем объект в бд
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long postID, Model model) {
        if (!postRepository.existsById(postID)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(postID);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add); // переводим в ArrayList если запись существует
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long postID, Model model) {
        if (!postRepository.existsById(postID)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(postID);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add); // переводим в ArrayList если запись существует
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long postID, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(postID).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post); // обновляем существующий объект

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogUpdate(@PathVariable(value = "id") long postID, Model model) {
        Post post = postRepository.findById(postID).orElseThrow();
        postRepository.delete(post); // обновляем существующий объект

        return "redirect:/blog";
    }
}
