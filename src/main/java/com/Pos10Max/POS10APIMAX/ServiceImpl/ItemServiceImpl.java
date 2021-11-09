package com.Pos10Max.POS10APIMAX.ServiceImpl;

import com.Pos10Max.POS10APIMAX.DTO.ItemDto;
import com.Pos10Max.POS10APIMAX.Entity.ItemEntity;
import com.Pos10Max.POS10APIMAX.Exceptions.ResourceAlreadyExists;
import com.Pos10Max.POS10APIMAX.Exceptions.ResourceNotFoundException;
import com.Pos10Max.POS10APIMAX.Repository.ItemRepository;
import com.Pos10Max.POS10APIMAX.Service.ItemService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;

import com.Pos10Max.POS10APIMAX.DTO.ItemDto;
import com.Pos10Max.POS10APIMAX.Entity.ItemEntity;
import com.Pos10Max.POS10APIMAX.Exceptions.ResourceAlreadyExists;
import com.Pos10Max.POS10APIMAX.Exceptions.ResourceNotFoundException;
import com.Pos10Max.POS10APIMAX.Repository.ItemRepository;
import com.Pos10Max.POS10APIMAX.Service.ItemService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Utils utils;

    @Override
    @Transactional
    public ItemDto save(ItemDto itemDto) {
        try {

            if(itemDto.getIname1().equals("") || itemDto.getIname1()==null)
            {
                itemDto.setIname1(itemDto.getIname());
            }

            if(itemRepository.getItemByIname(itemDto.getIname())!=null)
                throw new ResourceAlreadyExists("Item Name Already Exist.");
            if(itemRepository.getItemByIno(itemDto.getIno())!=null)
                throw new ResourceAlreadyExists("Item-No Already Exist.");
            if(itemRepository.getItemByBarcode(itemDto.getBarcode())!=null)
                throw new ResourceAlreadyExists("Baracode Already Exist.");


            if (itemRepository.getMaxIno() != null) {
                itemDto.setIno(Long.parseLong(itemRepository.getMaxIno() + "") + 1);
                if ( itemDto.getBarcode() == null || itemDto.getBarcode().equals(""))
                    itemDto.setBarcode(Long.parseLong(itemRepository.getMaxIno() + "") + 1 + "");
            }
            else {
                itemDto.setBarcode(1+"");
            }
            entityManager.createNativeQuery("INSERT INTO item (barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,menu,hsn,udes,minstock,maxstock,rack,disp,ostock) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                    .setParameter(1, itemDto.getBarcode())
                    .setParameter(2, itemDto.getIname())
                    .setParameter(3, itemDto.getIname1())
                    .setParameter(4, itemDto.getPrate())
                    .setParameter(5, itemDto.getTaxp())
                    .setParameter(6, itemDto.getMrp())
                    .setParameter(7, itemDto.getRprice())
                    .setParameter(8, itemDto.getWprice())
                    .setParameter(9, itemDto.getCat())
                    .setParameter(10, itemDto.getMenu())
                    .setParameter(11, itemDto.getHsn())
                    .setParameter(12, itemDto.getUdes())
                    .setParameter(13, itemDto.getMinstock())
                    .setParameter(14, itemDto.getMaxstock())
                    .setParameter(15, itemDto.getRack())
                    .setParameter(16, itemDto.getDisp())
                    .setParameter(17, itemDto.getOstock())
                    .executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new ResourceAlreadyExists(e.getMessage());
        }
        return utils.getMapper().map(itemRepository.getItemByIname(itemDto.getIname()),ItemDto.class);
    }

    @Override
    public ItemDto getItemByIno(long ino) {
        ItemDto dto=utils.getMapper().map(itemRepository.getItemByIno(ino),ItemDto.class);
        if(dto==null)
        {
            throw new ResourceNotFoundException("No Record Found");
        }
        return dto;
    }



    @Override
    public ItemDto getItemByBarcode(String barcode) {
        ItemDto returnvalue=utils.getMapper().map(itemRepository.getItemByBarcode(barcode),ItemDto.class);
        if(returnvalue==null)
            throw new ResourceNotFoundException("No record Found");
        return returnvalue;
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto) {
            ItemEntity entity=itemRepository.getItemByIno(itemDto.getIno());
            if(entity==null)
                throw new RuntimeException("Invalid Ino");


           entity=utils.getMapper().map(itemDto,ItemEntity.class);

           ItemEntity updates=itemRepository.save(entity);
           if(updates==null)
               throw  new RuntimeException("Unable to Update Item , Plz check the fields");

        return utils.getMapper().map(updates,ItemDto.class);
    }

    @Override
    public boolean deleteItemByIno(long ino) {
        if(itemRepository.getItemByIno(ino)==null)
           throw new ResourceNotFoundException("No Such Item Exist in DataBase");

        itemRepository.deleteItemByino(ino);
        if(itemRepository.getItemByIno(ino)!=null)
            throw new RuntimeException("Failed TO Delete Item! Try Again!");
        return true;
    }

    @Override
    public boolean deleteItemByBarcode(String ino) {
        return false;
    }

    @Override
    public ItemDto getItemByIname(String iname) {

        ItemDto retunValue=new ItemDto();
        if(itemRepository.getItemByIname(iname)==null)
            throw new ResourceNotFoundException("Invalid Item Name");

        retunValue=utils.getMapper().map(itemRepository.getItemByIname(iname),ItemDto.class);
        return retunValue;
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<ItemDto> returnValue=new ArrayList<ItemDto>();

        List<ItemEntity> datas=itemRepository.findAll();

        for(ItemEntity item:datas)
        {
            returnValue.add(utils.getMapper().map(item,ItemDto.class));
        }

        return returnValue;
    }

    @Override
    public List<ItemDto> searchItem(String prefix) {
        List<ItemDto> returnValue=new ArrayList<ItemDto>();
        Iterable<ItemEntity> items=itemRepository.searchItem(prefix);
        for(ItemEntity item:items)
        {
            returnValue.add(utils.getMapper().map(item,ItemDto.class));
        }
        return returnValue;

    }

    @Override
    public Object[] getInames() {
        return itemRepository.getItemNames();
    }
    @Override
    public Object[] getbarcodes() {
        return itemRepository.getbarcodes();
    }
    @Override
    public Object[] getCats() {
        return itemRepository.getItemCats();
    }

    @Override
    public Object[] getManu() {
        return itemRepository.getItemManu();
    }

    @Override
    public Object[] getUdes() {
        return itemRepository.getItemUdes();
    }

//    public List<Object> getAll() {
////        List<Object> abc=itemRepository();
//
//        return null;
//    }

//    private List<Object> itemRepository() {
//    }
//
//    @Override
//    public Object[] getInames() {
//        return new Object[0];
//    }
}
