package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RenderImg {

  @Autowired
  private ProdottoDao prodottoDao;

  @GetMapping("/productImg")
  public ResponseEntity<byte[]> getImage(@RequestParam("id") Long id) {
    Optional<Prodotto> opPro = prodottoDao.findProdottoById(id);
    if (opPro.isEmpty() || opPro.get().getImg() == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Prodotto p = opPro.get();
    byte[] image = p.getImg();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "image/jpeg");
    headers.add("Content-Length", String.valueOf(image.length));
    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }
}
