package main.Repositorys;

import main.models.Parcel;
import main.models.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/23/15.
 */
@Transactional
public interface ParcelRepository extends JpaRepository<Parcel, Long> {



    @Query("SELECT u FROM Parcel u WHERE (u.barcode LIKE CONCAT('%',:barcode,'%')" +
            " OR u.reciever LIKE CONCAT('%',:reciever,'%')" +
            " OR u.address LIKE CONCAT('%',:address,'%')" +
            " OR u.recievedBy LIKE CONCAT('%',:recievedBy,'%'))" +
            " AND u.organisation.id=:organisation order by u.id desc")
    Page<Parcel> findByBarcodeOrRecieverOrAddressOrRecievedByAndOrganisationOrderByIdDesc(@Param("barcode")String barcode,
                                                                                          @Param("reciever")String reciever,
                                                                                          @Param("address")String address,
                                                                                          @Param("recievedBy")String recievedBy,
                                                                                          @Param("organisation")long organisation,
                                                                                          Pageable pageable);

    @Query("SELECT u FROM Parcel u WHERE (u.barcode LIKE CONCAT('%',:barcode,'%')" +
            " OR u.reciever LIKE CONCAT('%',:reciever,'%')" +
            " OR u.address LIKE CONCAT('%',:address,'%')" +
            " OR u.recievedBy LIKE CONCAT('%',:recievedBy,'%')) order by u.id desc")
    Page<Parcel> findByBarcodeOrRecieverOrAddressOrRecievedByOrderByIdDesc(@Param("barcode")String barcode,
                                                                           @Param("reciever")String reciever,
                                                                           @Param("address")String address,
                                                                           @Param("recievedBy")String recievedBy,
                                                                           Pageable pageable);

    @Query("SELECT u FROM Parcel u WHERE (u.barcode LIKE CONCAT('%',:barcode,'%')" +
            " OR u.reciever LIKE CONCAT('%',:reciever,'%')" +
            " OR u.address LIKE CONCAT('%',:address,'%')" +
            " OR u.recievedBy LIKE CONCAT('%',:recievedBy,'%'))" +
            " AND u.region.id=:region AND u.status=:status AND u.zone.id=NULL order by u.id desc")
    Page<Parcel> findByBarcodeOrRecieverOrAddressOrRecievedByAndRegionAndStatusOrderByIdDesc(@Param("barcode")String barcode,
                                                                                             @Param("reciever")String reciever,
                                                                                             @Param("address")String address,
                                                                                             @Param("recievedBy")String recievedBy,
                                                                                             @Param("region")long region,
                                                                                             @Param("status")int status,
                                                                                             Pageable pageable);

    @Query("SELECT u FROM Parcel u WHERE (u.barcode LIKE CONCAT('%',:barcode,'%')" +
            " OR u.reciever LIKE CONCAT('%',:reciever,'%')" +
            " OR u.address LIKE CONCAT('%',:address,'%')" +
            " OR u.recievedBy LIKE CONCAT('%',:recievedBy,'%'))" +
            " AND u.zone.id=:zone AND u.status=:status AND u.courier=0 order by u.id desc")
    Page<Parcel> findByBarcodeOrRecieverOrAddressOrRecievedByAndZoneAndStatusOrderByIdDesc(@Param("barcode")String barcode,
                                                                                           @Param("reciever")String reciever,
                                                                                           @Param("address")String address,
                                                                                           @Param("recievedBy")String recievedBy,
                                                                                           @Param("zone")long zone,
                                                                                           @Param("status")int status,
                                                                                           Pageable pageable);


    Page<Parcel> findByUserId(@Param("user")long user,Pageable pageable);
    Page<Parcel> findByOrganisation(@Param("organisation")long organisation,Pageable pageable);
    @Query("SELECT u FROM Parcel u where (u.deliveryDate between to_date(:dateStart,'MM/DD/YYYY') " +
            "and to_date(:dateEnd,'MM/DD/YYYY')) order by u.id desc")
    Page<Parcel> findByDateRange(@Param("dateStart")Date dateStart,@Param("dateEnd")Date dateEnd,Pageable pageable);
    @Query("SELECT u FROM Parcel u where (u.deliveryDate between to_date(:dateStart,'MM/DD/YYYY') " +
            "and to_date(:dateEnd,'MM/DD/YYYY')) AND u.organisation=:organisation order by u.id desc")
    Page<Parcel> findByDateRangeAndOrganisation(@Param("dateStart")Date dateStart,
                                      @Param("dateEnd")Date dateEnd,
                                      @Param("organisation")long organisation,
                                      Pageable pageable);
    List<Parcel> findByBarcode(@Param("barcode")String barcode);

    List<Parcel> findByCourierAndStatus(@Param("courier")long id, @Param("status")int code);
}
