package edigest.enotes.controller;


import edigest.enotes.entity.Notes;
import edigest.enotes.entity.User;
import edigest.enotes.repository.UserRepository;
import edigest.enotes.service.NotesService;
import edigest.enotes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesService notesService;

    @ModelAttribute
    public User getUser(Principal principal, Model model) {

        String name = principal.getName();
        User user = userRepository.findByEmail(name);
        model.addAttribute("user", user);
        return user;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/add_notes")
    public String addNote() {
        return "add_note";
    }

    @PostMapping("/update_notes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Model model, Principal principal ,@RequestParam MultipartFile file) {


        notes.setDate(LocalDate.now());
        notes.setUser(getUser(principal, model));
        try {


            Notes saveNotes = notesService.saveNotes(notes, file);

            if (saveNotes != null) {
                session.setAttribute("msg", "Notes has been updated successfully");
            } else {
                session.setAttribute("msg", "Something went wrong on server");
            }

        }catch (Exception e){
            session.setAttribute("msg", "Something went wrong on server");
        }

        return "redirect:/user/view_notes";
    }

    @GetMapping("/delete_notes/{id}")
    public String deleteNotes(@PathVariable int id, HttpSession session) {

        boolean flag = notesService.deleteNotes(id);
        if (flag) {
            session.setAttribute("msg", "Notes has been deleted successfully");

        } else {
            session.setAttribute("msg", "Something went wrong on server");
        }
        return "redirect:/user/view_notes";

    }

    @GetMapping("/edit_notes/{id}")
    public String editNote(@PathVariable int id, Model model) {
        Notes notes = notesService.getNotesById(id);
        model.addAttribute("notes", notes);

        return "edit_note";
    }

    @GetMapping("/view_notes")
    public String viewNote(Model model, Principal principal, @RequestParam(defaultValue = "0") int pageNo) {

        User user = getUser(principal, model);
        Page<Notes> notes = notesService.getNotesByUser(user, pageNo);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalElements", notes.getTotalElements());
        model.addAttribute("totalPage", notes.getTotalPages());
        model.addAttribute("notesList", notes.getContent());


        return "view_note";
    }

    @PostMapping("/saveNotes")
    public String addNote(@ModelAttribute Notes notes, HttpSession httpSession, Principal principal, Model model , @RequestParam MultipartFile file) {

        notes.setDate(LocalDate.now());
        // notes.setPdfFile(notes.getPdfFile());
        notes.setUser(getUser(principal, model));





              try {


                  Notes saveNotes = notesService.saveNotes(notes, file);

            if (saveNotes != null) {
                httpSession.setAttribute("msg", "Notes has been saved successfully");

            } else {
                httpSession.setAttribute("msg", "Something went wrong on server");
            }
              }catch (Exception e) {
                  model.addAttribute("msg", "Something went wrong on server");
              }




            return "redirect:/user/add_notes";


    }





}
