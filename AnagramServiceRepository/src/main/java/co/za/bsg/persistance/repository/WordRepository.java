package co.za.bsg.persistance.repository;

import co.za.bsg.persistance.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, String> {

    @Query("select w from Word w " +
            "where :systemDate between w.effectiveFrom AND w.effectiveTo ")
    List<Word> findAllActiveWords(@Param("systemDate") LocalDateTime systemDate);

    @Query("select w from Word w " +
            "where :systemDate between w.effectiveFrom AND w.effectiveTo ")
    Page<Word> pageAllActiveWords(@Param("systemDate") LocalDateTime systemDate, Pageable pageable);

    @Query("select w from Word w " +
            "where w.wordText  = :wordText " +
            "and :systemDate between w.effectiveFrom AND w.effectiveTo ")
    Word findActiveWord(@Param("wordText") String wordText,
                        @Param("systemDate") LocalDateTime systemDate);

    @Transactional
    @Modifying
    @Query("update Word w " +
            "set w.effectiveTo = :systemDate " +
            "where w.wordText  = :wordText " +
            "and :systemDate between w.effectiveFrom AND w.effectiveTo ")
    void retireWord(@Param("wordText") String wordText,
                    @Param("systemDate") LocalDateTime systemDate);

    @Query("select w from Word w " +
            "where w.effectiveFrom <> :dictionaryStartDate")
    Page<Word> findAllAddedWords(@Param("dictionaryStartDate") LocalDateTime dictionaryStartDate, Pageable pageable);

    @Query("select w from Word w " +
            "where w.effectiveTo <> :endOfTimeDate")
    Page<Word> findAllRemovedWords(@Param("endOfTimeDate") LocalDateTime endOfTimeDate, Pageable pageable);
}
