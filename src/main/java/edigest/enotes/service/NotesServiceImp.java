package edigest.enotes.service;

import edigest.enotes.entity.Notes;
import edigest.enotes.entity.User;
import edigest.enotes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class NotesServiceImp implements NotesService {



   @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    NotesRepository notesRepository;

    @Override
    public Notes saveNotes(Notes notes , MultipartFile file) throws IOException {

        Path path = Paths.get(uploadDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        notes.setFilePath(filePath.toString().replace("\\", "/"));


        return notesRepository.save(notes);
    }

    @Override
    public Notes getNotesById(int id) {

        return notesRepository.findById(id).get();
    }


    public Page<Notes> getNotesByUser(User user , int  pageNo) {



        Pageable pageable = PageRequest.of(pageNo,5);

        return notesRepository.findByUser(user ,  pageable );
    }

    @Override
    public Notes updateNotes(Notes notes) {
        return notesRepository.save(notes);

    }

    @Override
    public boolean deleteNotes(int id) {

       Notes notes = notesRepository.findById(id).get() ;
        if(notes != null){
            notesRepository.delete(notes);
            return true;
        }
        return false;

    }

    @Override
    public Page<Notes> getAllNotes(int pageNo) {

        Pageable pageable = PageRequest.of(pageNo, 5);
        return notesRepository.findAll(pageable);

    }

    @Override
    public Page<Notes> searchNotes(String search, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo, 5);

         return notesRepository.search(search, pageable);
    }



}
