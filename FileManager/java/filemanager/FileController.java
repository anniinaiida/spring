package filemanager;
 
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
 
@Controller
public class FileController {
    @Autowired
    private fileObjectRepository fileObjectRepository;
    
    @GetMapping("/files")
    public String redirect1(Model model) {
        if (fileObjectRepository.count() > 0) {
            FileObject fo = fileObjectRepository.getOne(1L);
            System.out.println("CLASS " + fo.getClass() + " MEDIATYPE " + fo.getMediaType() + " NAME " + fo.getName());
        }
        model.addAttribute("files", fileObjectRepository.findAll());
 
        return "files";
    }
    
    @PostMapping("/files")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        FileObject fo = new FileObject();
 
        fo.setName(file.getOriginalFilename());
        fo.setMediaType(file.getContentType());
        fo.setSize(file.getSize());
        fo.setContent(file.getBytes());
        fileObjectRepository.save(fo);
 
        return "redirect:/files";
    }
    
    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        FileObject fo = fileObjectRepository.getOne(id);
 
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getSize());
        headers.add("Content-Disposition", "attachment; filename=" + fo.getName());
 
        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }
    
}