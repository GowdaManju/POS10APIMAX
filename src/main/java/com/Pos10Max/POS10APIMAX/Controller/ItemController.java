package com.Pos10Max.POS10APIMAX.Controller;

import com.Pos10Max.POS10APIMAX.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.Pos10Max.POS10APIMAX.DTO.ItemDto;
import com.Pos10Max.POS10APIMAX.Service.ItemService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;
import com.Pos10Max.POS10APIMAX.UiRequest.ItemRequest;
import com.Pos10Max.POS10APIMAX.UiResponse.ItemResponse;
import com.Pos10Max.POS10APIMAX.UiResponse.OperationalStatusModel;
import com.Pos10Max.POS10APIMAX.UiResponse.RequestOperationName;
import com.Pos10Max.POS10APIMAX.UiResponse.RequestOperationStatus;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    Utils utils;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ItemResponse addItem(@RequestBody @Valid ItemRequest itemRequest) {
        ItemResponse returnValue=new ItemResponse();
        ItemDto itemDto = utils.getMapper().map(itemRequest, ItemDto.class);
        ItemDto saved = itemService.save(itemDto);
        if (saved == null)
            throw new RuntimeException("Failed To Add Item Plz Try Again");
        returnValue=utils.getMapper().map(saved,ItemResponse.class);

        // below code will add item links
        Link namelink = linkTo(methodOn(ItemController.class).getItemByName(returnValue.getIname())).withRel("by iname");
        Link inolink = linkTo(methodOn(ItemController.class).getItemByID(returnValue.getIno())).withRel("by ino");
        Link barcodelink = linkTo(methodOn(ItemController.class).getItemByBarcode(returnValue.getBarcode())).withRel("by barcode");
        returnValue.add(namelink, inolink, barcodelink);

        return returnValue;
    }

