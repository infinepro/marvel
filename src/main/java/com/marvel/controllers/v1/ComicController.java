package com.marvel.controllers.v1;

import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.domain.Comic;
import com.marvel.repositories.ComicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public")
public class ComicController {

    private final ComicRepository comicRepository;

    public ComicController(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @GetMapping("/comics")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataContainerModel<Comic> getComics() {
        //todo:`1
        return null;
    }
}
