package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.RepositoryLayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.File;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.FileRepo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@DataJpaTest
class FileRepoTest {

    @Autowired
    private FileRepo underTest;
    /**
     * this test has to check the file can be found by its id and isDeleted boolean
     */
    @Test
    void itShouldFindFileByIdAndIsDeleted() {
        //given
        File file = new File(
                "image/png",
                "my_picture",
                "uuiu-uuii-iuu",
                "C://reasmlar",
                false
        );

        //when
        File save = underTest.save(file);
        File expected = underTest.findByIdAndIsDeleted(save.getId(), false).orElseThrow(ResourceNotFoundException::new);

        //then
        assertThat(expected).isEqualTo(save);
    }


    /**
     * this test has to check it throws exception is it cannot find
     * file by its id and isDeleted boolean
     */
    @Test
    void itShouldThrowExceptionWhenDoesNotFindFileByIdAndIsDeleted() {
        //given
        File file = new File(
                "image/png",
                "my_picture",
                "uuiu-uuii-iuu",
                "C://reasmlar",
                false
        );

        //when
        File save = underTest.save(file);
        //then
        assertThrows(ResourceNotFoundException.class, () -> underTest.findByIdAndIsDeleted((save.getId()+1), false).orElseThrow(ResourceNotFoundException::new));
    }

}