//    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
//    public ItemResponse updateItem(@RequestBody ItemRequest itemRequest) {
//
//        ItemDto returnValue=utils.getMapper().map(itemRequest,ItemDto.class);
//
//
//        return null;
//
//
//    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public List<ItemResponse> getAllItems() {
        List<ItemResponse> returnValue = new ArrayList<ItemResponse>();
        List<ItemDto> lists = itemService.getAllItems();
        for (ItemDto dtos : lists) {
            ItemResponse res = utils.getMapper().map(dtos, ItemResponse.class);
            Link namelink = linkTo(methodOn(ItemController.class).getItemByName(res.getIname())).withRel("by iname");
            Link inolink = linkTo(methodOn(ItemController.class).getItemByID(res.getIno())).withRel("by ino");
            Link barcodelink = linkTo(methodOn(ItemController.class).getItemByBarcode(res.getBarcode())).withRel("by barcode");
            res.add(namelink, inolink, barcodelink);
            returnValue.add(res);
        }
        return returnValue;
    }

    @GetMapping(path="/{ino}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ItemResponse getItemByID(@PathVariable long ino) {
        ItemResponse returnValue = utils.getMapper().map(itemService.getItemByIno(ino), ItemResponse.class);

        Link idlink = linkTo(methodOn(ItemController.class).getItemByID(returnValue.getIno())).withSelfRel();
        returnValue.add(idlink);
        return returnValue;
    }

    @GetMapping(path="byname/{name}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ItemResponse getItemByName(@PathVariable String name) {
        ItemResponse returnValue = utils.getMapper().map(itemService.getItemByIname(name), ItemResponse.class);
        Link self = linkTo(methodOn(ItemController.class).getItemByName(returnValue.getIname())).withSelfRel();
        returnValue.add(self);
        return returnValue;
    }

    @GetMapping(path="barcode/{barcode}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ItemResponse getItemByBarcode(@PathVariable String barcode) {
        ItemResponse returnValue = utils.getMapper().map(itemService.getItemByBarcode(barcode), ItemResponse.class);
        Link namelink = linkTo(methodOn(ItemController.class).getItemByBarcode(returnValue.getBarcode())).withSelfRel();
        returnValue.add(namelink);
        return returnValue;
    }

    @DeleteMapping(path="{ino}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public OperationalStatusModel deleteItemByID(@PathVariable long ino) {
        itemService.deleteItemByIno(ino);
        OperationalStatusModel returnValue = new OperationalStatusModel();
        returnValue.setOperationType(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        Link namelink = linkTo(methodOn(ItemController.class).deleteItemByID(ino)).withRel("Delete Link");

        return returnValue;
    }

    @GetMapping(path="/search/{prefix}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public List<ItemResponse> searchItem(@PathVariable("prefix") String prefix) {
        List<ItemResponse> returnValue = new ArrayList<ItemResponse>();

        List<ItemDto> lists = itemService.searchItem(prefix);
        for (ItemDto dtos : lists) {
            ItemResponse res = utils.getMapper().map(dtos, ItemResponse.class);
            Link namelink = linkTo(methodOn(ItemController.class).getItemByName(res.getIname())).withRel("by iname");
            Link inolink = linkTo(methodOn(ItemController.class).getItemByID(res.getIno())).withRel("by ino");
            Link barcodelink = linkTo(methodOn(ItemController.class).getItemByBarcode(res.getBarcode())).withRel("by barcode");

            res.add(namelink, inolink, barcodelink);
            returnValue.add(res);
        }
        return returnValue;
    }
    @GetMapping(path="/names",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public Object[] getItemNames() {

        return itemService.getInames();
    }

    @GetMapping(path="/barcodes",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public Object[] getBarCodes() {

        return itemService.getbarcodes();
    }

    @GetMapping(path="/cats",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public Object[] getCats() {

        return itemService.getCats();
    }

    @GetMapping(path="/manus",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public Object[] getManus() {

        return itemService.getManu();
    }

    @GetMapping(path="/udes",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public Object[] getUdes() {

        return itemService.getUdes();
    }


    @PostMapping(path="/{id}/profileimg",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> uploadSingleFileExample1(@PathVariable("id") long id, @RequestParam("file") MultipartFile partfile) {
//        log.info("Request contains, File: " + file.getOriginalFilename());
        // Add your processing logic heres
        File f=null;
        try {
            System.out.println(partfile.getName());
            f = new File(System.getProperty("user.dir")+"/images/"+ new Date().getTime()+".png");
            f.createNewFile();
//
            FileOutputStream fileOut=new FileOutputStream(f);
            fileOut.write(partfile.getBytes());
//            byte[] base64Image = Base64.getEncoder().encode(partfile.getBytes());
////            System.out.println(new String(base64Image));   // Outputs "SGVsbG8="
//            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(base64Image));
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//            BufferedImage image = ImageIO.read(bis);
//            bis.close();
//            File outputfile = new File("mgdog.png");
//            ImageIO.write(image, "png", outputfile);




//            byte[] decoded = Base64.getDecoder().decode(encoded);
//            System.out.println(new String(decoded));

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok(partfile.getName()+"****"+ partfile.getOriginalFilename()+"****"+partfile.getSize()+"*****"+f.getAbsolutePath());

    }

    @PutMapping(path="/{ino}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ItemResponse addItem(@PathVariable("ino") String ino, @RequestBody ItemRequest itemRequest) {

        ItemDto dto=itemService.updateItem(utils.getMapper().map(itemRequest,ItemDto.class));

        return utils.getMapper().map(dto,ItemResponse.class);
    }

}


//base64 to image
//byte[] base64Image = Base64.getEncoder().encode(partfile.getBytes());
//    //            System.out.println(new String(base64Image));   // Outputs "SGVsbG8="
//    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(base64Image));
//    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//    BufferedImage image = ImageIO.read(bis);
//            bis.close();
//                    File outputfile = new File("mgdog.png");
//                    ImageIO.write(image, "png", outputfile);




//           final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
//            res.add(new Link(uriString, "url"), new Link(uriString + "byname/" + res.getIname(), "item By Name"), new Link(uriString + "/" + res.getIno(), "item By ino"));