package com.marvel.controllers.v1.api;

import com.marvel.api.v1.model.ModelDataWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api("Image controller")
public interface ImageControllerApi {

    @ApiOperation(value = "This will get image file by name")
    byte[] getImageWithMediaType(
            @ApiParam(value = "The name of the image") String name
    ) throws IOException;

    @ApiOperation(value = "This will upload image file on server and return model with generated new file name.")
    ModelDataWrapper<Object> uploadImage(MultipartFile file) throws IOException;

}
