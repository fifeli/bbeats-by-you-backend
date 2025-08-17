package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findFirstById(long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value="UPDATE ORDERS SET COMPLETE=1 WHERE ID=:id")
    int completeOrder(@Param("id") long id);

    Page<Order> findOrdersByCompleteTrue(Pageable pageable);

    Page<Order> findOrdersByCompleteTrueAndEmail(@Param("email") String email, Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT * FROM ORDERS WHERE ((FULL_NAME LIKE :keyword OR EMAIL LIKE :keyword OR DESCRIPTION LIKE :keyword OR MOBILE LIKE :keyword OR POSTAL_CODE LIKE :keyword OR SONG_LENGTH LIKE :keyword OR TEMPO_VALUE LIKE :keyword OR PRICE LIKE :keyword OR ADDRESS LIKE :keyword)) AND COMPLETE =:isComplete")
    Page<Order> findOrdersByType(@Param("keyword") String keyword, @Param("isComplete") int isComplete, Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT * FROM ORDERS WHERE ((FULL_NAME LIKE :keyword OR EMAIL LIKE :keyword OR DESCRIPTION LIKE :keyword OR MOBILE LIKE :keyword OR POSTAL_CODE LIKE :keyword OR SONG_LENGTH LIKE :keyword OR TEMPO_VALUE LIKE :keyword OR PRICE LIKE :keyword OR ADDRESS LIKE :keyword))")
    Page<Order> findOrdersWithoutType(@Param("keyword") String keyword, Pageable pageable);
}
