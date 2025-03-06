package edigest.enotes.service;

import edigest.enotes.entity.Notes;
import edigest.enotes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface NotesService {

    public Notes saveNotes(Notes notes , MultipartFile file) throws Exception;

    public Notes getNotesById(int id);
    public Page<Notes> getAllNotes(int pageNo);
    public Page<Notes> getNotesByUser(User user , int pageNo);
    public Page<Notes> searchNotes(String search, int pageNo);

    public Notes updateNotes(Notes notes);
    public boolean deleteNotes(int id);


}
