package com.asif.reportgenerateminiproject2.Repository;

import com.asif.reportgenerateminiproject2.Entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerDetails, Serializable> {

    @Query("select distinct(planName) from CustomerDetails")
    public List<String> getPlanNames();

    @Query("select distinct(planStatus) from CustomerDetails")
    public List<String> getPlanStatus();

}

