package edigest.enotes.controller;

import edigest.enotes.entity.Notes;
import edigest.enotes.entity.User;
import edigest.enotes.service.NotesService;
import edigest.enotes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class Home {

    @GetMapping("/")
    public String index() {
        return "index";
    }


       @GetMapping("/register")
    public String register() {
        return "register";
       }

       @GetMapping("/login")
    public String login() {
        return "login";
       }

    @GetMapping("/add_notes")
    public String addNotes() {
        return "login";
    }

//    @GetMapping("/view_notes")
//    public String viewNotes() {
//        return "redirect:/avail_books";
//    }

       @Autowired
       UserService userService ;
    @Autowired
    NotesService  notesService ;

       @PostMapping("/UserRegister")
    public String userRegister(@ModelAttribute User user , HttpSession session) {


           if( userService.checkUserByEmail(user.getEmail() ) ){

                     session.setAttribute("msg","Email Already Exists");

                 }else {
                     System.out.println(user);
                     User newUser = userService.saveUser(user);
                     if(newUser!=null){
                         session.setAttribute("msg","User Successfully Registered");
                     }else
                         {
                         session.setAttribute("msg","Something Went Wrong");
                         }

                 }

           return "redirect:/register";

       }




    @GetMapping("/view_notes")
    public String viewNote(Model model , @RequestParam(defaultValue = "0") int pageNo ) {


        Page<Notes> notes = notesService.getAllNotes(pageNo);

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalElements",notes.getTotalElements());
        model.addAttribute("totalPage",notes.getTotalPages());
        model.addAttribute("notesList",notes.getContent());


        return "view_note";
    }
    @GetMapping("/search_book")
    public String searchBook(@Param("keyword")  String keyword ,Model model , @RequestParam(defaultValue = "0") int pageNo) {

           Page<Notes> notes = notesService.searchNotes(keyword,pageNo);

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalElements",notes.getTotalElements());
        model.addAttribute("totalPage",notes.getTotalPages());
        model.addAttribute("notesList",notes.getContent());

        return "view_note";



    }



}
