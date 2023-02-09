package nextstep.subway.ui;

import nextstep.subway.domain.exceptions.CanNotAddSectionException;
import nextstep.subway.domain.exceptions.CanNotSplitSectionException;
import nextstep.subway.domain.exceptions.NotPositiveNumberException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleIllegalArgsException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @ExceptionHandler({CanNotAddSectionException.class, CanNotSplitSectionException.class, NotPositiveNumberException.class})
    public ResponseEntity<Void> exception(RuntimeException e) {
        return ResponseEntity.unprocessableEntity().build();
    }
}
