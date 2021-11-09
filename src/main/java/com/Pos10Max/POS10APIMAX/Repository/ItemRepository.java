package com.Pos10Max.POS10APIMAX.Repository;

import com.Pos10Max.POS10APIMAX.DTO.ItemDto;
import com.Pos10Max.POS10APIMAX.Entity.ItemEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity,Integer>, CrudRepository<ItemEntity,Integer> {

////    @Autowired
////    EntityManager entityManager;
////@PersistenceContext
////@Autowired
//// EntityManager entityManager;
//
//    @Autowired
//    EntityManager entityManager;

    ItemEntity save(ItemDto itemDto);

 @Query(value="SELECT * FROM item",nativeQuery = true)
    List<ItemEntity> findAll();

    @Query(value="select distinct iname from item ", nativeQuery=true)
    Object[] getItemNames();

   @Query(value="select distinct barcode from item ", nativeQuery=true)
   Object[] getbarcodes();

    @Query(value="select distinct cat from item ", nativeQuery=true)
    Object[] getItemCats();

    @Query(value="select distinct menu from item ", nativeQuery=true)
    Object[] getItemManu();

   @Query(value="select distinct udes from item ", nativeQuery=true)
   Object[] getItemUdes();

    @Modifying
    @Transactional
    @Query(value="delete from item  where ino= :ino", nativeQuery = true)
    void deleteItemByino(@Param("ino") long ino);


    @Query(value="select max(ino) from item ", nativeQuery=true)
    Object getMaxIno();

    @Query(value="select * from item  where  iname= :iname ", nativeQuery = true)
    ItemEntity getItemByIname(@Param("iname") String iname);

    @Query(value="select * from item  where  ino= :ino ", nativeQuery = true)
    ItemEntity getItemByIno(@Param("ino") long ino);

   @Query(value="select * from item  where  barcode= :barcode ", nativeQuery = true)
   ItemEntity getItemByBarcode(@Param("barcode") String barcode);

   @Query(value="select * from item  where   iname like %:prefix% or ino like %:prefix% or barcode like %:prefix% order by ino ", nativeQuery = true)
   List<ItemEntity> searchItem(@Param("prefix") String prefix);


//    @Transactional
//    public default void insertWithQuery(ItemDto person) {
//        entityManager.createNativeQuery("INSERT INTO person (id, first_name, last_name) VALUES (?,?,?)")
////                .setParameter(1, person.getId())
////                .setParameter(2, person.getFirstName())
////                .setParameter(3, person.getLastName())
//                .executeUpdate();
//    }



//    @Modifying
//    @Query(value="update author set last_name= :lastName where first_name = :firstName", nativeQuery=true)
//    void updateitem(String firstName, String lastName);

//    ...
}


