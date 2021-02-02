package app.ankifill.controller;

import app.ankifill.model.AnkiCard;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class OutputController {
    @PostMapping("/save")
    @SneakyThrows
    public ResponseEntity<Resource> save(@RequestBody List<AnkiCard> ankiCards) {
        File file = ResourceUtils.getFile("result.csv");
        file.delete();
        file.createNewFile();
        Path path = Paths.get(file.getAbsolutePath());

        for (AnkiCard ankiCard : ankiCards) {
            Files.write(path, ankiCard.toString().getBytes(), StandardOpenOption.APPEND);
        }


        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/saveFile")
    @SneakyThrows
    public ResponseEntity<Resource> saveFile() {
        File file = ResourceUtils.getFile("result.csv");
        Path path = Paths.get(file.getAbsolutePath());


        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
