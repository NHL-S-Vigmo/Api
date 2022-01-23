package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.controllers.logic.AbstractVigmoController;
import com.nhlstenden.student.vigmo.dto.FileDto;
import com.nhlstenden.student.vigmo.models.File;
import com.nhlstenden.student.vigmo.services.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "File Controller", protocols = "GET,PUT,POST,DELETE", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("files")
public class FileController extends AbstractVigmoController<FileService, FileDto> {

    public FileController(FileService service) {
        super(service);
    }

    @ApiOperation(value = "Gets the related variables for the requested slideshow")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Invalid file_key")})
    @GetMapping("/{file_key}/render")
    ResponseEntity<byte[]> renderFile(@PathVariable("file_key") final String file_key){
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        File media = service.getRawEntityByKey(file_key);
        headers.setContentType(MediaType.parseMediaType(media.getMimeType()));
        headers.setContentDisposition(ContentDisposition.inline().filename(media.getName()).build());
        headers.set("X-File-Id", Long.toString(media.getId()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(media.getData());
    }
}
