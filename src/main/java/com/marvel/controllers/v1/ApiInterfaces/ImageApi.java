package com.marvel.controllers.v1.ApiInterfaces;

import com.marvel.api.v1.model.ModelDataWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api("Image controller")
public interface ImageApi {

    @ApiOperation(value = "This will get image file by name")
    byte[] getImageWithMediaType(String name) throws IOException;

    @ApiOperation(value = "This will upload image file on server and return model with generated new file name.")
    ModelDataWrapper<Object> uploadImage(MultipartFile file) throws IOException;

}
