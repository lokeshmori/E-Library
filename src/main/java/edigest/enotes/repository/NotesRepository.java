package edigest.enotes.repository;

import edigest.enotes.entity.Notes;
import edigest.enotes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer> {

public Page<Notes> findByUser(User user , Pageable pageable);

    //public Page<Notes>  findAllNotes( Pageable pageable);


    @Query("select b from Notes b where  b.title LIKE %?1%" + " OR b.description LIKE %?1% " )
    public Page<Notes> search( String keyword , Pageable pageable);





}
