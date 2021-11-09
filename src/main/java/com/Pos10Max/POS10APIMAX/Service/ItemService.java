package com.Pos10Max.POS10APIMAX.Service;

import com.Pos10Max.POS10APIMAX.DTO.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto save(ItemDto itemDto);
    ItemDto getItemByIno(long ino);
    ItemDto getItemByBarcode(String barcode);
    ItemDto updateItem(ItemDto itemDto);
    boolean deleteItemByIno(long ino);
    boolean deleteItemByBarcode(String ino);
    ItemDto getItemByIname(String iname);
    List<ItemDto> getAllItems();


    List<ItemDto> searchItem(String prefix);

    Object[] getInames();
    Object[] getCats();
    Object[] getManu();
    Object[] getUdes();
    Object[] getbarcodes();

}
