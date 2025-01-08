package com.vakya.notesservies.advices;

import com.vakya.notesservies.dtos.ErrorDto;
import com.vakya.notesservies.exceptions.NotesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotesNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(NotesNotFoundException exception){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        return  new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleSearchException(Exception exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }


}
